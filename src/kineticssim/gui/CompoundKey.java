package kineticssim.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import kineticssim.Compound;

public class CompoundKey extends JPanel {
	ControlPanel pan;
	Font f = new Font("Monospaced", Font.BOLD, 16);
	public BufferedImage img;
	private String info;
	public CompoundKey(ControlPanel p) {
		pan = p;
		this.setMinimumSize(new Dimension(300, 250));
		this.setPreferredSize(new Dimension(300, 250));
		this.setMaximumSize(new Dimension(300, 250));
	}
	
	int verticalspacing = 18;
	
	@Override
	public void paint(Graphics gr) {
		if (img == null)
			img = (BufferedImage) this.createImage(300, 300);
		int i = verticalspacing;
		Graphics g = img.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 300, 300);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(f);
		
		g.setColor(Color.WHITE);
		g.drawString("Information: ", 10, i);
		i += verticalspacing*2;
		
		for (Compound c: pan.getSim().getPhysicsSimulation().compounds) {
			g.setColor(c.getcolor());
			g.drawString(c.getname() + ": " + pan.getSim().getPhysicsSimulation().particlecount.get(c).size(), 10, i);
			i += verticalspacing;
		}
		i += verticalspacing;
		g.setColor(Color.WHITE);
		for (String s: info.split("\n")) {
			g.drawString(s, 10, i);
			i += verticalspacing;
		}
		gr.drawImage(img, 0, 0, this);
	}
	
	public void setText(String text) {
		info = text;
	}
}
