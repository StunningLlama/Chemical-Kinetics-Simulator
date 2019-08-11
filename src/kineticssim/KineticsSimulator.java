package kineticssim;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import kineticssim.gui.ReactionManager;
import kineticssim.gui.SimulationGUI;
import kineticssim.reactions.BimolecularSynthesis;
import kineticssim.reactions.CompoundReaction;
import kineticssim.reactions.UnimolecularDecomposition;
import kineticssim.reactions.LindemannHinshelwoodEq;
import kineticssim.reactions.Reaction;
import kineticssim.reactions.ReactionSystem;
import kineticssim.util.ConcentrationDataCollector;
import kineticssim.util.Scale;
import kineticssim.util.Vector;

/*****
 * Chemical Kinetics Simulator
 * @author Brandon
 * E block
 *****/

//dimerization of no2
//Ozone decomposition
//Oxygen hydrogen

//This is the main class
public class KineticsSimulator {
	private SimulationGUI simGUI;
	private PhysicsSimulation phys;
	
	public static Compound NO2;
	public static Compound N2O4;
	

	public static Compound A;
	public static Compound B;
	public Scale scale = new Scale();
	
	public double toMass(double molarmass) {
		return molarmass/PhysicsSimulation.AVOGADRO;
	}
	
	private ReactionManager rm;
	
	public KineticsSimulator() {
		init();
	}
	
	//Initialization function
	
	public void init() {
		phys = new PhysicsSimulation(this);
		simGUI = new SimulationGUI(this);
		ReactionSystem reactions = new ReactionSystem();
		
		boolean preset = true;
		
		//Example chemical reaction that is set as the deafault reaction. Also demonstrates how to use some of the functions
		
		CompoundReaction r = null;
		
		if (preset) {
			NO2 = new Compound(toMass(46), 0.14E-9, new Color(255, 255, 255), "NO2");
			N2O4 = new Compound(toMass(92), 0.2E-9, new Color(238, 100, 40), "N2O4");

			phys.addCompound(NO2);
			phys.addCompound(N2O4);
			addRandomParticles(1000, NO2, 800);

			r = new CompoundReaction();
			r.setDeltaH(-1E-19);
			r.setActivationEnergy(1E-19);
			r.setRateFwd(1E12/31);
			r.setRateBack(1E12/31);
			r.getreactants().add(NO2);
			r.getreactants().add(NO2);
			r.getproducts().add(N2O4);
			
			
			
			r.convertToReactions(reactions.getReactions());
			phys.graphs.add(new ConcentrationDataCollector(300, NO2, scale));
			phys.graphs.add(new ConcentrationDataCollector(300, N2O4, scale));
		}


		
		for (Reaction rr: reactions.getReactions())
			System.out.println(rr);
		
		phys.reactions = reactions;
		phys.calculateAvgMass();
		phys.calculateAvgRadius();
		rm = new ReactionManager();

		rm.compounds.add(NO2);
		rm.compounds.add(N2O4);
		rm.updateCompList();
		
		rm.reactions.add(r);
		rm.updateRList();
		
		phys.nextcollection = phys.collectioninterval;
		
		phys.simulating = true;
	}
	
	
	//This adds particles randomly to the simulation based on the compound and temperature
	public void addRandomParticles(int n, Compound c, double temperature) {
		Vector dim = Vector.sub(phys.getboundBR(), phys.getboundTL());
		Vector center = Vector.add(phys.getboundBR(), phys.getboundTL()).mul(0.5);
		for (int i = 0; i < n; i++) {
			double angle = Math.random()*2.0*Math.PI;
			double speed = Math.sqrt(PhysicsSimulation.BOLTZMANN * temperature * 3.0 / c.getmass());
			Particle p = new Particle((Math.random() - 0.5)*dim.x + center.x, (Math.random() - 0.5)*dim.y + center.y, speed * Math.cos(angle), speed * Math.sin(angle), c);
			phys.addParticle(p);

			//System.out.println(p.getVelocity());
			//System.out.println(p.getPosition());
			
		}
	}
	
	//Resets the field if you want to load another scenario
	public void setUp() {
		synchronized(phys) {
		
		ReactionSystem reactions = new ReactionSystem();
		phys.getParticles().clear();
		phys.compounds.clear();
		phys.particlecount.clear();
		
		//Add new compounds
		
		for (Compound c: rm.compounds) {
			phys.addCompound(c);
		}
		
		//Add new reactions
		
		for (CompoundReaction rx: rm.reactions) {
			rx.convertToReactions(reactions.getReactions());
		}
		
		phys.reactions = reactions;
		phys.calculateAvgMass();
		phys.calculateAvgRadius();
	
		
		for (Reaction r: phys.reactions.getReactions()) {

			System.out.println(r);
		}
		
		
		//Add graphs
		scale.reset();
		phys.graphs.clear();
		for (Compound c: phys.compounds) {
			phys.graphs.add(new ConcentrationDataCollector(300, c, scale));	
		}
		
		phys.time = 0;
		phys.nextcollection = phys.collectioninterval;
		phys.bondenergy = 0;
		}
	}
	

	//Various getters
	
	public SimulationGUI getGUI() {
		return simGUI;
	}
	
	public PhysicsSimulation getPhysicsSimulation() {
		return phys;
	}
	
	public ReactionManager getReactionManager() {
		return rm;
	}
	
	public void start() {
		
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		KineticsSimulator sim = new KineticsSimulator();
		sim.start();
	}
}
