package kineticssim.reactions;
import kineticssim.Compound;
import kineticssim.Particle;
import kineticssim.PhysicsSimulation;
import kineticssim.util.Vector;

// A + M -> A* + M, necessary for reactions with two components on one side.
public class LindemannHinshelwoodEq extends Reaction {

	public Compound A;
	

	public LindemannHinshelwoodEq(double ac, double dH) {
		super(ac, dH);
	}

	//Only activates reaction if the kinetic energy is greater than activation energy, this is a two way reaction
	public double checkCollisionSucesss(Particle a, Particle b, PhysicsSimulation s, Vector vCOM, Vector pCOM, double fwdenergy, double energy) {
		Particle reactant;
		if (a.getCompound().equals(A)) {
			reactant = a;
		} else if (b.getCompound().equals(A)) {
			reactant = b;
		} else {
			return 0;
		}
		if (reactant.getActivated() && fwdenergy > (deltaH - this.activationenergyfwd)) {
			reactant.setActivated(false);
			s.bondenergy -= this.deltaH;
			return -this.deltaH;
		}
		if (!reactant.getActivated() && fwdenergy > this.activationenergyfwd) {
			reactant.setActivated(true);
			s.bondenergy += this.deltaH;
			return this.deltaH;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return "LH equilibrium, " + A.getname() + " + M -> " + A.getname() + "* + M, " + super.toString();
	}
}
