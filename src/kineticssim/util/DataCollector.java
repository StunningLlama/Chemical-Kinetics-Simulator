package kineticssim.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

//The data collector collects data and displays it in a line graph over time
public class DataCollector {
	public double[] timeTable;
	public double[] dataTable;
	
	public int size;
	public Color c;
	
	private int index = 0;
	
	private boolean scrolling = true;
	
	public double xscale;
	public Scale yscale;
	
	public DataCollector(int i_size, Color c_i, Scale yscale_i) {
		size = i_size;
		timeTable = new double[size];
		dataTable = new double[size];
		
		for (int i = 0; i < size; i++) {
			timeTable[i] = 0;
			dataTable[i] = 0;
		}
		c = c_i;
		xscale = 1;
		yscale = yscale_i;
	}
	
	//This function draws the graph onto a Graphics g
	public void graph(Graphics2D g, int xA, int yA, int xB, int yB) {
		g.setColor(c);
		//g.setStroke(new BasicStroke(2));
		for (int i = 0; i < index - 1; i++) {
			int x1 = xA + (int)(((timeTable[i] - timeTable[0])/xscale/size) * (xB-xA));
			int x2 = xA + (int)(((timeTable[i+1] - timeTable[0])/xscale/size) * (xB-xA));
			int y1 = yB - (int)((dataTable[i]/yscale.getMax()) * (yB-yA));
			int y2 = yB - (int)((dataTable[i+1]/yscale.getMax()) * (yB-yA));
			//System.out.println(x1 + " " + x2 + " " + y1 + " " + y2);
			g.drawLine(x1, y1, x2, y2);
		}
	}
	
	//Adds a data point to the table.
	public void addDataPoint(double time, double value) {
		if (index == size)
		{
			if (scrolling) {
				for (int i = 0; i < size - 1; i++) {

					timeTable[i] = timeTable[i + 1];
					dataTable[i] = dataTable[i + 1];
					timeTable[size - 1] = time;
					dataTable[size - 1] = value;
					xscale = (timeTable[index-1]-timeTable[0])/size;
					if (value > yscale.getMax()) {
						yscale.setMax(value);
					}
				}
				return;
			} else {
				return;
			}
		}
		timeTable[index] = time;
		dataTable[index] = value;
		index++;
		xscale = (timeTable[index-1]-timeTable[0])/size;
		if (value > yscale.getMax()) {
			yscale.setMax(value);
		}
	}
}
