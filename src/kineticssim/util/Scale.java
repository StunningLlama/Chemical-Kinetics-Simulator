package kineticssim.util;

//The purpose of this class is to synchronize the y-axis of many graphs at the same time
public class Scale {
	private double maximum = 0.0;
	
	public double getMax() {
		return maximum;
	}
	
	public void setMax(double max) {
		maximum = max;
	}
	
	public void reset() {
		maximum = 0;
	}
}
