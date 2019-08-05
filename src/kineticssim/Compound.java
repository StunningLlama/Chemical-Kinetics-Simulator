package kineticssim;
import java.awt.Color;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;

/*****
 * Chemical Kinetics Simulator
 * @author Brandon
 * E block
 *****/


//This class represents a type of chemical compound, with its associated variables like how massive it is, its radius, color, etc.
public class Compound implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2675832852079792543L;


	private double mass;
	private double radius;
	private Color color;
	private String name;
	
	public Compound(double imass, double iradius, Color col, String iname) {
		mass = imass;
		radius = iradius;
		color = col;
		name = iname;
	}
	

	//Various getters and setters
	
	public double getmass() {
		return mass;
	}
	
	public double getradius() {
		return radius;
	}
	
	public Color getcolor() {
		return color;
	}
	
	public String getname() {
		return name;
	}
	
	public void setmass(double imass) {
		mass = imass;
	}
	
	public void setcolor(Color icolor) {
		color = icolor;
	}
	

	public void setradius(String iname) {
		name = iname;
	}
	

	public void setradius(double iradius) {
		radius = iradius;
	}
	
	@Override
	public boolean equals(Object c) {
		if (c instanceof Compound)
			return ((Compound) c).getname().equals(this.getname());
		return false;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public void toFileData(PrintWriter w) {
		w.println(Double.toString(mass));
		w.println(Double.toString(radius));
		w.println(Integer.toHexString(color.getRGB()).substring(2));
		w.println(name);
	}
	
	public static Compound fromFileData(Scanner in) {
		Compound c = new Compound(0, 0, Color.BLACK, "");
		c.mass = Double.valueOf(in.nextLine());
		c.radius = Double.valueOf(in.nextLine());
		c.color = new Color((int)(long)Long.valueOf(in.nextLine(), 16));
		c.name = in.nextLine();
		return c;
	}
	
	public Compound copy() {
		Compound c = new Compound(mass, radius, color, name);
		return c;
	}
}
