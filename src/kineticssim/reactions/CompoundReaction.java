package kineticssim.reactions;

import java.awt.Color;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import kineticssim.Compound;
import kineticssim.util.ConcentrationDataCollector;
import kineticssim.util.Scale;

//A more "abstract" reaction class designed to be used in the GUI and converted to actual Reactions when the user wants to run a simulation.
//For example, A -> B is more complicated than it sounds because it involves some sub-reactions.
public class CompoundReaction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9036411905734330087L;
	//private List<Reaction> reactions;
	private List<Compound> reactants;
	private List<Compound> products;
	private double dH;
	private double eA;
	private double rfwd;
	private double rback;
	private boolean reversible;
	
	public double getDeltaH() {
		return dH;
	}

	public void setDeltaH(double dH) {
		this.dH = dH;
	}

	public double getActivationEnergy() {
		return eA;
	}

	public void setActivationEnergy(double eA) {
		this.eA = eA;
	}
	
	public void setReversible(boolean reversible) {
		this.reversible = reversible;
	}
	
	public boolean isReversible() {
		return reversible;
	}

	public CompoundReaction() {
		reactants = new ArrayList<Compound>();
		products = new ArrayList<Compound>();
		rfwd = 1E11;
		rback = 1E11;
		eA = 1E-19;
		dH = -1E-19;
		reversible = true;
	}
	
	public List<Compound> getreactants() {
		return reactants;
	}
	
	public List<Compound> getproducts() {
		return products;
	}
	
	public double getRateFwd() {
		return rfwd;
	}
	
	public double getRateBack() {
		return rback;
	}
	
	public void setRateFwd(double k) {
		rfwd = k;
	}
	
	public void setRateBack(double k) {
		rback = k;
	}
	

	public void toFileData(PrintWriter w, List<Compound> compounds) {
		w.println(Double.toString(dH));
		w.println(Double.toString(eA));
		w.println(Double.toString(rfwd));
		w.println(Double.toString(rback));
		w.println(Boolean.toString(reversible));
		w.println(compounds.indexOf(reactants.get(0)));
		if (reactants.size() > 1)
			w.println(compounds.indexOf(reactants.get(1)));
		else
			w.println("N");
		w.println(compounds.indexOf(products.get(0)));
		if (products.size() > 1)
			w.println(compounds.indexOf(products.get(1)));
		else
			w.println("N");
	}
	
	public static CompoundReaction fromFileData(Scanner in, List<Compound> compounds) {
		CompoundReaction r = new CompoundReaction();
		r.dH = Double.valueOf(in.nextLine());
		r.eA = Double.valueOf(in.nextLine());
		r.rfwd = Double.valueOf(in.nextLine());
		r.rback = Double.valueOf(in.nextLine());
		r.reversible = Boolean.valueOf(in.nextLine());
		r.reactants.add(compounds.get(Integer.valueOf(in.nextLine())));
		String s = in.nextLine();
		if (!s.equalsIgnoreCase("N"))
			r.reactants.add(compounds.get(Integer.valueOf(s)));
		r.products.add(compounds.get(Integer.valueOf(in.nextLine())));
		s = in.nextLine();
		if (!s.equalsIgnoreCase("N"))
			r.products.add(compounds.get(Integer.valueOf(s)));
		
		return r;
	}
	
	@Override
	public String toString() {
		if (reactants.size() == 0)
			return "-";

		if (products.size() == 0)
			return "-";
		
		
		String s = "";
		for (Compound c: reactants) {
			s += " + ";
			s += c.getname();
		}
		s = s.substring(3);
		s += " -> ";
		for (Compound c: products) {
			s += c.getname();
			s += " + ";
		}
		return s.substring(0, s.length() - 3);
	}
	
	//Convert it to the component reactions that makes up a compound reaction
	public void convertToReactions(Collection<Reaction> l) {
		
		
		if (reactants.size() == 1 && products.size() == 1) {
			
			UnimolecularTransformation LHdecomp = new UnimolecularTransformation(0, -eA+dH, rfwd);
			LHdecomp.prod = products.get(0);
			LHdecomp.c = reactants.get(0);
			
			LindemannHinshelwoodEq LHeq = new LindemannHinshelwoodEq(eA, eA);
			LHeq.A = reactants.get(0);

			l.add(LHdecomp);
			l.add(LHeq);
			
			if (reversible) {
				UnimolecularTransformation LHdecomp2 = new UnimolecularTransformation(0, -eA, rback);
				LHdecomp2.prod = reactants.get(0);
				LHdecomp2.c = products.get(0);

				LindemannHinshelwoodEq LHeq2 = new LindemannHinshelwoodEq(eA-dH, eA-dH);
				LHeq2.A = products.get(0);


				l.add(LHdecomp2);
				l.add(LHeq2);
			}
		} if (reactants.size() == 1 && products.size() == 2) {
			if (reversible) {
				BimolecularSynthesis bc = new BimolecularSynthesis(eA-dH, -dH, rback);
				bc.ra = products.get(0);
				bc.rb = products.get(1);
				bc.prod = reactants.get(0);
				l.add(bc);
			}

			UnimolecularDecomposition LHdecomp = new UnimolecularDecomposition(0, -eA+dH, rfwd);
			LHdecomp.prodA = products.get(0);
			LHdecomp.prodB = products.get(1);
			LHdecomp.c = reactants.get(0);
			LHdecomp.calcReducedMass();

			LindemannHinshelwoodEq LHeq = new LindemannHinshelwoodEq(eA, eA);
			LHeq.A = LHdecomp.c;
			
			l.add(LHdecomp);
			l.add(LHeq);
		} if (reactants.size() == 2 && products.size() == 1) {

			BimolecularSynthesis bc = new BimolecularSynthesis(eA, dH, rfwd);
			bc.ra = reactants.get(0);
			bc.rb = reactants.get(1);
			bc.prod = products.get(0);
			l.add(bc);


			if (reversible) {
				UnimolecularDecomposition LHdecomp = new UnimolecularDecomposition(0, -eA, rback);
				LHdecomp.prodA = reactants.get(0);
				LHdecomp.prodB = reactants.get(1);
				LHdecomp.c = products.get(0);
				LHdecomp.calcReducedMass();

				LindemannHinshelwoodEq LHeq = new LindemannHinshelwoodEq(eA-dH, eA-dH);
				LHeq.A = LHdecomp.c;

				l.add(LHdecomp);
				l.add(LHeq);
			}

		} if (reactants.size() == 2 && products.size() == 2) {
			BimolecularReaction r1 = new BimolecularReaction(eA, dH, rfwd);
			r1.ra = reactants.get(0);
			r1.rb = reactants.get(1);
			r1.prodA = products.get(0);
			r1.prodB = products.get(1);
			r1.calcReducedMass();
			l.add(r1);

			if (reversible) {
				BimolecularReaction r2 = new BimolecularReaction(eA-dH, -dH, rback);
				r2.prodA = reactants.get(0);
				r2.prodB = reactants.get(1);
				r2.ra = products.get(0);
				r2.rb = products.get(1);
				r2.calcReducedMass();
				l.add(r2);
			}
		}
	}
}
