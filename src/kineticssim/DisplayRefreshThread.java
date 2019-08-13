package kineticssim;

import kineticssim.gui.SimulationGUI;

/*****
 * Chemical Kinetics Simulator
 * @author Brandon
 * E block
 *****/

public class DisplayRefreshThread implements Runnable {
	
	private SimulationGUI gui;
	
	
	public DisplayRefreshThread(SimulationGUI instance) {
		gui = instance;
	}
	
	//This is the main thread that updates the physics and renders the GUI.
	@Override
	public void run() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		gui.getSimulator().getPhysicsSimulation().updateGUI();
		int n = 0;
		while (true) {
			//Aim for an optimal 60 fps - 17 ms between each frame.
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//TODO
			int it = gui.getSimulator().getPhysicsSimulation().iterations;
			for (int i = 0; i < it; i++)
				gui.getSimulator().getPhysicsSimulation().iterate();
			
			if (n%2 == 1) {
				gui.getSimulator().getPhysicsSimulation().updateGUI();
				gui.getControlPanel().repaintKey();
			}
			
			gui.repaint();
			
			n++;
		}
	}
	
}
