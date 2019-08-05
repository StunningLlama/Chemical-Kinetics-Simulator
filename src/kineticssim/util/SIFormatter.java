package kineticssim.util;

//This class formats numbers with SI prefixes, for example 4 mL.
public class SIFormatter {
	
	static String[] prefixes = {"y", "z", "a", "f", "p", "n", "\u00B5", "m", "", "k", "M", "G", "T", "P", "E", "Z", "Y"};
	
	public static String format(String unit, double value, int digits) {
		
		//Calculate exponent and get prefix from that exponent
		int exp = ((int)Math.log10(Math.abs(value)) + 299) /3 - 100;
		if (exp <= -8)
			exp = -8;
		if (exp >= 8)
			exp = 8;
		return String.format("%." + digits + "f", (value / Math.pow(10, exp*3))) + " " + prefixes[exp+8] + unit;
	}
}
