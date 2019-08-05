package kineticssim.reactions;

import java.util.ListIterator;

import kineticssim.Compound;
import kineticssim.Particle;
import kineticssim.PhysicsSimulation;
import kineticssim.util.Vector;

// A -> B + C
public class UnimolecularDecomposition extends Reaction {

	public Compound c;
	public Compound prodA;
	public Compound prodB;
	private double u;
	private double aFrac;
	private double bFrac;
	private double rateConst;
	
	public UnimolecularDecomposition(double activationenergy_i, double deltaH_i, double rateConst_i) {
		super(activationenergy_i, deltaH_i);
		rateConst = rateConst_i;
	}
	
	//Necessary to calculate the reduced mass
	public void calcReducedMass() {
		u = (prodA.getmass()*prodB.getmass())/(prodA.getmass() + prodB.getmass());
		aFrac = prodB.getmass() / (prodA.getmass() + prodB.getmass());
		bFrac = prodA.getmass() / (prodA.getmass() + prodB.getmass());
	}
	//p.getActivated()
	
	//Check whether a random chance has been met and the particle decays
	public void checkRxnSuccess(Particle p, PhysicsSimulation ph, ListIterator<Particle> it) {
		if (p.getCompound().equals(c) && p.getActivated()) {
			if (Math.random() < ph.timestep*rateConst) {
				double a = Math.random()*2*Math.PI;
				Vector randomDirection = new Vector((double)Math.cos(a), (double)Math.sin(a));
				double vRel = Math.sqrt(-this.deltaH/(u/2.0));
				Vector pos = p.getPosition();
				Particle aP = new Particle(Vector.add(pos, Vector.mul(randomDirection, prodA.getradius())), Vector.add(p.getVelocity(), Vector.mul(randomDirection, vRel*aFrac)), prodA);
				Particle bP = new Particle(Vector.add(pos, Vector.mul(randomDirection, -prodB.getradius())), Vector.add(p.getVelocity(), Vector.mul(randomDirection, -vRel*bFrac)), prodB);
				ph.removeParticleAlt(p);
				ph.addParticleAlt(aP);
				ph.addParticleAlt(bP);
				it.remove();
				it.add(aP);
				it.add(bP);
				ph.bondenergy += this.deltaH;
			}
		}
	}

	@Override
	public String toString() {
		return "Unimolecular Decomposition, " + c.getname() + " -> " + prodA.getname() + " + " + prodB.getname() + ", " + super.toString();
	}
}
