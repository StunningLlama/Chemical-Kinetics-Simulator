package kineticssim.gui;
import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JFrame;

import kineticssim.DisplayRefreshThread;
import kineticssim.KineticsSimulator;

/*****
 * Chemical Kinetics Simulator
 * @author Brandon
 * E block
 *****/


//Container class that contains the entire GUI
public class SimulationGUI extends JFrame {
	private KineticsSimulator application;
	private MoleculeRenderer renderer;
	private int renderwidth = 1200;
	private int renderheight = 700;
	private int histogramwidth = 600;
	private int histogramheight = 300;
	private int graphwidth = 600;
	private int graphheight = 300;

	//Various getters and setters
	
	public int getRenderwidth() {
		return renderwidth;
	}

	public void setRenderwidth(int renderwidth) {
		this.renderwidth = renderwidth;
	}

	public int getRenderheight() {
		return renderheight;
	}

	public void setRenderheight(int renderheight) {
		this.renderheight = renderheight;
	}

	public int getHistogramwidth() {
		return histogramwidth;
	}

	public void setHistogramwidth(int histogramwidth) {
		this.histogramwidth = histogramwidth;
	}

	public int getHistogramheight() {
		return histogramheight;
	}

	public void setHistogramheight(int histogramheight) {
		this.histogramheight = histogramheight;
	}

	public int getGraphwidth() {
		return graphwidth;
	}

	public void setGraphwidth(int graphwidth) {
		this.graphwidth = graphwidth;
	}

	public int getGraphheight() {
		return graphheight;
	}

	public void setGraphheight(int graphheight) {
		this.graphheight = graphheight;
	}



	private DisplayRefreshThread refreshthread;
	private ControlPanel controlpanel;
	
	//Intialize the GUi and set size
	public SimulationGUI(KineticsSimulator parent) {
		application = parent;

		this.setVisible(true);
		this.setTitle("Kinetics Simulator");
		this.setSize(1800, 1080);
		
		this.setLayout(new BorderLayout());
		renderer = new MoleculeRenderer(this);
		renderer.setSize(1800, 1000);
		this.add(renderer, BorderLayout.WEST);
		controlpanel = new ControlPanel(application);
		this.add(controlpanel, BorderLayout.CENTER);
		//this.pack();
		
		refreshthread = new DisplayRefreshThread(this);
		Thread t = new Thread(refreshthread);
		t.start();
	}
	
	public KineticsSimulator getSimulator() {
		return application;
	}
	
	public ControlPanel getControlPanel() {
		return controlpanel;
	}
	
	
	public MoleculeRenderer getRenderer() {
		return this.renderer;
	}
}
