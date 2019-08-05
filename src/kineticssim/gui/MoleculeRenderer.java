package kineticssim.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import javax.swing.JPanel;

import kineticssim.Particle;
import kineticssim.PhysicsSimulation;
import kineticssim.util.DataCollector;
import kineticssim.util.Vector;

/*****
 * Chemical Kinetics Simulator
 * @author Brandon
 * E block
 *****/

//This renders the visualization of gas molecules themselves as well as the graphs.
public class MoleculeRenderer extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3919792069124979049L;
	BufferedImage img_particledisplay;
	BufferedImage img_distr;
	BufferedImage img_graph;
	PhysicsSimulation simulation;
	SimulationGUI frame;
	
	private List<Vector> heads = new ArrayList<Vector>();
	private List<Vector> tails = new ArrayList<Vector>();
	
	public void drawVec(Vector from, Vector delta) {
		tails.add(from);
		heads.add(Vector.add(from, delta));
	}
	
	public void removeVec(int id) {
		heads.remove(id);
		tails.remove(id);
	}
	
	public MoleculeRenderer(SimulationGUI gui) {
		simulation = gui.getSimulator().getPhysicsSimulation();
		frame = gui;
		img_particledisplay = (BufferedImage) gui.createImage(Math.max(gui.getRenderwidth(), gui.getHistogramwidth() + gui.getGraphwidth()), gui.getRenderheight() + Math.max(gui.getHistogramheight(), gui.getGraphheight()));
		img_distr = (BufferedImage) gui.createImage(gui.getHistogramwidth(), gui.getHistogramheight());
		img_graph = (BufferedImage) gui.createImage(gui.getGraphwidth(), gui.getGraphheight());
		this.setPreferredSize(new Dimension(img_particledisplay.getWidth(), img_particledisplay.getHeight()));
	}
	//640 300
	public double transform(double x, double st, double end, double size) {
		return (x-st)/(st+end) * size;
	}
	
	//We render the particles and graphs onto bufferedimages, which will then be rendered onto the graphics
	@Override
	public void paintComponent(Graphics g) {
		
		if (!simulation.simulating)
			return;
		
		synchronized(simulation) {
		
		Graphics ig = img_particledisplay.getGraphics();
		ig.setColor(Color.BLACK);
		ig.fillRect(0, 0, img_particledisplay.getWidth(), img_particledisplay.getHeight());
		Color c = Color.WHITE;
		//try {
		for (Particle p: simulation.getParticles()) {
			if (p.getActivated())
				c = p.getCompound().getcolor().brighter();
			else
				c = p.getCompound().getcolor();
			ig.setColor(c);
			double rad = p.getCompound().getradius();
			int xt = (int) transform((p.getX() - rad), simulation.getboundTL().x, simulation.getboundBR().x, frame.getRenderwidth());
			int yt = (int) transform((p.getY() - rad), simulation.getboundTL().y, simulation.getboundBR().y, frame.getRenderheight());
			int xr = (int) transform((p.getX() + rad), simulation.getboundTL().x, simulation.getboundBR().x, frame.getRenderwidth());
			int yr = (int) transform((p.getY() + rad), simulation.getboundTL().y, simulation.getboundBR().y, frame.getRenderheight());
			
			ig.fillOval((int) xt, (int) yt, (int) (xr-xt), (int) (yr-yt));
		}	
		

		//Vector drawing that is not used
		for (int i = 0; i < heads.size(); i++) {
			Vector h = heads.get(i);
			Vector t = tails.get(i);
			ig.setColor(Color.GREEN);
			
			ig.drawLine((int)t.x, (int)t.y, (int)h.x, (int)h.y);
			ig.setColor(Color.BLUE);
			ig.drawString("H", (int)h.x, (int)h.y);
			ig.drawString("T", (int)t.x, (int)t.y);
		}
		
		Graphics ggraph = img_distr.getGraphics();
		ggraph.clearRect(0, 0, 1280, 300);
		
		ggraph.setColor(Color.red);
		
		/*********
		 * ******
		 */
		simulation.getHistogram().draw(ggraph, simulation);
		
		Graphics gplot = img_graph.getGraphics();
		gplot.setColor(Color.BLACK);
		gplot.fillRect(0, 0, img_graph.getWidth(), img_graph.getHeight());
		
		for (DataCollector d: simulation.getGraphs()) {
			d.graph(gplot, 0, 0, img_graph.getWidth(), img_graph.getHeight());
		}
		
		
		g.drawImage(img_particledisplay, 0, 0, this);
		g.drawImage(img_distr, 0, 700, this);
		g.drawImage(img_graph, 600, 700, this);
		}
	}
}
