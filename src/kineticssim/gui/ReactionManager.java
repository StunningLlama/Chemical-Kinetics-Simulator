package kineticssim.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;

import kineticssim.Compound;
import kineticssim.PhysicsSimulation;
import kineticssim.reactions.CompoundReaction;
import kineticssim.reactions.Reaction;

/*
 * 	A -> C
	A + B -> C
	A -> C + D
	A + B -> C + D
 */


//The window that allows you to add and modify reactions
public class ReactionManager extends JFrame implements ActionListener, ItemListener {
	private Choice reactiontype;
	Button b;
	List l;
	Panel reaction;
	Choice cr;
	Choice ca;
	Choice cb;
	Choice cc;
	Choice cd;
	/*JSpinner ta;
	JSpinner tb;
	JSpinner tc;
	JSpinner td;
	*/
	
	Button b1;
	Button b2;
	Button b3;
	Button b4;
	Button b5;
	Button b6;
	
	Button selectcolor;
	
	TextField tta;
	TextField ttb;
	TextField ttc;
	TextField ttd;

	TextField tte;
	TextField ttf;
	TextField ttg;
	TextField tth;
	
	JLabel text5;
	JLabel text6;
	JLabel text7;
	JLabel text8;
	
	Checkbox chk;
	
	JMenuBar mb;
	JMenuItem mia;
	JMenuItem mib;
	JMenuItem mic;
	
	public java.util.List<CompoundReaction> reactions;
	public java.util.List<Compound> compounds;
	
	public ReactionManager() {
		
		//We use awt/swing for the GUI here
		
		reactions = new java.util.ArrayList<CompoundReaction>();
		
		this.setLayout(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel compoundpanel = new JPanel();
		tabbedPane.addTab("Compounds", compoundpanel);
		JPanel reactionpanel = new JPanel();
		tabbedPane.addTab("Reactions", reactionpanel);
		l = new List();
		//l.setFont(Font);
		l.addItemListener(this);
		mb = new JMenuBar();
		JMenu jm = new JMenu("File");
		mia = new JMenuItem("Save");
		mib = new JMenuItem("Load");
		mic = new JMenuItem("About");
		jm.add(mia);
		jm.add(mib);
		jm.add(mic);
		mia.addActionListener(this);
		mib.addActionListener(this);
		mic.addActionListener(this);
		mb.add(jm);
		
		this.setJMenuBar(mb);
		
		
		
		
		cr = new Choice();
		
		cr.add("A -> C");
		cr.add("A + B -> C");
		cr.add("A -> C + D");
		cr.add("A + B -> C + D");
		
		cr.addItemListener(this);
		cr.select(3);
		//u2192
		//cr.setFont(new Font("Serif", Font.PLAIN, 40));
		ca = new Choice();
		cb = new Choice();
		cc = new Choice();
		cd = new Choice();
		ctypes.add(ca);
		ctypes.add(cb);
		ctypes.add(cc);
		ctypes.add(cd);
		//cc.setSize(800, 90);
		//ta = new JSpinner();
		//tb = new JSpinner();
		//tc = new JSpinner();
		//td = new JSpinner();
		text5 = new JLabel("Reactant A");
		text6= new JLabel("Reactant B");
		text7 = new JLabel("Product C");
		text8 = new JLabel("Product D");
		JLabel text9 = new JLabel("Reaction Type");
		JLabel text10 = new JLabel("Activation Energy (kJ/mol)");
		JLabel text11 = new JLabel("Delta H (kJ/mol)");
		JLabel text12 = new JLabel("K forward");
		JLabel text13 = new JLabel("K backward");
		chk = new Checkbox("Reversible");
		
		tte = new TextField();
		ttf = new TextField();
		ttg = new TextField();
		tth = new TextField();
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		reactionpanel.setLayout(new BorderLayout());
		
		ca.setPreferredSize(new Dimension(150, 24));
		cb.setPreferredSize(new Dimension(150, 24));
		cc.setPreferredSize(new Dimension(150, 24));
		cd.setPreferredSize(new Dimension(150, 24));
		//ta.setPreferredSize(new Dimension(200, 32));
		//tb.setPreferredSize(new Dimension(200, 32));
		//tc.setPreferredSize(new Dimension(200, 32));
		//td.setPreferredSize(new Dimension(200, 32));
		panel1.add(text9);
		panel1.add(cr);
		panel1.add(text5);
		panel1.add(ca);
		//panel1.add(ta);
		panel1.add(text6);
		panel1.add(cb);
		//panel1.add(tb);
		panel1.add(text7);
		panel1.add(cc);
		//panel1.add(tc);
		panel1.add(text8);
		panel1.add(cd);
		//panel1.add(td);
		panel1.add(text10);
		panel1.add(tte);
		panel1.add(text11);
		panel1.add(ttf);
		panel1.add(text12);
		panel1.add(ttg);
		panel1.add(text13);
		panel1.add(tth);
		panel1.add(chk);
		
		b1 = new Button("Add");
		b1.addActionListener(this);
		b2 = new Button("Change");
		b2.addActionListener(this);
		b3 = new Button("Delete");
		b3.addActionListener(this);
		panel1.add(b1);
		panel1.add(b2);
		panel1.add(b3);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		panel2.add(panel1, BorderLayout.NORTH);
		reactionpanel.add(panel2, BorderLayout.EAST);
		reactionpanel.add(l, BorderLayout.CENTER);
		l.setSize(new Dimension(800, 450));
		
		
		

		tta = new TextField();
		ttb = new TextField();
		ttc = new TextField();
		ttd = new TextField();
		JLabel text1 = new JLabel("Name");
		JLabel text2= new JLabel("Mass (amu)");
		JLabel text3 = new JLabel("Radius (pm)");
		JLabel text4 = new JLabel("Color");

		text1.setPreferredSize(new Dimension(120, 24));
		text2.setPreferredSize(new Dimension(120, 24));
		text3.setPreferredSize(new Dimension(120, 24));
		text4.setPreferredSize(new Dimension(120, 24));
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));

		selectcolor = new Button("Select Color");
		selectcolor.addActionListener(this);
		
		panel3.add(text1);
		panel3.add(tta);
		panel3.add(text2);
		panel3.add(ttb);
		panel3.add(text3);
		panel3.add(ttc);
		panel3.add(text4);
		panel3.add(ttd);
		panel3.add(selectcolor);
		panel3.add(new JLabel(" "));
		
		b4 = new Button("Add");
		b4.addActionListener(this);
		b5 = new Button("Change");
		b5.addActionListener(this);
		b6 = new Button("Delete");
		b6.addActionListener(this);
		panel3.add(b4);
		panel3.add(b5);
		panel3.add(b6);
		
		JPanel panel4 = new JPanel();
		panel4.setLayout(new BorderLayout());
		panel4.add(panel3, BorderLayout.NORTH);
		l2 = new List();
		l2.setSize(new Dimension(800, 450));
		compoundpanel.setLayout(new BorderLayout());
		compoundpanel.add(l2, BorderLayout.CENTER);
		compoundpanel.add(panel4, BorderLayout.EAST);
		l2.addItemListener(this);
		this.add(tabbedPane);
		this.setSize(720, 720);
		this.setVisible(false);
		
		compounds = new ArrayList<Compound>();
	}

	List l2;
	int selected = 0;
	
	boolean legacyfiles = false;
	
	java.util.List<Compound> complist = new ArrayList<Compound>();
	@Override
	public void actionPerformed(ActionEvent e) {
		//Buttons to add, remove or change a reaction or compound
		if (e.getSource() == b1) {
			//Add reaction
			l.add("Test - Rxn");
			reactions.add(new CompoundReaction());
			l.select(reactions.size() - 1);
			if (ca.getSelectedIndex() == 0)
				setReaction();
			else
				putReaction();
			updateRList();
		} else if (e.getSource() == b2) {
			//Change reaction
			putReaction();
			updateRList();
		} else if (e.getSource() == b3) {
			//Remove reaction
			reactions.remove(l.getSelectedIndex());
			l.remove(l.getSelectedIndex());
			//l.select(l.getSelectedIndex() - 1);
			updateRList();
			setReaction();
		} else if (e.getSource() == b4) {
			compounds.add(new Compound(6E-26, 1.4E-10, Color.WHITE, "Default"));
			l2.add("Default");
			l2.select(compounds.size() - 1);
			if (tta.getText().isEmpty())
				setCompound();
			else
				putCompound();
			updateCompList();
		} else if (e.getSource() == b5) {
			putCompound();
			updateCompList();
		} else if (e.getSource() == b6) {
			compounds.remove(l2.getSelectedIndex());
			l2.remove(l2.getSelectedIndex());
			l2.select(l2.getSelectedIndex() - 1);
			updateCompList();
			setCompound();
		} else if (e.getSource() == selectcolor) {
			Color usercolor = JColorChooser.showDialog(this, "Select color", Color.WHITE);
			ttd.setText(Integer.toHexString(usercolor.getRGB()).substring(2));
		} else if (e.getSource() == mia) {
			FileDialog d = new FileDialog(this, "Save scenario", FileDialog.SAVE);
			d.setVisible(true);
			String filename = d.getFile();
			if (filename != null) {
				try {
					FileOutputStream fileOut = new FileOutputStream(d.getDirectory() + d.getFile());
					
					if (legacyfiles) {

						ObjectOutputStream out = new ObjectOutputStream(fileOut);
						out.writeObject(compounds);
						out.writeObject(reactions);
						out.close();
					}
					else {
					
						PrintWriter out = new PrintWriter(fileOut);

						out.println(compounds.size());

						for (Compound c: compounds) {
							c.toFileData(out);
						}

						out.println(reactions.size());

						for (CompoundReaction r: reactions) {
							r.toFileData(out, compounds);
						}

						out.close();
					}
					fileOut.close();
				} catch (IOException i) {
					i.printStackTrace();
				}
			}
		} else if (e.getSource() == mib) {

			FileDialog d = new FileDialog(this, "Load scenario", FileDialog.LOAD);
			d.setVisible(true);
			String filename = d.getFile();
			if (filename != null) {
				try {
					FileInputStream fileIn = new FileInputStream(d.getDirectory() + d.getFile());
					
					if (legacyfiles) {

						ObjectInputStream in = new ObjectInputStream(fileIn);
						compounds = (java.util.List<Compound>)in.readObject();
						reactions = (java.util.List<CompoundReaction>)in.readObject();
						
						in.close();
					} else {

						Scanner in = new Scanner(fileIn);

						compounds.clear();
						reactions.clear();

						int size = Integer.valueOf(in.nextLine());
						for (int i = 0; i < size; i++) {
							compounds.add(Compound.fromFileData(in));
						}

						size = Integer.valueOf(in.nextLine());

						for (int i = 0; i < size; i++) {
							reactions.add(CompoundReaction.fromFileData(in, compounds));
						}

						in.close();
					}

					fileIn.close();
					this.updateCompList();
					this.updateRList();
				} catch (IOException i) {
					JOptionPane.showMessageDialog(null, "Error: File does not exist");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "Error: Invalid file");
				}
			}
		} else if (e.getSource() == mic) {
			JOptionPane.showMessageDialog(null, "Chemical Kinetics Simulator\nBrandon Li, 2019\n20li3@lexingtonma.org");
		}
	}
	
	//Set the awt list to reflect changes in the internal compound list
	public void updateCompList() {
		complist.clear();
		l2.removeAll();
		for (Choice c: this.ctypes)
			c.removeAll();
		for (Choice c: this.ctypes) {
			c.add("");
		}
		for (Compound cm: compounds) {
			complist.add(cm);
			l2.add(cm.getname());
			for (Choice c: this.ctypes) {
				c.add(cm.getname());
			}
		}
	}
	
	//Same, except for reactions
	public void updateRList() {
		l.removeAll();
		for (CompoundReaction r: reactions) {
			l.add(r.toString());
		}
	}
	
	//Move information from fields to list
	private void putCompound() {
		try {
			Compound c = compounds.get(l2.getSelectedIndex());
			c.setradius(tta.getText());
			c.setmass(Double.valueOf(ttb.getText())/massConversionFactor);
			c.setradius(Double.valueOf(ttc.getText())/sizeConversionFactor);
			c.setcolor(new Color((int)(long)Long.valueOf(ttd.getText(), 16)));
		} catch(NumberFormatException e) {
			
		}
		//compounds.set(l2.getSelectedIndex());
		//l2.replaceItem(name, l2.getSelectedIndex());
	}
	
	private double sizeConversionFactor = 1E12;
	private double massConversionFactor = 6.022E23;
	private double energyConversionFactor = PhysicsSimulation.AVOGADRO / 1000;
	
	//Move info from list to textfields
	private void setCompound() {
		Compound c = compounds.get(l2.getSelectedIndex());
		tta.setText(c.getname());
		ttb.setText("" + c.getmass()*massConversionFactor);
		ttc.setText("" + c.getradius()*sizeConversionFactor);
		ttd.setText(Integer.toHexString(c.getcolor().getRGB()).substring(2));
	}
	
	public int getId(Compound c) {
		for (int i = 0; i < complist.size(); i++) {
			if (complist.get(i) == c)
				return i;
		}
		return -1;
	}
	
	//Move info from fields to list
	private void putReaction() {
		CompoundReaction r = reactions.get(l.getSelectedIndex());
		r.getreactants().clear();
		r.getproducts().clear();
		int ind = cr.getSelectedIndex();
		
		
		
		r.getreactants().add(complist.get(ca.getSelectedIndex() - 1));
		r.getproducts().add(complist.get(cc.getSelectedIndex() - 1));
		if (ind == 1 || ind == 3) {
			r.getreactants().add(complist.get(cb.getSelectedIndex() - 1));
		}
		if (ind == 2 || ind == 3) {
			r.getproducts().add(complist.get(cd.getSelectedIndex() - 1));
		}
		try {
			r.setActivationEnergy(Double.valueOf(tte.getText())/energyConversionFactor);
			r.setDeltaH(Double.valueOf(ttf.getText())/energyConversionFactor);
			r.setRateFwd(Double.valueOf(ttg.getText()));
			r.setRateBack(Double.valueOf(tth.getText()));
			r.setReversible(chk.getState());
		}
		catch(NumberFormatException e) {
				
		}
	}
	
	//Move from list to textfields
	private void setReaction() {
		CompoundReaction r = reactions.get(l.getSelectedIndex());
		ca.select(0);
		cb.select(0);
		cc.select(0);
		cd.select(0);
		if (r.getreactants().size() > 0)
			ca.select(getId(r.getreactants().get(0)) + 1);
		if (r.getproducts().size() > 0)
			cc.select(getId(r.getproducts().get(0)) + 1);

		int ind = 0;

		if (r.getreactants().size() == 1) {
			if (r.getproducts().size() == 1) {
				ind = 0;
			} else {
				ind = 2;
			}
		} else if (r.getreactants().size() == 2) {
			if (r.getproducts().size() == 1) {
				ind = 1;
			} else {
				ind = 3;
			}
		} else {
			ind = cr.getSelectedIndex();
		}
		
		cr.select(ind);
		
		
		//Gray out textboxes if the reaction has less reactants/products
		if (ind == 1 || ind == 3) {
			cb.setEnabled(true);
			text6.setEnabled(true);
			if (r.getreactants().size() > 1)
				cb.select(getId(r.getreactants().get(1)) + 1);
		} else {
			cb.setEnabled(false);
			text6.setEnabled(false);
		}
		if (ind == 2 || ind == 3) {
			cd.setEnabled(true);
			text8.setEnabled(true);
			if (r.getproducts().size() > 1)
				cd.select(getId(r.getproducts().get(1)) + 1);
		} else {
			cd.setEnabled(false);
			text8.setEnabled(false);
		}
		
		tte.setText("" + r.getActivationEnergy()*energyConversionFactor);
		ttf.setText("" + r.getDeltaH()*energyConversionFactor);
		ttg.setText("" + r.getRateFwd());
		tth.setText("" + r.getRateBack());
		chk.setState(r.isReversible());
	}
	
	java.util.List<Choice> ctypes = new ArrayList<Choice>();
	//java.util.List<JSpinner> numbers;
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		 if (e.getSource() == l) {
				setReaction();
				//ca.select(po);
			} else if (e.getSource() == l2) {
				System.out.println("Test");
				setCompound();
			} else  if (e.getSource() == cr) {
				

				int ind = cr.getSelectedIndex();


				//Gray out textboxes if the reaction has less reactants/products
				if (ind == 1 || ind == 3) {
					cb.setEnabled(true);
					text6.setEnabled(true);
				} else {
					cb.setEnabled(false);
					text6.setEnabled(false);
					cb.select(0);
				}
				if (ind == 2 || ind == 3) {
					cd.setEnabled(true);
					text8.setEnabled(true);
				} else {
					cd.setEnabled(false);
					text8.setEnabled(false);
					cd.select(0);
				}
				
				
			}
	}
}

//class 