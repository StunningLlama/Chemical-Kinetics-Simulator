package kineticssim.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import kineticssim.PhysicsSimulation;

//Class for the histogram of the velocities
public class Histogram {
	
	private int histogramsize = 0;
	private double histogramdiv = 0.5e-10;

	private int[] histogram;
	
	
	public Histogram(int size_i) {
		histogramsize = size_i;

		histogram = new int[histogramsize];
		for (int i = 0; i < histogramsize; i++) {
			histogram[i] = 0;
		}
	}
	
	//Various getters and setters, and functions to increase histogram
	
	public void setHistogramDiv(double div) {
		histogramdiv = div;
	}
	
	public double getHistogramDiv() {
		return histogramdiv;
	}
	
	public int getSize() {
		return this.histogramsize;
	}
	
	public void setHistogram(int index, int value) {
		histogram[index] = value;
	}
	
	public double getHistogram(int index) {
		return histogram[index];
	}

	public void incHistogram(int index) {
		histogram[index]++;
	}
	
	//Draws the histogram onto a Graphics object
	public void draw(Graphics2D ggraph, PhysicsSimulation simulation, int graphheight) {
		int max = 10;
		for (int i = 0; i < histogramsize; i++) {
			if (histogram[i] > max) {
				max = histogram[i];	
			}
		}
		

		
		for (int i = 0; i < histogramsize; i++) {
			ggraph.drawRect(i*20, graphheight - 150*histogram[i]/max, 20, 150*histogram[i]/max);
			
		}
		ggraph.setColor(Color.GREEN);
		double temp = simulation.computetemperature();
		for (int i = 0; i < histogramsize; i++) {
			double left = histogramdiv * simulation.maxwellboltzmann((i + 0.5)*histogramdiv, temp, simulation.avgmass);
			double right = histogramdiv * simulation.maxwellboltzmann((i+1.5)*histogramdiv, temp, simulation.avgmass);
			ggraph.drawLine((int)(i*20), graphheight - (int)(left*simulation.getParticles().size()*150/max), (int)((i+1)*20), graphheight - (int)(right*simulation.getParticles().size()*150/max));
		}
	}
}
