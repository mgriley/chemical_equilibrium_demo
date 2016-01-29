/*
 Matthew Riley
 ICS3U-1
 Final Project - GamePanel
 May 17, 2014
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

/*
 TODO: 

 Ask about the "RasterFormatException" - log saved in folder
 Make it have a miniature sirens that blink/spin (and that make indications when a turret upgrade occurs)
 Change the mac dock icon 
 */
/**
 *
 * @author matthewriley
 */
public class RxPanel extends DemoPanel {

	ParticlePlayground myPlayground;
	SmartGraph myGraph;

    RxPanel(DemoFrame frame, int numA, int numB, int numC, int numD) {
    	
    	super(frame);
    	
    	// Create the particle playground and the graph
    	Pt pos = new Pt(30, 200);
    	myPlayground = new ParticlePlayground(pos, 400, 400, numA, numB, numC, numD);
    	myGraph = new SmartGraph(pos.intX() + myPlayground.width() + 30, pos.intY(), 500, 400);
    	
    	// Add the first graph point
    	myGraph.update(numA, numB, numC, numD);
    	
    	// Create a button that changes the frame, and add it to the panel
		JButton btn = new JButton("Menu");
		btn.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				frame.changePanel(new MenuPanel(frame));
			}
		});
		super.setupBtn(btn);
		
		// Set the init values of the labels
		String[] initValues = {numA + "", numB + "", numC + "", numD + ""};
		super.updateInitLabels(initValues);
    }

    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	Graphics2D g2d = (Graphics2D) g;
    	optimizeGraphics(g2d);
    	drawTitleImg(g2d);
    	
    	// Update the particle playground every cycle
    	myPlayground.update();
    	
    	// Get the concentration of each particle type
		int[] numTypes = myPlayground.getNumOfEach();
		int numA = numTypes[0];
		int numB = numTypes[1];
		int numC = numTypes[2];
		int numD = numTypes[3];
		
		// Update the values of the current concentrations labels
		String[] values = {numA + "", numB + "", numC + "", numD + ""};
    	super.updateCurrentLabels(values);
    		
		// Add the new point to the graph
		myGraph.update(numA, numB, numC, numD);
    	
    	// Draw the rx. displays
    	myPlayground.draw(g2d);
    	myGraph.draw(g2d);
    }
}
