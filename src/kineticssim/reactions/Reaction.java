package kineticssim.reactions;

import kineticssim.PhysicsSimulation;

/*****
 * Chemical Kinetics Simulator
 * @author Brandon
 * E block
 *****/

//Parent class for all the possible reactions, has a couple of fields but not much more than that because conditions for reaction success are different.
public abstract class Reaction {
	//public abstract void checkCollisionSucesss(Particle a, Particle B, Vector velA, Vector velB, PhysicsSimulation s, Vector vCOM, Vector pCOM, double energy);
	
	public double activationenergyfwd;
	public double deltaH;
//	public double actiationenergyback;
	public Reaction(double activationenergy_i, double deltaH_i) {
		activationenergyfwd = activationenergy_i;
		deltaH = deltaH_i;
	}
	
	public Reaction(double rateconst, double dH, double temperature, double preexp) {
		double eA = -Math.log(rateconst/preexp)*PhysicsSimulation.BOLTZMANN*temperature;
		activationenergyfwd = eA;
		deltaH = dH;
	}
	
	@Override
	public String toString() {
		return "eA = " + this.activationenergyfwd + ", dH = " + deltaH;
	}
}
