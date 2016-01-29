import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * TODO for Future Generations
 * 
 * Greetings! If you've decided to improve this software, thank you! Here is a list of things 
 * Hugh and I wanted to do, but we didn't have time for:
 * 
 * 1.
 * Tweak the slider range, speed, and particle size for the cleanest possible readings
 *  
 *  2.
 *  Add a pause btn for RxPanel
 *  
 *  3.
 *  Draw little arrows on the end of the graph axis
 * 
 * 4. Le Chatelier's Principle:
 * Tap A, B, C, or D to add 1 particle of that type to the container
 * - as a demonstration of Le Chatelier's principle
 * - Add a column for stresses added (for added particles)
 * 
 * 5. Technicalities:
 * Specify quantities of [Initial] and [Current] -> they are particles / L 
 * - specify that the container is 1L in volume
 * -- the states should be (g), not (aq) b/c they are in a fixed-volume container
 * -- allow the user to resize the container (using a slider) to simulate 
 * increased pressure in Le Chatelier's principle
 * 
 * 6. Make stop button for the demo 
 * 
 * 7. After the user stops the demo, make a larger printout of the graph, annotated with
 * times when particles added, container volume changed, etc.
 * - save as a .png file
 * 
 * 8. Make the demo fullscreen (will allow for more room for the container and the graph)
 * 
 * 9. Allow more options on the menu screen
 * a) Molar coefficients
 * b) mass/particle of each Species (where radius is directly proportional to mass)
 * c) starting volume of the container
 * d) colour of each species
 */

public class DemoFrame extends JFrame {
	
	public DemoFrame(int width, int height) {
		
		// Set up the frame
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(width, height);
		setResizable(false);
		setTitle("Equilibrium Demo");
		setLayout(null);
		
		// Set the first panel to the menu panel
		this.setContentPane(new MenuPanel(this));
	}
	
	// Switch to the specified panel
	public void changePanel(JPanel nextPanel) {
		
		// Swap panels
		this.setContentPane(nextPanel);
		this.revalidate();
	}
	
	public JPanel getPanel() {
		return (JPanel) getContentPane();
	}	
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
    	
    	// Frame dimensions
    	int width = 1000;
    	int height = 700;
    	
    	// Generate the title image
    	DemoPanel.generateTitleImg(width, 120);
    	
    	// Setup the frame
    	DemoFrame myFrame = new DemoFrame(width, height);
    	myFrame.setVisible(true);
    	
        // Repaint the panel at a rate of 60fps
        while(true) {
        	
        	myFrame.getPanel().repaint();
        	
        	Thread.sleep(17);
        }
    }
}
