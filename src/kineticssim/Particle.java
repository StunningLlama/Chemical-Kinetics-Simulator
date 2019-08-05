package kineticssim;
import kineticssim.util.Vector;

/*****
 * Chemical Kinetics Simulator
 * @author Brandon
 * E block
 *****/

//Particle objects are what the simulation uses to represent molecules moving about
public class Particle {
	private Vector pos;
	private Vector vel;
	
	public int colliding = 0;
	
	private boolean activated;
	
	private double excessKE;
	
	private Compound compound;
	
	public Particle(Compound c) {
		pos = new Vector();
		vel = new Vector();
		compound = c;
		activated = false;
	}
	
	//Constructors with starting pos and vel
	
	public Particle(double xstart, double ystart, double velx, double vely, Compound c) {
		pos = new Vector(xstart, ystart);
		vel = new Vector(velx, vely);
		compound = c;
		activated = false;
	}
	
	public Particle(Vector pos_i, Vector vel_i, Compound c) {
		pos = pos_i;
		vel = vel_i;
		compound = c;
		activated = false;
	}

	public double distanceSquaredTo(Particle p) {
		return Vector.distsq(pos, p.pos);
	}
	
	
	//Various getters and setters
	
	public Compound getCompound() {
		return compound;
	}
	
	public double getX() {
		return pos.x;
	}
	
	public double getY() {
		return pos.y;
	}
	
	public double getVX() {
		return vel.x;
	}
	
	public double getVY() {
		return vel.y;
	}
	
	public Vector getVelocity() {
		return vel;
	}
	
	public Vector getPosition() {
		return pos;
	}
	
	public double getExcessKE() {
		return excessKE;
	}
	
	public void removeExcessKE() {
		excessKE = 0;
	}
	
	public void setExcessKE(double KE) {
		excessKE = KE;
	}
	
	public double getMass() {
		return compound.getmass();
	}
	
	public double getRadius() {
		return compound.getradius();
	}
	
	//Numerical methods to implement calculus into the simulation - position is integral of velocity
	public void integrateVelocity(double frac) {
		pos.x += vel.x * frac;
		pos.y += vel.y * frac;
		if (Double.isNaN(pos.x) || Double.isNaN(pos.y))
			throw new RuntimeException();
	}
	
	//Change the position of the particle
	public void changePosition(Vector dv, double frac) {
		pos.x += dv.x*frac;
		pos.y += dv.y*frac;
		if (Double.isNaN(pos.x) || Double.isNaN(pos.y))
			throw new RuntimeException();
	}
	
	public void setVelocity(Vector v) {
		vel.x = v.x;
		vel.y = v.y;
		if (Double.isNaN(vel.x) || Double.isNaN(vel.y))
			throw new RuntimeException();
	}
	

	public void setPosition(Vector v) {
		pos.x = v.x;
		pos.y = v.y;
		if (Double.isNaN(pos.x) || Double.isNaN(pos.y))
			throw new RuntimeException();
	}
	
	//An activated particle is excited and can undergo further chemical reactions
	public void setActivated(boolean state) {
		activated = state;
	}
	
	public boolean getActivated() {
		return activated;
	}
}
