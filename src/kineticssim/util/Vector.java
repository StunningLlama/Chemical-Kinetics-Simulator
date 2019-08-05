package kineticssim.util;
/*****
 * Chemical Kinetics Simulator
 * @author Brandon
 * E block
 *****/

//Useful utility class for vector computations, necessary in physics
//Many functions are self explanatory
public class Vector {
	
	//Vectors are mutable because they need to be changed in some circumstances.
	public double x;
	public double y;
	
	public Vector(double xi, double yi) {
		x = xi;
		y = yi;
	}

	public Vector(Vector v) {
		x = v.x;
		y = v.y;
	}
	
	public static Vector copy(Vector v) {
		if (v == null)
			return null;
		return new Vector(v);
	}

	public Vector() {
		this.x = 0.0;
		this.y = 0.0;
	}
	
	public void set(Vector a) {
		x = a.x;
		y = a.y;
	}
	
	public static double cross(Vector a, Vector b) {
		return (a.x * b.y - a.y * b.x);
	}

	public static double dot(Vector a, Vector b) {
		return a.x*b.x + a.y*b.y;
	}
	
	public static double mag(Vector a) {
		return Math.sqrt(a.x*a.x + a.y*a.y);
	}
	
	//Project vector onto another vector
	public static Vector proj(Vector a, Vector b) {
		if (b.mag() == 0)
			return new Vector();
		return Vector.mul(b, Vector.dot(a, b)/b.magsq());
	}
	
	public static Vector err(Vector a, Vector b) {
		if (b.mag() == 0)
			return new Vector();
		return Vector.sub(a, Vector.mul(b, Vector.dot(a, b)/b.magsq()));
	}
	
	//Magnitude
	public double mag() {
		return Math.sqrt(x*x + y*y);
	}

	//Magnitude squared
	public double magsq() {
		return x*x + y*y;
	}

	public static double zeroIfNaN(double a) {
		if (Double.isFinite(a))
			return a;
		return 0;
	}
	
	public static double angle(Vector a, Vector b) {
		double dotprod = Vector.dot(a, b);
		return zeroIfNaN(Math.toDegrees(Math.acos(dotprod/(a.mag() * b.mag()))));
	}
	
	public static Vector add(Vector a, Vector b) {
		return new Vector(a.x+b.x, a.y+b.y);
	}

	public static Vector sub(Vector a, Vector b) {
		return new Vector(a.x-b.x, a.y-b.y);
	}
	
	public static Vector add(Vector a, Vector b, Vector c) {
		return new Vector(a.x+b.x+c.x, a.y+b.y+c.y);
	}
	
	public void add(Vector a) {
		x += a.x;
		y += a.y;
	}
	
	public void add(double xi, double yi) {
		x += xi;
		y += yi;
	}

	public Vector mul(double a) {
		x *= a;
		y *= a;
		return this;
	}

	public static Vector mul(Vector a, double b) {
		return new Vector(a.x*b, a.y*b);
	}

	public static Vector unitvector(Vector a) {
		if (a.mag() == 0)
			return new Vector(0.0, 0.0);
		return Vector.mul(a, 1.0/a.mag());
	}

	public Vector unitvector() {
		return Vector.unitvector(this);
	}
	
	public static double distsq(Vector a, Vector b) {
		return (a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y);
	}

	public static double dist(Vector a, Vector b) {
		return Math.sqrt(Vector.distsq(a, b));
	}
	
	public boolean isNaN() {
		return !Double.isFinite(x) || !Double.isFinite(y);
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof Vector && ((Vector)o).x == this.x && ((Vector)o).y == this.y;
	}
}