package kineticssim.reactions;
import kineticssim.Compound;
import kineticssim.Particle;
import kineticssim.PhysicsSimulation;
import kineticssim.util.Vector;

/*****
 * Chemical Kinetics Simulator
 * @author Brandon
 * E block
 *****/

//A+B -> C
public class BimolecularSynthesis extends Reaction {

	public Compound ra;
	public Compound rb;
	
	public Compound prod;
	
	public double preExpFactor;
	
	public BimolecularSynthesis(double ac, double dH) {
		super(ac, dH);
	}

	//Only activates reaction if the kinetic energy is greater than activation energy
	public boolean checkCollisionSucesss(Particle a, Particle b, PhysicsSimulation s, Vector vCOM, Vector pCOM, double fwdenergy, double energy) {
		if (a.getCompound().equals(ra) && b.getCompound().equals(rb) || a.getCompound().equals(rb) && b.getCompound().equals(ra))
		{
			if (fwdenergy > this.activationenergyfwd) {
				//System.out.println("init " + ((velA.magsq()*a.getMass()*0.5 + velB.magsq()*b.getMass()*0.5)));
				//System.out.println("en " + energy);
				if (energy-this.deltaH < 0) {
					System.out.println("ERROR energy is negative");
					return false;
				}
				s.removeParticle(a);
				s.removeParticle(b);
				Particle p = new Particle(pCOM.x, pCOM.y, vCOM.x, vCOM.y, prod);
				p.setExcessKE(energy-this.deltaH);
				s.addParticle(p);
				s.bondenergy += deltaH;
			}
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Bimolecular Synthesis, " + ra.getname() + " + " + rb.getname() + " -> " + prod.getname() + ", " + super.toString();
	}
}
