import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.AttributedString;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public abstract class DemoPanel extends JPanel {
	
	public static BufferedImage titleImg;
	public JLabel[] initLabels, currentLabels;
	
	public static void generateTitleImg(int width, int height) throws IOException {
    	
		titleImg = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		
    	Graphics2D g2d = (Graphics2D) titleImg.createGraphics();
    	optimizeGraphics(g2d);
    	
    	String[] types = {"A", "B", "C", "D"};
    	
    	Color[] colors = new Color[4];
    	colors[0] = new Color(255, 150, 150); // light red
    	colors[1] = new Color(140, 207, 255); // light blue
    	colors[2] = new Color(150, 255, 150); // light green
    	colors[3] = new Color(255, 220, 150); // light orange		
    			
    	// Draw the text and the plus signs
		int xInterval = titleImg.getWidth() / 4;
    	for(int i = 0; i < 4; i++) {
    		
    		// Draw a circle around the text
    		int r = 50;
    		int cX = xInterval / 2 + i * xInterval;
    		int cY = titleImg.getHeight() / 2;
    		g2d.setColor(colors[i]);
    		g2d.fillOval(cX - r, cY - r, r * 2, r * 2);
    		g2d.setColor(Color.BLACK);
    		
    		Stroke original = g2d.getStroke();
    		g2d.setStroke(new BasicStroke(2.0f));
    		g2d.drawOval(cX - r, cY - r, r * 2, r * 2);
    		g2d.setStroke(original);
    		
    		// Generate 1/4th of the equilibrium expression
    		
    		int textWidth = 75;
    		int textHeight = 20;
    		int textX = xInterval / 2 + i * xInterval - textWidth / 2;
    		int textY = titleImg.getHeight() / 2 + textHeight / 2;
    		
    		Font bigFont = new Font("sans serif", Font.PLAIN, 40);
    		Font smallFont = new Font("sans serif", Font.PLAIN, 15);
    		AttributedString text = new AttributedString("1" + types[i] + "(aq)");
    		text.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
    		text.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 2, 6);
    		text.addAttribute(TextAttribute.FONT, bigFont, 0, 2);
    		text.addAttribute(TextAttribute.FONT, smallFont, 2, 6);
    		
    		g2d.drawString(text.getIterator(), textX , textY);
    		
    		// Draw a plus sign, if applicable
    		if(i % 2 == 0) {
    			
    			int centreX = xInterval / 2 + i * xInterval + xInterval / 2;
    			int centreY = titleImg.getHeight() / 2;
    			
    			URL imgURL = DemoFrame.class.getResource("plus.png");
    			BufferedImage plusImg = ImageIO.read(imgURL);
    			
    			int x = centreX - plusImg.getWidth() / 2;
    			int y = centreY - plusImg.getHeight() / 2;
    			g2d.drawImage(plusImg, x, y, plusImg.getWidth(), plusImg.getHeight(), null);	
    		}
    	}
    	
    	// Draw the equilibrium arrow in the centre of the img.
    	URL imgURL = DemoFrame.class.getResource("arrow.png");
    	BufferedImage arrowImg = ImageIO.read(imgURL);
    	
    	int x = titleImg.getWidth() / 2 - arrowImg.getWidth() / 2;
    	int y = titleImg.getHeight() / 2 - arrowImg.getHeight() / 2;
    	g2d.drawImage(arrowImg, x, y, arrowImg.getWidth(), arrowImg.getHeight(), null);
    }
	
	public DemoPanel(DemoFrame frame) {
		
		// Establish absolute positioning
		
		this.setLayout(null);
		this.setSize(frame.getSize());
		this.setLocation(0, 0);
		
		// Setup the title block
		
		// Setup the init conc. labels
		
		Font labelFont = new Font("sans serif", Font.PLAIN, 20);
		
		initLabels = new JLabel[4];
		for(int i = 0; i < initLabels.length; i++) {
			
			JLabel label = new JLabel(10 + "", SwingConstants.CENTER);
			label.setFont(labelFont);
			initLabels[i] = label;
			
			Dimension size = label.getPreferredSize();
			int xInterval = this.getWidth() / 4;
			int x = xInterval / 2 + i * xInterval - size.width / 2;
			int y = titleImg.getHeight() + 10 - size.height / 2;
			label.setBounds(x, y, size.width, size.height);
			
			this.add(label);
			
			// Create the row label
			if(i == 0) {
				
				JLabel initRowLabel = new JLabel("[Initial]", SwingConstants.CENTER);
				initRowLabel.setFont(labelFont);
				size = initRowLabel.getPreferredSize();
				x = 10;
				initRowLabel.setBounds(x, y, size.width, size.height);	
				
				this.add(initRowLabel);
			}
		}
		
		// Setup the current conc. labels
		currentLabels = new JLabel[4];
		for(int i = 0; i < currentLabels.length; i++) {
			
			JLabel label = new JLabel(10 + "", SwingConstants.CENTER);
			label.setFont(labelFont);
			currentLabels[i] = label;
			
			Dimension size = label.getPreferredSize();
			int xInterval = this.getWidth() / 4;
			int x = xInterval / 2 + i * xInterval - size.width / 2;
			int y = titleImg.getHeight() + 35 - size.height / 2;
			label.setBounds(x, y, size.width, size.height);
			
			this.add(label);
			
			// Create the row label
			if(i == 0) {
				
				JLabel currentRowLabel = new JLabel("[Current]", SwingConstants.CENTER);
				currentRowLabel.setFont(labelFont);
				size = currentRowLabel.getPreferredSize();
				x = 10;
				currentRowLabel.setBounds(x, y, size.width, size.height);	
				
				this.add(currentRowLabel);
			}
		}
	}

	public void setupBtn(JButton btn) {
	
		// Position and add the given button
	
		Dimension size = btn.getPreferredSize();
		int x = this.getWidth() / 2 - size.width / 2;
		int y = this.getHeight() - 75;
		btn.setBounds(x, y, size.width, size.height);
		
		this.add(btn);	
	}
	
	// Update the initial concentration labels with the given values
	public void updateInitLabels(String[] newValues) {
		
		for(int i = 0; i < initLabels.length; i++) {
			
			// Update the init label
			initLabels[i].setText(newValues[i]);
		}
	}
	
	// Update the current concentration labels with the given values
	public void updateCurrentLabels(String[] newValues) {
		
		for(int i = 0; i < initLabels.length; i++) {
			
			// Update the current label
			currentLabels[i].setText(newValues[i]);
		}	
	}
	
	public void drawTitleImg(Graphics2D g2d) {
		
		g2d.drawImage(titleImg, 0, 0, titleImg.getWidth(), titleImg.getHeight(), null);
	}
	
    public static void optimizeGraphics(Graphics2D g2d) {
		
		// Optimize the graphics for higher quality drawing
		g2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(
				RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
	    	
    }
}
