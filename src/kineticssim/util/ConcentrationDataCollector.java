package kineticssim.util;

import java.awt.Color;

import kineticssim.Compound;

//Subclass of datacollector, specifically collects data on how concentrated molecules are.
public class ConcentrationDataCollector extends DataCollector {

	public Compound comp;
	
	public ConcentrationDataCollector(int i_size, Compound i_comp, Scale sc_i) {
		super(i_size, i_comp.getcolor(), sc_i);
		comp = i_comp;
	}

}
