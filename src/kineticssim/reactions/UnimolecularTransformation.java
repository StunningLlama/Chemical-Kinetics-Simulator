package kineticssim.reactions;

import java.util.ListIterator;

import kineticssim.Compound;
import kineticssim.Particle;
import kineticssim.PhysicsSimulation;
import kineticssim.util.Vector;

// A -> B
public class UnimolecularTransformation extends Reaction {

	public Compound c;
	public Compound prod;
	private double k;
	
	public UnimolecularTransformation(double activationenergy_i, double deltaH_i, double rateconst) {
		super(activationenergy_i, deltaH_i);
		k = rateconst;
	}

	//Check whether a random chance has been met and the particle decays
	public void checkRxnSuccess(Particle p, PhysicsSimulation ph, ListIterator<Particle> it) {
		if (p.getCompound().equals(c) && p.getActivated()) {
			if (Math.random() < ph.timestep*k) {
				Particle product = new Particle(p.getPosition(), p.getVelocity(), prod);
				product.setExcessKE(-this.deltaH);
				ph.removeParticleAlt(p);
				ph.addParticleAlt(product);
				it.remove();
				it.add(product);
				ph.bondenergy += this.deltaH;
			}
		}
	}
	
	@Override
	public String toString() {
		return "Unimolecular Transformation, " + c.getname() + " -> " + prod.getname() + ", " + super.toString();
	}
}
