import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.AttributedString;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class MenuPanel extends DemoPanel {
	
	private JSlider[] sliders;
	
	public MenuPanel(final DemoFrame frame) {	
		
		super(frame);
		
		// Sliders
		
		// Set the relevant values for the slider
		int minC = 0;
		int maxC = 50;
		int initC = (maxC + minC) / 2;
		
		// Create a slider for each particle type (0 - A, 1 - B, 2 - C, 3 - D)
		sliders = new JSlider[4];
		for(int i = 0; i < sliders.length; i++) {
			
			JSlider slider = new JSlider(JSlider.HORIZONTAL, minC, maxC, initC);
			
			sliders[i] = slider;
			
			// Add a listener to the slider
			slider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
				}
			});
			
			// Size and position the sliders
			Dimension size = slider.getPreferredSize();
			int xInterval = this.getWidth() / 4;
			int x = xInterval / 2 + i * xInterval - size.width / 2;
			int y = titleImg.getHeight() - size.height / 2 + 60;
			slider.setBounds(x, y, size.width, size.height);
			
			this.add(slider);
		}
		
		// Button:
		
		// Create a button that changes the frame
		JButton btn = new JButton("Start");
		
		btn.addActionListener(new AbstractAction() {
			
			public void actionPerformed(ActionEvent e) {
				
				// Get the selected number of each type of particle, and change the panel 
				int numA = (int) sliders[0].getValue();
				int numB = (int) sliders[1].getValue();
				int numC = (int) sliders[2].getValue();
				int numD = (int) sliders[3].getValue();
				frame.changePanel(new RxPanel(frame, numA, numB, numC, numD));
			}
		});
		super.setupBtn(btn);
		
		// Set the values of the current label
		String[] currentValues = new String[4];
    	for(int i = 0; i < currentValues.length; i++) {
    		currentValues[i] = "--";
    	}
    	updateCurrentLabels(currentValues);
	}
	
	@Override
    public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 
    	Graphics2D g2d = (Graphics2D) g;
    	
    	optimizeGraphics(g2d);
    	drawTitleImg(g2d);
    	
    	// Update the value of the labels
    	String[] initValues = new String[4];
    	for(int i = 0; i < initValues.length; i++) {
    		initValues[i] = "" + (int) sliders[i].getValue();
    	}
    	updateInitLabels(initValues);
  
    	// Draw the Crescent logo and title
		try {
			int textSpacing = 7;
			int borderSpacing = 30;
			URL imgURL = DemoFrame.class.getResource("Crescent Logo.png");
	    	BufferedImage crescentLogo;
			crescentLogo = ImageIO.read(imgURL);
	    	int x = borderSpacing + crescentLogo.getWidth() / 4;
	    	int y = this.getHeight() / 2 - crescentLogo.getHeight() / 4;
	    	g2d.drawImage(crescentLogo, x, y, crescentLogo.getWidth(), crescentLogo.getHeight(), null);
			Font names = new Font("sans serif", Font.PLAIN, 15);
			g2d.setFont(names);
			AttributedString nameText = new AttributedString("By: Matthew Riley & Hugh McCauley");
			nameText.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
			int nameX = textSpacing;
			int nameY = this.getHeight()- crescentLogo.getHeight() / 8 - textSpacing;
			g2d.drawString(nameText.getIterator(), nameX, nameY);
			AttributedString yearText = new AttributedString("Class of 2015");
			nameText.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
			int yearX = textSpacing;
			int yearY = this.getHeight()- crescentLogo.getHeight() / 8 + textSpacing;
			g2d.drawString(yearText.getIterator(), yearX, yearY);
			Font titles = new Font("sans serif", Font.PLAIN, 100);
			g2d.setFont(titles);
			AttributedString title = new AttributedString("Equilibrium");
			title.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
			//title.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 0, 11);
			title.addAttribute(TextAttribute.FONT, titles);
			int titleX = crescentLogo.getWidth() * 3 / 2 + borderSpacing + 10;
			int titleY = this.getHeight() / 2 + 30;
			g2d.drawString(title.getIterator(), titleX, titleY);
			AttributedString titleBot = new AttributedString("Demo");
			titleBot.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
			//titleBot.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 0, 4);
			titleBot.addAttribute(TextAttribute.FONT, titles);
			g2d.drawString(titleBot.getIterator(), titleX, titleY + 90);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
