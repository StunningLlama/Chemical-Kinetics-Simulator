package kineticssim;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

import kineticssim.gui.ControlPanel;
import kineticssim.gui.MoleculeRenderer;
import kineticssim.reactions.BimolecularReaction;
import kineticssim.reactions.BimolecularSynthesis;
import kineticssim.reactions.UnimolecularDecomposition;
import kineticssim.reactions.UnimolecularTransformation;
import kineticssim.reactions.LindemannHinshelwoodEq;
import kineticssim.reactions.Reaction;
import kineticssim.reactions.ReactionSystem;
import kineticssim.util.ConcentrationDataCollector;
import kineticssim.util.DataCollector;
import kineticssim.util.Histogram;
import kineticssim.util.SIFormatter;
import kineticssim.util.Vector;

/*****
 * Chemical Kinetics Simulator
 * @author Brandon
 * E block
 *****/


/***************************
 * Velocity - m/s
 * radius - m
 * mass - kg
 * Units of length - m
 * 
 * 
 * 
 * 
 * 
 *
 */

//Main class for the physics simulation, contains all the particles, data, and information about the physical state of the system.
public class PhysicsSimulation {
	
	//Main list used for iterating accross the molecules.
	private List<Particle> molecules;
	
	private Vector boundTL = new Vector(0, 0);
	private Vector boundBR = new Vector(1.2E-8*2 * 4.0/3.0, 0.7E-8*2 * 4.0/3.0);
	
	private double defwidth = 1.2E-8*2 * 4.0/3.0;
	private double defheight = 0.7E-8*2 * 4.0/3.0;
	
	ReactionSystem reactions;
	public List<Compound> compounds;
	
	public double time = 0.0;
	public double timestep = 1E-13;
	
	public double nextcollection = 0.0;
	public double collectioninterval = 1E-13;
	
	public double avgmass = 1.0;
	public double avgradius = 1.0;
	
	public double goaltemperature = 4000;
	public double temperature = 1000.0;
	Random rand = new Random();
	
	public boolean simulating = false;
	
	//This separates the particles by type.
	public HashMap<Compound, List<Particle>> particlecount;
	
	List<DataCollector> graphs;
	
	Histogram histogram;
	
	//private int graphupdateinterval = 20;
	
	KineticsSimulator sim;
	
	public PhysicsSimulation(KineticsSimulator k) {
		molecules = new ArrayList<Particle>();
		graphs = new ArrayList<DataCollector>();
		compounds = new ArrayList<Compound>();
		particlecount = new HashMap<Compound, List<Particle>>();
		histogram = new Histogram(25);
		sim = k;
	}
	
	public Vector getboundTL() {
		return boundTL;
	}
	
	public Vector getboundBR() {
		return boundBR;
	}
	
	//These methods deal with the histogram and data collection, seen at the bottom of the screen
	public void resethistogram() {
		for (int i = 0; i < histogram.getSize(); i++) {
			histogram.setHistogram(i, 0);
		}
	}
	
	public List<DataCollector> getGraphs() {
		return this.graphs;
	}
	
	public Histogram getHistogram() {
		return this.histogram;
	}

	//Add a new type of compound
	public void addCompound(Compound c) {
		List<Particle> col = new ArrayList<Particle>();
		if (!particlecount.containsKey(c))
			particlecount.put(c, col);
		if (!compounds.contains(c))
			compounds.add(c);
		
		sim.getGUI().getControlPanel().c.removeAll();
		for (Compound comp: compounds)
			sim.getGUI().getControlPanel().c.add(comp.getname());
	}

	
	//These methods makes sure appropriate fields are updated when particles are added or removed
	public void addParticleAlt(Particle p) {
		if (!particlecount.get(p.getCompound()).contains(p))
			particlecount.get(p.getCompound()).add(p);
	}
	
	public void removeParticleAlt(Particle p) {
		particlecount.get(p.getCompound()).remove(p);
	}
	
	public void addParticle(Particle p) {
		molecules.add(p);
		if (!particlecount.get(p.getCompound()).contains(p))
			particlecount.get(p.getCompound()).add(p);
	}
	
	public void removeParticle(Particle p) {
		molecules.remove(p);
		particlecount.get(p.getCompound()).remove(p);
	}

	//One iteration of the simulation. Moves time forward by a timestep.
	public void iterate() {

		if (!simulating)
			return;
		
		synchronized(this) {

			long starttime = System.nanoTime();

			//Loops over particles to see which ones are close enough to collide
			Particle a;
			Particle b;
			for (int i = 0; i < molecules.size() - 1; i++) {
				for (int j = i+1; j < molecules.size(); j++) {
					a = molecules.get(i);
					b = molecules.get(j);
					if (a.distanceSquaredTo(b) < (a.getRadius() + b.getRadius())*((a.getRadius() + b.getRadius()))) {

						collide(a, b);
					}
				}
			}


			//Computes which particles undergo unimolecular reactions (one reactant involved)
			for (Reaction r: reactions.getReactions()) {
				if (r instanceof UnimolecularDecomposition || r instanceof UnimolecularTransformation) {
					ListIterator<Particle> it = molecules.listIterator();
					while (it.hasNext()) {
						Particle m = it.next();
						if (r instanceof UnimolecularDecomposition)
							((UnimolecularDecomposition) r).checkRxnSuccess(m, this, it);
						else
							((UnimolecularTransformation) r).checkRxnSuccess(m, this, it);
					}
				}
			}
			
			//This code bounces the particles off the walls and corrects the temperature
			for (Particle m: molecules) {
				m.integrateVelocity(timestep);
				
				
				Vector v = m.getVelocity();
				double r = m.getRadius();
				boolean collided = false;
				
				/*if (m.colliding == 4) {
					Vector dim = Vector.sub(getboundBR(), getboundTL());
					Vector center = Vector.add(getboundBR(), getboundTL()).mul(0.5);
					m.setPosition(new Vector((Math.random() - 0.5)*dim.x + center.x, (Math.random() - 0.5)*dim.y + center.y));
					System.out.println("asdasd");
							
				}*/
				
				if ((m.getPosition().x - r) < boundTL.x) {
					v.x = Math.abs(v.x);
					m.getPosition().x += v.x * timestep;
					if ((m.getPosition().x - r) < boundTL.x) {
						m.getPosition().x = boundTL.x + r;
					}
					collided = true;
				} else if ((m.getPosition().x + r) > boundBR.x) {
					v.x = -Math.abs(v.x);
					m.getPosition().x += v.x * timestep;
					if ((m.getPosition().x + r) > boundBR.x) {
						m.getPosition().x = boundBR.x - r;
					}
					collided = true;
				}
				
				if ((m.getPosition().y - r) < boundTL.y) {
					v.y = Math.abs(v.y);
					m.getPosition().y += v.y * timestep;
					if ((m.getPosition().y - r) < boundTL.y) {
						m.getPosition().y = boundTL.y + r;
					}
					collided = true;
				} else if ((m.getPosition().y + r) > boundBR.y) {
					v.y = -Math.abs(v.y);
					m.getPosition().y += v.y * timestep;
					if ((m.getPosition().y + r) > boundBR.y) {
						m.getPosition().y = boundBR.y - r;
					}
					collided = true;
				}
				
				/*if (collided)
					m.colliding++;
				else
					m.colliding = 0;*/
				
				if (!sim.getGUI().getControlPanel().settemp.isSelected())
					scalefactor = 1;
				
				if (collided)
					m.setVelocity(v.mul(scalefactor));


			}

			//This updates the histogram to reflect the velocity distribution of the particles
			resethistogram();
			for (Particle m: molecules) {
				if ((int)(KE(m)/histogram.getHistogramDiv()) < histogram.getSize())
					histogram.incHistogram((int)(KE(m)/histogram.getHistogramDiv()));
			}

			time += timestep;
			n++;

			temperature = computetemperature();

			scalefactor = Math.pow(targettemp/temperature, 1.0);

			if (scalefactor > 2.0)
				scalefactor = 2.0;

			if (scalefactor < 0.1)
				scalefactor = 0.1;
			

			//This collects data for the graph of the concentration over time
			Vector dim = Vector.sub(getboundBR(), getboundTL());
			if (time > this.nextcollection) {

				for (DataCollector c: graphs) {
					if (c instanceof ConcentrationDataCollector) {
						Compound comp = ((ConcentrationDataCollector)c).comp;
						((ConcentrationDataCollector)c).addDataPoint(time, particlecount.get(comp).size()/(dim.x*dim.y));
					}
				}
				
				nextcollection += this.collectioninterval;
			}
			histogram.setHistogramDiv(4*Math.sqrt(3 * temperature * BOLTZMANN / avgmass) / histogram.getSize());



			long endtime = System.nanoTime() - starttime;
			//System.out.println("Frametime: " + endtime);

		}
	}

	public int iterations = 1;
	
	public void updateGUI() {

		String reducetimestep = "";

		if (timestep*Math.sqrt(3.0*BOLTZMANN*temperature/avgmass) > avgradius*0.25)
			reducetimestep = "(Please reduce timestep) ";

		double energy = this.computetotalenergy();

		ControlPanel cp = sim.getGUI().getControlPanel();


		targettemp = Math.pow(10, cp.temperature.getValue() / 30.0 + 1.0);
		
		
		int targetcount = 0;

		timestep = Math.pow(10, cp.timestep.getValue() / 20.0 - 15.0);
		collectioninterval = Math.pow(10, cp.datacollectioninterval.getValue() / 20.0 - 15.0);

		boundBR.x = Math.pow(10, cp.width.getValue() / 50.0 - 1.0)*defwidth;
		boundBR.y = Math.pow(10, cp.height.getValue() / 50.0 - 1.0)*defheight;

		iterations = cp.iterations.getValue();

		//This updates the textarea to include information about the temperature and stuff
		//double cA = this.calculateConcentration(KineticsSimulator.N2O4)/AVOGADRO;
		//double cB = this.calculateConcentration(KineticsSimulator.NO2)/AVOGADRO;
		String s = "";
		s += ("Time: " + SIFormatter.format("s", time, 3)) + '\n';
		s += ("Temperature: " + SIFormatter.format("K", temperature, 3)) + '\n';
		s += ("Energy: " + SIFormatter.format("J", energy, 3)) + '\n';
		s += ("Energy (tot): " + SIFormatter.format("J", energy + bondenergy, 3)) + '\n';
		//s += ("" + cA / (cB*cB)) + '\n';
		cp.setText(s);
		cp.l3.setText(SIFormatter.format("K", targettemp, 1) + " ");
		cp.l5.setText(reducetimestep + SIFormatter.format("S", timestep, 1) + " ");
		cp.l5d.setText(SIFormatter.format("S", collectioninterval, 1) + " ");
		cp.l4.setText(targetcount + " ");
		cp.l5i.setText("Iterations per frame: " + iterations);
		cp.l5w.setText(SIFormatter.format("m", boundBR.x, 1));
		cp.l5h.setText(SIFormatter.format("m", boundBR.y, 1));
	}
	
	boolean dbg = false;
	double scalefactor = 1.0;
	public double targettemp = 1000.0;

	int n = 0;
	public static double BOLTZMANN = 1.38e-23;
	public static double AVOGADRO = 6.022E23;
	public static double IDEALGAS = BOLTZMANN*AVOGADRO;
	public double KE(Particle p) {
		//return p.getVelocity().magsq() * p.getMass() * 0.5;
		return p.getVelocity().mag();
	}

	public void calculateAvgMass() {
		double totalmass = 0.0;
		for (Particle p: molecules) {
			totalmass += p.getMass();
		}
		avgmass = totalmass/molecules.size();
	}
	
	public void calculateAvgRadius() {
		double totalradius = 0.0;
		for (Particle p: molecules) {
			totalradius += p.getRadius();
		}
		avgradius = totalradius/molecules.size();
	}

	public double calculateConcentration(Compound c) {
		if (!particlecount.containsKey(c))
			return 0;
		Vector vec = Vector.sub(this.boundBR, this.boundTL);
		return particlecount.get(c).size() / (vec.x * vec.y);
	}

	//Function that gets called when two particles collide
	public void collide(Particle a, Particle b) {
		//System.out.println(a.getPosition() + " " + b.getPosition() + " " + a.getVelocity() + " " + b.getVelocity() + " ");


		long starttime = System.nanoTime();


		MoleculeRenderer r = sim.getGUI().getRenderer();

		//Lots of vector math to simulate an elastic collision with some energy changes
		Vector aVel = a.getVelocity();
		Vector bVel = b.getVelocity();
		Vector vCM = Vector.add(Vector.mul(aVel, a.getMass()), Vector.mul(bVel, b.getMass())).mul(1.0/(a.getMass() + b.getMass()));

		Vector aVelT = Vector.sub(aVel, vCM);
		Vector bVelT = Vector.sub(bVel, vCM);

		//Convert velocities into center of mass reference frame
		
		if (vCM.isNaN())
			System.out.println("A");
		if (dbg) {
			System.out.println("KE before\n"
					+ aVel.magsq() * a.getMass() * 0.5 + "\n"
					+ bVel.magsq() * b.getMass() * 0.5 + "\n");


			System.out.println("KE before (transformed)\n"
					+ aVelT.magsq() * a.getMass() * 0.5 + "\n"
					+ bVelT.magsq() * b.getMass() * 0.5 + "\n");

		}
		Scanner s = null;

		if (dbg) { 
			r.drawVec(a.getPosition(), aVel);
			r.drawVec(b.getPosition(), bVel);


			s = new Scanner(System.in);
			sim.getGUI().repaint();
			s.nextLine();
			r.removeVec(0);
			r.removeVec(0);
		}

		if (dbg) {
			r.drawVec(new Vector(a.getPosition()), aVelT);
			r.drawVec(new Vector(b.getPosition()), bVelT);

			sim.getGUI().repaint();
			s.nextLine();
		}


		Vector diff = Vector.sub(a.getPosition(), b.getPosition());
		double mindist = Vector.err(diff, aVelT).mag();
		//if (mindist >= (a.getRadius() + b.getRadius()))
		//	return;


		if (dbg) {
			r.drawVec(new Vector(a.getPosition()), Vector.err(diff, aVelT));

			sim.getGUI().repaint();
			s.nextLine();
		}

		Vector something = Vector.proj(diff, aVelT);


		if (dbg) {
			r.drawVec(a.getPosition(), something);

			sim.getGUI().repaint();
			s.nextLine();
		}

		double combinedradius = (a.getRadius() + b.getRadius());
		double theta = Math.asin(mindist / combinedradius);
		double combinedl = (a.getRadius() + b.getRadius()) * Math.cos(theta);

		double delta = something.mag() - combinedl;

		double totalmag = aVelT.mag() + bVelT.mag();



		if (dbg) {
			r.drawVec(new Vector(500, 250), new Vector(combinedl, 0));

			r.drawVec(new Vector(b.getPosition()), new Vector(100*Math.cos(theta), 100*Math.sin(theta)));

			sim.getGUI().repaint();
			s.nextLine();
		}


		if (aVelT.isNaN() || bVelT.isNaN())
			System.out.println("BB");
		a.changePosition(Vector.mul(aVelT, 1/totalmag).mul(delta), 1.0);
		b.changePosition(Vector.mul(bVelT, 1/totalmag).mul(delta), 1.0);

		
		//Move particles out of the way of eachother

		if (dbg) {
			sim.getGUI().repaint();
			s.nextLine();
		}

		Vector diff2 = (Vector.sub(b.getPosition(), a.getPosition()));
		//Vector.mul(diff2, a.getRadius() / combinedradius);
		//Vector.mul(diff2, b.getRadius() / combinedradius);
		Vector adelta = Vector.proj(aVelT, diff2);
		Vector bdelta = Vector.proj(bVelT, diff2);
		Vector aVelF = Vector.add(aVelT, Vector.mul(adelta, -2));
		Vector bVelF = Vector.add(bVelT, Vector.mul(bdelta, -2));

		//Compute velocities of the new travel direction
		
		if (aVelF.isNaN() || bVelF.isNaN())
			System.out.println("B");
		Vector posCM = Vector.add(Vector.mul(a.getPosition(), a.getMass()), Vector.mul(b.getPosition(), b.getMass())).mul(1/(a.getMass()+b.getMass()));
		double KEtot = (aVelT.magsq() * a.getMass() * 0.5) + (bVelT.magsq() * b.getMass() * 0.5);
		double KEfwd = (adelta.magsq() * a.getMass() * 0.5) + (bdelta.magsq() * b.getMass() * 0.5);

		double deltaKE = a.getExcessKE() + b.getExcessKE();
		double iKE = deltaKE;
		
		//Compute KE's
		
		//This code checks to see if two colliding particles will react
		for (Reaction rxn: reactions.getReactions()) {
			if (rxn instanceof BimolecularSynthesis) {
				 if (((BimolecularSynthesis) rxn).checkCollisionSucesss(a, b, this, vCM, posCM, KEfwd, KEtot))
					 break;
			}
			if (rxn instanceof BimolecularReaction) {
				 if (((BimolecularReaction) rxn).checkCollisionSucesss(a, b, this, vCM, posCM, KEfwd, KEtot))
					 break;
			}
			if (rxn instanceof LindemannHinshelwoodEq) {
				 double d = ((LindemannHinshelwoodEq) rxn).checkCollisionSucesss(a, b, this, vCM, posCM, KEfwd, KEtot);
				 deltaKE -= d;
				 if (d != 0) {
					// System.out.println(deltaKE);
					 break;
					 
				 }
			}
		}

		if (dbg) {
		System.out.println("KE after (transformed)\n"
				+ aVelF.magsq() * a.getMass() * 0.5 + "\n"
				+ bVelF.magsq() * b.getMass() * 0.5 + "\n");


			r.drawVec(a.getPosition(), aVelF);
			r.drawVec(b.getPosition(), bVelF);
		}

		//Vector aVelFT = Vector.add(vCM, aVelF);
		//Vector bVelFT = Vector.add(vCM, bVelF);
		

		/*if (aVelFT.isNaN() || bVelFT.isNaN())
			System.out.println("C");
		
		
		if (dbg) {
		System.out.println("KE after\n"
				+ aVelFT.magsq() * a.getMass() * 0.5 + "\n"
				+ bVelFT.magsq() * b.getMass() * 0.5 + "\n");
		}*/
		

		a.removeExcessKE();
		b.removeExcessKE();
		//double KE = aVelFT.magsq() * a.getMass() * 0.5 + bVelFT.magsq() * b.getMass() * 0.5;
		
		
		double KE = aVelF.magsq() * a.getMass() * 0.5 + bVelF.magsq() * b.getMass() * 0.5;
		double finalKE = KE+deltaKE;
		double factor = Math.sqrt(finalKE/KE);

		Vector aVelFT = Vector.add(vCM, aVelF.mul(factor));
		Vector bVelFT = Vector.add(vCM, bVelF.mul(factor));

		//Change velocities based on the changes in KE that we need
		
		//if (aVelFT.isNaN() || bVelFT.isNaN())
		//	System.out.println("D");
		
		if (!Double.isFinite(factor)) {
			System.out.println("E");
			System.out.println(iKE);
			System.out.println(KEtot);
			System.out.println(KEfwd);
			System.out.println(KE);
			System.out.println(finalKE);
			System.out.println(deltaKE);
		}
		try {
		//a.setVelocity(aVelFT.mul(factor));
	//	b.setVelocity(bVelFT.mul(factor));
		a.setVelocity(aVelFT);
		b.setVelocity(bVelFT);
		} catch(RuntimeException e) {
			e.printStackTrace();
		}
		if (dbg) {
			sim.getGUI().repaint();
			s.nextLine();
		}
		

		long endtime = System.nanoTime() - starttime;
		//System.out.println("Took " + endtime + " ns to compute");
	}
	
	public double bondenergy = 0.0;
	
	//Calculates the expected distribution of particle speeds
	public double maxwellboltzmann(double speed, double temperature, double mm) {
		return 4.0*Math.PI*speed*speed*Math.pow((mm/(2*Math.PI*BOLTZMANN*temperature)), 1.5)*Math.exp(-mm*speed*speed / (2*BOLTZMANN*temperature));
	}

	//Calculate temperature of all particles
	public double computetemperature() {
		double KE = 0.0;
		for (Particle m: molecules) {
			KE += 0.5 * m.getVelocity().magsq() * m.getMass();
			KE += m.getExcessKE();
		}
		if (molecules.size() == 0)
			return 0;
		return KE/molecules.size() * 2.0 / 3.0 / BOLTZMANN;
	}
	
	//Calculate the total energy
	public double computetotalenergy() {
		double KE = 0.0;
		for (Particle m: molecules) {
			KE += 0.5 * m.getVelocity().magsq() * m.getMass();
			KE += m.getExcessKE();
		}
		if (molecules.size() == 0)
			return 0;
		return KE;
	}
	
	public List<Particle> getParticles() {
		return molecules;
	}
}
