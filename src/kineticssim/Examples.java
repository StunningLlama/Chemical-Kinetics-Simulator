package kineticssim;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import kineticssim.gui.ReactionManager;

public class Examples implements ActionListener {
	private int SIZE = 3;
	
	
	private String[] data = {
			"2\r\n" + 
			"7.638658253072068E-23\r\n" + 
			"1.4E-10\r\n" + 
			"ffffff\r\n" + 
			"NO2\r\n" + 
			"1.5277316506144137E-22\r\n" + 
			"2.0E-10\r\n" + 
			"ee6428\r\n" + 
			"N2O4\r\n" + 
			"1\r\n" + 
			"-9.963467286615743E-20\r\n" + 
			"9.963467286615743E-20\r\n" + 
			"1.0\r\n" + 
			"3.2E10\r\n" + 
			"true\r\n" + 
			"0\r\n" + 
			"0\r\n" + 
			"1\r\n" + 
			"N\r\n"
			
			,
			
			"2\r\n" + 
			"1.0627698439056791E-22\r\n" + 
			"2.0E-10\r\n" + 
			"ff9900\r\n" + 
			"Explosive\r\n" + 
			"5.313849219528396E-23\r\n" + 
			"1.4E-10\r\n" + 
			"999999\r\n" + 
			"Products\r\n" + 
			"1\r\n" + 
			"-3.985386914646297E-19\r\n" + 
			"1.6605778811026237E-19\r\n" + 
			"1.0E11\r\n" + 
			"1.0E11\r\n" + 
			"true\r\n" + 
			"0\r\n" + 
			"N\r\n" + 
			"1\r\n" + 
			"1\r\n"
			
			,
			
			""
	};
	
	private String[] names = {
			"NO2/N2O4 equilibrium",
			"Explosion",
			"Decomposition of ozone"
	};
	
	private JMenuItem[] items = new JMenuItem[SIZE];
	
	ReactionManager man;
	
	public Examples(ReactionManager m) {
		man = m;
	}
	
	public void addToMenu(JMenu jm) {
		JMenu ex = new JMenu("Examples");
		for (int i = 0; i < SIZE; i++) {
			JMenuItem it = new JMenuItem(names[i]);
			items[i] = it;
			it.addActionListener(this);
			ex.add(it);
		}
		jm.add(ex);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("wednesday");
		for (int i = 0; i < SIZE; i++) {
			if (items[i] == e.getSource()) {
				Scanner s = new Scanner(data[i]);
				man.loadFile(s);
				man.updateCompList();
				man.updateRList();
				return;
			}
		}
	}
}
