package kineticssim.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Panel;
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
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.Box.Filler;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import kineticssim.Compound;
import kineticssim.PhysicsSimulation;
import kineticssim.reactions.CompoundReaction;

/*
 * 	A -> C
	A + B -> C
	A -> C + D
	A + B -> C + D
 */

//wtf
//The window that allows you to add and modify reactions
public class ReactionManager extends JFrame implements ActionListener, ListSelectionListener, ItemListener {
	private JComboBox<String> reactiontype;
	JButton b;
	JList<String> l;
	JList<String> l2;
	DefaultListModel<String> lm;
	DefaultListModel<String> l2m;
	
	Panel reaction;
	JComboBox<String> cr;
	JComboBox<String> ca;
	JComboBox<String> cb;
	JComboBox<String> cc;
	JComboBox<String> cd;
	/*JSpinner ta;
	JSpinner tb;
	JSpinner tc;
	JSpinner td;
	*/
	
	JButton b1;
	JButton b2;
	JButton b3;
	JButton b4;
	JButton b5;
	JButton b6;
	
	JButton selectcolor;
	
	JTextField tta;
	JTextField ttb;
	JTextField ttc;
	JTextField ttd;

	JTextField tte;
	JTextField ttf;
	JTextField ttg;
	JTextField tth;
	
	JLabel text5;
	JLabel text6;
	JLabel text7;
	JLabel text8;
	
	JCheckBox chk;
	
	JMenuBar mb;
	JMenuItem mia;
	JMenuItem mib;
	JMenuItem mic;
	JMenuItem min;
	
	public java.util.List<CompoundReaction> reactions;
	public java.util.List<Compound> compounds;
	
	
	private Filler newFiller(int w, int h) {
		Filler f = new Box.Filler(new Dimension(w, h), new Dimension(w, h), new Dimension(2000, h));
		f.setAlignmentX(Component.LEFT_ALIGNMENT);
		return f;
	}
	
	public ReactionManager() {
		
		//We use awt/swing for the GUI here
		
		reactions = new java.util.ArrayList<CompoundReaction>();
		
		this.setLayout(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel compoundpanel = new JPanel();
		tabbedPane.addTab("Compounds", compoundpanel);
		JPanel reactionpanel = new JPanel();
		tabbedPane.addTab("Reactions", reactionpanel);
		lm = new DefaultListModel<String>();
		l = new JList<String>(lm);
		//l.setFont(Font);
		l.addListSelectionListener(this);
		mb = new JMenuBar();
		JMenu jm = new JMenu("File");
		mia = new JMenuItem("Save");
		mib = new JMenuItem("Load");
		mic = new JMenuItem("About");
		min = new JMenuItem("New");
		jm.add(mia);
		jm.add(min);
		jm.add(mic);
		jm.add(mib);
		mia.addActionListener(this);
		mib.addActionListener(this);
		mic.addActionListener(this);
		min.addActionListener(this);
		mb.add(jm);
		
		this.setJMenuBar(mb);
		
		
		
		
		cr = new JComboBox<String>();
		//cr.
		cr.addItem("A -> C");
		cr.addItem("A + B -> C");
		cr.addItem("A -> C + D");
		cr.addItem("A + B -> C + D");
		cr.setAlignmentX(LEFT_ALIGNMENT);

		cr.setSelectedIndex(3);
		cr.addItemListener(this);
		//u2192
		//cr.setFont(new Font("Serif", Font.PLAIN, 40));
		ca = new JComboBox<String>();
		ca.setAlignmentX(LEFT_ALIGNMENT);
		cb = new JComboBox<String>();
		cb.setAlignmentX(LEFT_ALIGNMENT);
		cc = new JComboBox<String>();
		cc.setAlignmentX(LEFT_ALIGNMENT);
		cd = new JComboBox<String>();
		cd.setAlignmentX(LEFT_ALIGNMENT);
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
		text5.setAlignmentX(LEFT_ALIGNMENT);
		text6= new JLabel("Reactant B");
		text6.setAlignmentX(LEFT_ALIGNMENT);
		text7 = new JLabel("Product C");
		text7.setAlignmentX(LEFT_ALIGNMENT);
		text8 = new JLabel("Product D");
		text8.setAlignmentX(LEFT_ALIGNMENT);
		JLabel text9 = new JLabel("Reaction Type");
		text9.setAlignmentX(LEFT_ALIGNMENT);
		JLabel text10 = new JLabel("Activation Energy (kJ/mol)");
		text10.setAlignmentX(LEFT_ALIGNMENT);
		JLabel text11 = new JLabel("Delta H (kJ/mol)");
		text11.setAlignmentX(LEFT_ALIGNMENT);
		JLabel text12 = new JLabel("K forward");
		text12.setAlignmentX(LEFT_ALIGNMENT);
		JLabel text13 = new JLabel("K backward");
		text13.setAlignmentX(LEFT_ALIGNMENT);
		chk = new JCheckBox("Reversible");
		chk.setAlignmentX(LEFT_ALIGNMENT);
		
		tte = new JTextField();
		tte.setAlignmentX(LEFT_ALIGNMENT);
		ttf = new JTextField();
		ttf.setAlignmentX(LEFT_ALIGNMENT);
		ttg = new JTextField();
		ttg.setAlignmentX(LEFT_ALIGNMENT);
		tth = new JTextField();
		tth.setAlignmentX(LEFT_ALIGNMENT);
		
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
		panel1.add(newFiller(200, 4));
		panel1.add(text5);
		panel1.add(ca);
		panel1.add(newFiller(10, 4));
		//panel1.add(ta);
		panel1.add(text6);
		panel1.add(cb);
		panel1.add(newFiller(10, 4));
		//panel1.add(tb);
		panel1.add(text7);
		panel1.add(cc);
		panel1.add(newFiller(10, 4));
		//panel1.add(tc);
		panel1.add(text8);
		panel1.add(cd);
		panel1.add(newFiller(10, 4));
		//panel1.add(td);
		panel1.add(text10);
		panel1.add(tte);
		panel1.add(newFiller(10, 4));
		panel1.add(text11);
		panel1.add(ttf);
		panel1.add(newFiller(10, 4));
		panel1.add(text12);
		panel1.add(ttg);
		panel1.add(newFiller(10, 4));
		panel1.add(text13);
		panel1.add(tth);
		panel1.add(newFiller(10, 4));
		panel1.add(chk);
		panel1.add(newFiller(10, 4));
		
		b1 = new JButton("Add");
		b1.addActionListener(this);
		b1.setAlignmentX(LEFT_ALIGNMENT);
		b2 = new JButton("Change");
		b2.addActionListener(this);
		b2.setAlignmentX(LEFT_ALIGNMENT);
		b3 = new JButton("Delete");
		b3.addActionListener(this);
		b3.setAlignmentX(LEFT_ALIGNMENT);
		panel1.add(b1);
		panel1.add(b2);
		panel1.add(b3);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		panel2.add(panel1, BorderLayout.NORTH);
		reactionpanel.add(panel2, BorderLayout.EAST);
		reactionpanel.add(l, BorderLayout.CENTER);
		l.setSize(new Dimension(800, 450));
		
		
		

		tta = new JTextField();
		Dimension d = new Dimension(120, 10);
		//tta.setMinimumSize(d);
		//tta.setPreferredSize(d);
		//tta.setMaximumSize(d);
		tta.setAlignmentX(LEFT_ALIGNMENT);
		ttb = new JTextField();
		//ttb.setMinimumSize(d);
	//	ttb.setPreferredSize(d);
		//ttb.setMaximumSize(d);
		ttb.setAlignmentX(LEFT_ALIGNMENT);
		ttc = new JTextField();
		//ttc.setMinimumSize(d);
		//ttc.setPreferredSize(d);
		//ttc.setMaximumSize(d);
		ttc.setAlignmentX(LEFT_ALIGNMENT);
		ttd = new JTextField();
		//ttd.setMinimumSize(d);
		//ttd.setPreferredSize(d);
		//ttd.setMaximumSize(d);
		ttd.setAlignmentX(LEFT_ALIGNMENT);
		JLabel text1 = new JLabel("Name");
		text1.setAlignmentY(Component.LEFT_ALIGNMENT);
		JLabel text2= new JLabel("Mass (amu)");
		text2.setAlignmentY(Component.LEFT_ALIGNMENT);
		JLabel text3 = new JLabel("Radius (pm)");
		text3.setAlignmentY(Component.LEFT_ALIGNMENT);
		JLabel text4 = new JLabel("Color");
		text4.setAlignmentY(Component.LEFT_ALIGNMENT);

		//text1.setPreferredSize(d);
		//text1.setMaximumSize(d);
		//text1.setMinimumSize(d);
		//text2.setPreferredSize(d);
		//text3.setPreferredSize(d);
		//text4.setPreferredSize(d);
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));

		selectcolor = new JButton("Select Color");
		selectcolor.addActionListener(this);
		selectcolor.setAlignmentX(LEFT_ALIGNMENT);
		//selectcolor.setPreferredSize(d);
		//selectcolor.setMaximumSize(d);
		//panel3.add();
		panel3.add(text1);
		panel3.add(tta);
		panel3.add(newFiller(140, 10));
		panel3.add(text2);
		panel3.add(ttb);
		panel3.add(newFiller(140, 10));
		panel3.add(text3);
		panel3.add(ttc);
		panel3.add(newFiller(140, 10));
		panel3.add(text4);
		panel3.add(ttd);
		panel3.add(newFiller(140, 4));
		panel3.add(selectcolor);
		panel3.add(newFiller(140, 25));
		
		b4 = new JButton("Add");
		b4.addActionListener(this);
		//b4.setPreferredSize(d);
		b4.setAlignmentY(Component.LEFT_ALIGNMENT);
		//b4.setMaximumSize(d);
		b5 = new JButton("Change");
		b5.addActionListener(this);
		//b5.setPreferredSize(d);
		b5.setAlignmentY(Component.LEFT_ALIGNMENT);
		//b5.setMaximumSize(d);
		b6 = new JButton("Delete");
		b6.addActionListener(this);
		//b6.setPreferredSize(d);
		b6.setAlignmentY(Component.LEFT_ALIGNMENT);
		//b6.setMaximumSize(d);
		panel3.add(b4);
		panel3.add(b5);
		panel3.add(b6);
		
		JPanel panel4 = new JPanel();
		panel4.setLayout(new BorderLayout());
		panel4.add(panel3, BorderLayout.NORTH);
		l2m = new DefaultListModel<String>();
		l2 = new JList<String>(l2m);
		l2.setPreferredSize(new Dimension(800, 450));
		compoundpanel.setLayout(new BorderLayout());
		compoundpanel.add(l2, BorderLayout.CENTER);
		compoundpanel.add(panel4, BorderLayout.EAST);
		l2.addListSelectionListener(this);
		this.add(tabbedPane);
		this.setSize(720, 720);
		this.setVisible(false);
		
		compounds = new ArrayList<Compound>();
	}

	int selected = 0;
	
	boolean legacyfiles = false;
	
	java.util.List<Compound> complist = new ArrayList<Compound>();
	@Override
	public void actionPerformed(ActionEvent e) {
		//JButtons to add, remove or change a reaction or compound
		if (e.getSource() == b1) {
			//Add reaction
			lm.addElement("Test - Rxn");
			reactions.add(new CompoundReaction());
			l.setSelectedIndex(reactions.size() - 1);
			if (ca.getSelectedIndex() == 0)
				setReaction();
			else
				putReaction();
			updateRList();
		} else if (e.getSource() == b2) {
			if (l.getSelectedIndex() == -1)
				return;
			//Change reaction
			putReaction();
			updateRList();
		} else if (e.getSource() == b3) {
			//Remove reaction
			if (l.getSelectedIndex() == -1)
				return;
			reactions.remove(l.getSelectedIndex());
			l.removeListSelectionListener(this);
			lm.remove(l.getSelectedIndex());
			l.addListSelectionListener(this);
			l.setSelectedIndex(-1);
			//l.select(l.getSelectedIndex() - 1);
			updateRList();
			setReaction();
		} else if (e.getSource() == b4) {
			compounds.add(new Compound(4/massConversionFactor, 1.4E-10, new Color(200, 235, 255), "Default (Helium)"));
			l2m.addElement("Default");
			l2.setSelectedIndex(compounds.size() - 1);
			if (tta.getText().isEmpty())
				setCompound();
			else
				putCompound();
			updateCompList();
		} else if (e.getSource() == b5) {
			if (l2.getSelectedIndex() == -1)
				return;
			putCompound();
			updateCompList();
		} else if (e.getSource() == b6) {
			if (l2.getSelectedIndex() == -1)
				return;
			compounds.remove(l2.getSelectedIndex());
			l2.removeListSelectionListener(this);
			l2m.remove(l2.getSelectedIndex());
			l2.addListSelectionListener(this);
			l2.setSelectedIndex(-1);
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
			JOptionPane.showMessageDialog(null, "Chemical Kinetics Simulator\nBrandon Li, 2019\nbrandonli.lex@gmail.com");
		} else if (e.getSource() == min) {
			compounds.clear();
			reactions.clear();
			this.updateCompList();
			this.updateRList();
		}
	}
	
	//Set the awt list to reflect changes in the internal compound list
	public void updateCompList() {
		complist.clear();
		int tmpselect = l2.getSelectedIndex();
		l2.removeListSelectionListener(this);
		l2m.removeAllElements();
		for (JComboBox<String> c: this.ctypes)
			c.removeAllItems();
		for (JComboBox<String> c: this.ctypes) {
			c.addItem("");
		}
		for (Compound cm: compounds) {
			complist.add(cm);
			l2m.addElement(cm.getname());
			for (JComboBox<String> c: this.ctypes) {
				c.addItem(cm.getname());
			}
		}
		l2.setSelectedIndex(tmpselect);
		l2.addListSelectionListener(this);
	}
	
	//Same, except for reactions
	public void updateRList() {
		int tmpselect = l.getSelectedIndex();
		l.removeListSelectionListener(this);
		lm.clear();
		for (CompoundReaction r: reactions) {
			lm.addElement(r.toString());
		}
		l.setSelectedIndex(tmpselect);
		l.addListSelectionListener(this);
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
			JOptionPane.showMessageDialog(null, "Error: Invalid number");
		}
		//compounds.set(l2.getSelectedIndex());
		//l2.replaceItem(name, l2.getSelectedIndex());
	}
	
	private double sizeConversionFactor = 1E12;
	private double massConversionFactor = 6.022E23;
	private double energyConversionFactor = PhysicsSimulation.AVOGADRO / 1000;
	
	//Move info from list to JTextFields
	private void setCompound() {
		if (l2.getSelectedIndex() == -1)
		{
			tta.setText("");
			ttb.setText("");
			ttc.setText("");
			ttd.setText("");
			return;
		}
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
		
		
		
		try {

			r.getreactants().add(complist.get(ca.getSelectedIndex() - 1));
			r.getproducts().add(complist.get(cc.getSelectedIndex() - 1));
			if (ind == 1 || ind == 3) {
				r.getreactants().add(complist.get(cb.getSelectedIndex() - 1));
			}
			if (ind == 2 || ind == 3) {
				r.getproducts().add(complist.get(cd.getSelectedIndex() - 1));
			}
			
			
			r.setActivationEnergy(Double.valueOf(tte.getText())/energyConversionFactor);
			r.setDeltaH(Double.valueOf(ttf.getText())/energyConversionFactor);
			r.setRateFwd(Double.valueOf(ttg.getText()));
			r.setRateBack(Double.valueOf(tth.getText()));
			r.setReversible(chk.getSelectedObjects() != null);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "Error: Invalid compound");
		}
		catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Error: Invalid number");
		}
	}
	
	//Move from list to JTextFields
	private void setReaction() {
		if (l.getSelectedIndex() == -1) {
			ca.setSelectedIndex(0);
			cb.setSelectedIndex(0);
			cc.setSelectedIndex(0);
			cd.setSelectedIndex(0);
			cr.setSelectedIndex(0);
			tte.setText("");
			ttf.setText("");
			ttg.setText("");
			tth.setText("");
			chk.setSelected(false);
			return;
		}
		CompoundReaction r = reactions.get(l.getSelectedIndex());
		ca.setSelectedIndex(0);
		cb.setSelectedIndex(0);
		cc.setSelectedIndex(0);
		cd.setSelectedIndex(0);
		if (r.getreactants().size() > 0)
			ca.setSelectedIndex(getId(r.getreactants().get(0)) + 1);
		if (r.getproducts().size() > 0)
			cc.setSelectedIndex(getId(r.getproducts().get(0)) + 1);

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
		
		cr.setSelectedIndex(ind);
		
		
		//Gray out textboxes if the reaction has less reactants/products
		if (ind == 1 || ind == 3) {
			cb.setEnabled(true);
			text6.setEnabled(true);
			if (r.getreactants().size() > 1)
				cb.setSelectedIndex(getId(r.getreactants().get(1)) + 1);
		} else {
			cb.setEnabled(false);
			text6.setEnabled(false);
		}
		if (ind == 2 || ind == 3) {
			cd.setEnabled(true);
			text8.setEnabled(true);
			if (r.getproducts().size() > 1)
				cd.setSelectedIndex(getId(r.getproducts().get(1)) + 1);
		} else {
			cd.setEnabled(false);
			text8.setEnabled(false);
		}
		
		tte.setText("" + r.getActivationEnergy()*energyConversionFactor);
		ttf.setText("" + r.getDeltaH()*energyConversionFactor);
		ttg.setText("" + r.getRateFwd());
		tth.setText("" + r.getRateBack());
		chk.setSelected(r.isReversible());
	}
	
	java.util.List<JComboBox<String>> ctypes = new ArrayList<JComboBox<String>>();
	//java.util.List<JSpinner> numbers;
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		 if (e.getSource() == cr) {
				

				int ind = cr.getSelectedIndex();


				//Gray out textboxes if the reaction has less reactants/products
				if (ind == 1 || ind == 3) {
					cb.setEnabled(true);
					text6.setEnabled(true);
				} else {
					cb.setEnabled(false);
					text6.setEnabled(false);
					cb.setSelectedIndex(0);
				}
				if (ind == 2 || ind == 3) {
					cd.setEnabled(true);
					text8.setEnabled(true);
				} else {
					cd.setEnabled(false);
					text8.setEnabled(false);
					cd.setSelectedIndex(0);
				}
				
				
			}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		//if (e.getValueIsAdjusting())
		//	return;
		 if (e.getSource() == l) {
				setReaction();
				//ca.select(po);
			} else if (e.getSource() == l2) {
				System.out.println("Test");
				setCompound();
			} 
	}
}

//class 