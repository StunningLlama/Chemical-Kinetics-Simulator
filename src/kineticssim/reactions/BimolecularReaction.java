package kineticssim.reactions;

import kineticssim.Compound;
import kineticssim.Particle;
import kineticssim.PhysicsSimulation;
import kineticssim.util.Vector;

// A+B -> C+D
public class BimolecularReaction extends Reaction {

	public Compound ra;
	public Compound rb;
	
	public Compound prodA;
	public Compound prodB;

	private double u;
	private double aFrac;
	private double bFrac;
	private double collisionprob;
	
	public BimolecularReaction(double activationenergy_i, double deltaH_i, double prob) {
		super(activationenergy_i, deltaH_i);
		collisionprob = prob;
	}

	//necessary for the computations to work, computes the reduced mass
	public void calcReducedMass() {
		u = (prodA.getmass()*prodB.getmass())/(prodA.getmass() + prodB.getmass());
		aFrac = prodB.getmass() / (prodA.getmass() + prodB.getmass());
		bFrac = prodA.getmass() / (prodA.getmass() + prodB.getmass());
	}

	//Only activates reaction if the kinetic energy is greater than activation energy
	public boolean checkCollisionSucesss(Particle a, Particle b, PhysicsSimulation s, Vector vCOM, Vector pCOM, double fwdenergy, double energy) {
		if (a.getCompound().equals(ra) && b.getCompound().equals(rb) || a.getCompound().equals(rb) && b.getCompound().equals(ra))
		{
			if (fwdenergy > this.activationenergyfwd && (Math.random() < collisionprob)) {
				//System.out.println("init " + ((velA.magsq()*a.getMass()*0.5 + velB.magsq()*b.getMass()*0.5)));
				//System.out.println("en " + energy);
				if (energy-this.deltaH < 0) {
					System.out.println("ERROR energy is negative");
					return false;
				}
				
				double ang = Math.random()*2*Math.PI;
				Vector randomDirection = new Vector((double)Math.cos(ang), (double)Math.sin(ang));
				double vRel = Math.sqrt((energy-this.deltaH)/(u/2.0));
				Particle aP = new Particle(Vector.add(pCOM, Vector.mul(randomDirection, prodA.getradius())), Vector.add(vCOM, Vector.mul(randomDirection, vRel*aFrac)), prodA);
				Particle bP = new Particle(Vector.add(pCOM, Vector.mul(randomDirection, -prodB.getradius())), Vector.add(vCOM, Vector.mul(randomDirection, -vRel*bFrac)), prodB);
				s.removeParticle(a);
				s.removeParticle(b);
				s.addParticle(aP);
				s.addParticle(bP);
				return true;
			}
		}
		return false;
	}

	
	@Override
	public String toString() {
		return "Bimolecular Reaction, " + ra + " + " + rb + " ->" + prodA + " " + prodB + ": " + super.toString();
	}
}
