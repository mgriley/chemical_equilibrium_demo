import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;


public class SmartGraph {
	
	// The points belonging to this graph
	private List<GraphPoint> points;
	
	// timer is for the x-axis timer of a point, intervalTimer is for graphing points every ___ seconds
	private Timer timer, intervalTimer;
	private static float INTERVAL = 1.0f;
	
	private Rectangle frame; // the frame of the graph
	private Rectangle graph; // the graph area within the frame
	
	// The greatest x and y values contained on the graph (storage required for graph scaling)
	private int maxY, maxX;
	
	public SmartGraph(int topLeftX, int topLeftY, int width, int height) {
		
		// Set the dimensions of the outer frame 
		frame = new Rectangle(topLeftX, topLeftY, width, height);
		
		// Set the dimensions of the graph, relative to the frame
		int horMargin = 50;
		int verMargin = 40;
		int graphWidth = width - horMargin * 2;
		int graphHeight = height - verMargin * 2;
		graph = new Rectangle(horMargin + topLeftX, verMargin + topLeftY, graphWidth, graphHeight);
		
		points = new ArrayList<>();
		
		// Set initial maximum values
		maxY = 50;
		maxX = 10;
	}
	
	public void update(int numA, int numB, int numC, int numD) {
		
		if(intervalTimer == null || intervalTimer.secsElapsed() > INTERVAL) {
			
			if(intervalTimer == null) {
				
				// If it's the first update, start the timers
				intervalTimer = new Timer();
				timer = new Timer();
			}
			
			// Get the time elapsed (in seconds) for the x-axis position
			float x = timer.secsElapsed();
			
			// Add four new points to the graph
			addPt(x, numA, Species.A);
			addPt(x, numB, Species.B);
			addPt(x, numC, Species.C);
			addPt(x, numD, Species.D);	
			
			intervalTimer.restart();
		}
	}
	
	private void addPt(float x, int num, Species s) {
		
		// Update the maximum graph values
		maxX = (int) Math.max(maxX, x);
		maxY = (int) Math.max(maxY, num);
		
		// Add the points
		points.add(new GraphPoint(x, num, s));
	}
	
	public void draw(Graphics2D g2d) {
		
		// Draw the background and border of the graph
		g2d.setColor(Color.WHITE);
		g2d.fill(frame);
		g2d.setColor(Color.BLACK);
		g2d.draw(frame);
		
		// Draw the graph title
		g2d.drawString("Particle Quantities", (int) graph.getCenterX() - 50, (int) graph.getMinY() - 20); 
		
		// Draw the coordinate axis
		int offset = 10;
		g2d.drawLine((int) graph.getMinX(), (int) graph.getMaxY() + offset, (int) graph.getMinX(), (int) graph.getMinY() - offset); // y-axis
		g2d.drawLine((int) graph.getMinX() - offset, (int) graph.getMaxY(), (int) graph.getMaxX() + offset, (int) graph.getMaxY()); // x-axis
		
		// Draw the coordinate axis labels
		Font original = g2d.getFont();
		g2d.setFont(original.deriveFont(10.0f));
		g2d.drawString("time (s)", (int) graph.getCenterX(), (int) graph.getMaxY() + 20);
		g2d.drawString("# each", (int) graph.getMinX() - 40, (int) graph.getCenterY());
		g2d.setFont(original);
		
		// Draw the origin label
		g2d.drawString("0", (int) graph.getMinX() - 15, (int) graph.getMaxY() + 15);
		
		// Label the maximum axis values
		g2d.drawString("" + maxX, (int) graph.getMaxX() - 5, (int) graph.getMaxY() + 20); // x-axis
		g2d.drawString("" + maxY, (int) graph.getMinX() - 25, (int) graph.getMinY() + 5); // y-axis
		
		// Draw a tick mark at the maximum axis values
		int tickLength = 3;
		g2d.drawLine((int) graph.getMaxX(), (int) graph.getMaxY(), (int) graph.getMaxX(), (int) graph.getMaxY() + tickLength); // x-axis
		g2d.drawLine((int) graph.getMinX() - tickLength, (int) graph.getMinY(), (int) graph.getMinX(), (int) graph.getMinY()); // y-axis
		
		// Graph each point
		for(int i = 0; i < points.size(); i++) {
			
			GraphPoint pt = points.get(i);
			
			// Get the scaled position of the point (where 0.0 is on an axis, and 1.0 is at the max value) 
			float scaledX = pt.getX() / (float) maxX;
			float scaledY = pt.getY() / (float) maxY;
			
			// Get the position of the point on the JPanel
			double posX = graph.getMinX() + graph.getWidth() * scaledX;
			double posY = graph.getMaxY() - graph.getHeight() * scaledY;
			
			// Draw the point on the JPanel
			int r = pt.species().ptRadius();
			g2d.setColor(pt.species().color());
			g2d.fillOval((int) posX - r, (int) posY - r, r * 2, r * 2);
			g2d.setColor(Color.BLACK);
			g2d.drawOval((int) posX - r, (int) posY - r, r * 2, r * 2);
		}
	}
}
