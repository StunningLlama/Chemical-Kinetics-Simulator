package kineticssim.gui;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

import kineticssim.Compound;
import kineticssim.KineticsSimulator;
import kineticssim.Particle;

//The control panel is on the right and has sliders and buttons to control the simulation as well as information
public class ControlPanel extends JPanel implements ActionListener, ItemListener {
	//JTextArea info;
	public JScrollBar temperature;
	public JScrollBar timestep;
	public JScrollBar datacollectioninterval;
	public JScrollBar iterations;
	public JScrollBar width;
	public JScrollBar height;
	public JLabel l3;
	public JLabel l4;
	public JLabel l5;
	public JLabel l5d;
	public JLabel l5i;
	public JLabel l5w;
	public JLabel l5h;
	JButton reset;
	JButton add;
	JButton remove;
	JButton openEditor;
	JButton closeEditor;
	JSpinner number;
	CompoundKey key;
	public Choice c;
	private KineticsSimulator sim;
	public JCheckBox settemp;
	
	public ControlPanel(KineticsSimulator k) {
		sim = k;
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		//info = new JTextArea("Hello");
		//info.setPreferredSize(new Dimension(300, 200));
		
		JPanel ps = new JPanel();
		ps.setLayout(new BoxLayout(ps, BoxLayout.Y_AXIS));
		//ps.add(info, BorderLayout.CENTER);
		key = new CompoundKey(this);
		key.setAlignmentX(CENTER_ALIGNMENT);
		ps.add(key);
		p.add(ps);
		
		p.add(new JLabel(" "));
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		temperature = new JScrollBar(JScrollBar.HORIZONTAL, 60, 10, 0, 90+10);
		temperature.setPreferredSize(new Dimension(200, 18));
		//number = new JScrollBar(JScrollBar.HORIZONTAL, 50, 10, 0, 100+10);
		//number.setPreferredSize(new Dimension(200, 18));
		number = new JSpinner();
		JLabel l1 = new JLabel("Temperature");
		l1.setPreferredSize(new Dimension(75, 18));
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.add(l1);
		p1.add(new JLabel("     "));
		p1.add(temperature);
		p1.add(new JLabel("     "));
		l3 = new JLabel("");
		settemp = new JCheckBox("Set Temperature");
		p1.add(settemp);
		p1.add(new JPanel());
		p1.add(l3);
		
		
		

		timestep = new JScrollBar(JScrollBar.HORIZONTAL, 60, 10, 0, 100+10);
		timestep.setPreferredSize(new Dimension(200, 18));
		JPanel p4 = new JPanel();
		JLabel l3 = new JLabel("Timestep");
		p4.setLayout(new BoxLayout(p4, BoxLayout.X_AXIS));
		p4.add(l3);
		p4.add(new JLabel("     "));
		p4.add(timestep);
		p4.add(new JLabel("     "));
		l5 = new JLabel("");
		p4.add(new JPanel());
		p4.add(l5);
		

		iterations = new JScrollBar(JScrollBar.HORIZONTAL, 1, 4, 1, 20+4);
		iterations.setPreferredSize(new Dimension(200, 18));
		JPanel p4i = new JPanel();
		JLabel l3i = new JLabel("Iterations");
		p4i.setLayout(new BoxLayout(p4i, BoxLayout.X_AXIS));
		p4i.add(l3i);
		p4i.add(new JLabel("     "));
		p4i.add(iterations);
		p4i.add(new JLabel("     "));
		l5i = new JLabel("");
		p4i.add(new JPanel());
		p4i.add(l5i);
		

		datacollectioninterval = new JScrollBar(JScrollBar.HORIZONTAL, 70, 10, 0, 100+10);
		datacollectioninterval.setPreferredSize(new Dimension(200, 18));
		JPanel p4d = new JPanel();
		JLabel l3d = new JLabel("Data Collection Interval");
		p4d.setLayout(new BoxLayout(p4d, BoxLayout.X_AXIS));
		p4d.add(l3d);
		p4d.add(new JLabel("     "));
		p4d.add(datacollectioninterval);
		p4d.add(new JLabel("     "));
		l5d = new JLabel("");
		p4d.add(new JPanel());
		p4d.add(l5d);
		

		width = new JScrollBar(JScrollBar.HORIZONTAL, 50, 10, 0, 100+10);
		width.setPreferredSize(new Dimension(200, 18));
		JPanel p4w = new JPanel();
		JLabel l3w = new JLabel("Width");
		p4w.setLayout(new BoxLayout(p4w, BoxLayout.X_AXIS));
		p4w.add(l3w);
		p4w.add(new JLabel("     "));
		p4w.add(width);
		p4w.add(new JLabel("     "));
		l5w = new JLabel("");
		p4w.add(new JPanel());
		p4w.add(l5w);


		height = new JScrollBar(JScrollBar.HORIZONTAL, 50, 10, 0, 100+10);
		height.setPreferredSize(new Dimension(200, 18));
		JPanel p4h = new JPanel();
		JLabel l3h = new JLabel("Height");
		p4h.setLayout(new BoxLayout(p4h, BoxLayout.X_AXIS));
		p4h.add(l3h);
		p4h.add(new JLabel("     "));
		p4h.add(height);
		p4h.add(new JLabel("     "));
		l5h = new JLabel("");
		p4h.add(new JPanel());
		p4h.add(l5h);

		
		
		
		
		
		
		
		
		
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		JLabel l2 = new JLabel("Number");
		l2.setPreferredSize(new Dimension(100, 18));
		p2.add(l2);
		p2.add(number);
		c = new Choice();
		c.addItemListener(this);
		p2.add(new JLabel("      "));
		p2.add(c);
		number.setPreferredSize(new Dimension(30, 18));
		p2.add(new JPanel());
		l4 = new JLabel("");
		p.add(new JLabel(" "));
		p.add(p1);
		p.add(new JLabel(" "));
		p.add(p2);
		p.add(new JLabel(" "));
		p.add(new JLabel(" "));
		p.add(new JLabel(" "));
		p.add(p4);
		p.add(new JLabel(" "));
		p.add(p4i);
		p.add(new JLabel(" "));
		p.add(p4d);
		p.add(new JLabel(" "));
		p.add(new JLabel(" "));
		p.add(new JLabel(" "));
		p.add(p4w);
		p.add(new JLabel(" "));
		p.add(p4h);
		p.add(new JLabel(" "));
		p.add(new JLabel(" "));
		p.add(new JLabel(" "));
		reset = new JButton("Reset");
		reset.addActionListener(this);
		remove = new JButton("Remove");
		remove.addActionListener(this);
		add = new JButton("Add");
		add.addActionListener(this);
		p2.add(add);
		p2.add(remove);


		openEditor = new JButton("Open Editor");
		openEditor.addActionListener(this);
		closeEditor = new JButton("Close Editor");
		closeEditor.addActionListener(this);
		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
		p3.add(new JLabel("Simulation"));
		p3.add(Box.createHorizontalGlue());
		p3.add(reset);
		p3.add(openEditor);
		p3.add(closeEditor);
		
		p.add(p3);
		//p.add(new JLabel(" "));
		//JPanel p4 = new JPanel();
		//p4.setLayout(new BoxLayout(p4, BoxLayout.X_AXIS));
		//p4.add(new JLabel("Set temperature"));
		

		
		
		this.setLayout(new BorderLayout());
		this.add(p, BorderLayout.NORTH);
	}
	
	public void repaintKey() {
		key.repaint();
	}
	
	public void setText(String text) {
		key.setText(text);
	}

	Random rand = new Random();
	
	public KineticsSimulator getSim() {
		return sim;
	}
	
	//This code controls the buttons in the control panel
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == add) {
			//Add particles to the simulation
			synchronized(sim.getPhysicsSimulation()) {
				Compound comp = sim.getPhysicsSimulation().compounds.get(c.getSelectedIndex());
				System.out.println(comp.getname());
				sim.addRandomParticles((Integer)number.getValue(), comp, sim.getPhysicsSimulation().targettemp);
				System.out.println(comp.getmass() + " and " + comp.getradius() + " and " + comp.getname());
				System.out.println(sim.getPhysicsSimulation().targettemp);
				System.out.println(sim.getPhysicsSimulation().getParticles().size());
				System.out.println(sim.getPhysicsSimulation().getParticles().get(0).getPosition());
				System.out.println(sim.getPhysicsSimulation().getParticles().get(0).getVelocity());
				
				sim.getPhysicsSimulation().calculateAvgMass();
				sim.getPhysicsSimulation().calculateAvgRadius();
			}
		} else if (e.getSource() == remove) {
			//Remove particles from the simulation
			synchronized(sim.getPhysicsSimulation()) {
				Compound comp = sim.getPhysicsSimulation().compounds.get(c.getSelectedIndex());
				int toremove = (Integer)number.getValue();
				List<Particle> col = sim.getPhysicsSimulation().particlecount.get(comp);
				int sizeinit = col.size();
				if (toremove > col.size())
					toremove = col.size();
				if (toremove > 0 && toremove <= col.size()) {
					for (int i = sizeinit; i > (sizeinit - toremove); i--) {
						int rr = rand.nextInt(i);
						sim.getPhysicsSimulation().removeParticle(col.get(rr));

					}
				}
				sim.getPhysicsSimulation().calculateAvgMass();
				sim.getPhysicsSimulation().calculateAvgRadius();
			}
		} else if (e.getSource() == reset) {
			//Reset simulation and load in new params
			System.out.println("HEY");
			sim.setUp();
		} else if (e.getSource() == openEditor) {
			sim.getReactionManager().setVisible(true);
		} else if (e.getSource() == closeEditor) {
			sim.getReactionManager().setVisible(false);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		
	}
	
	
}
