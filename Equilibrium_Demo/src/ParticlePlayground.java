import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;


public class ParticlePlayground {
	
	private int width, height;
	private Pt topLeft;
	
	List<Particle> particles;
	
	public ParticlePlayground(Pt argTopLeft, int argWidth, int argHeight, int numA, int numB, int numC, int numD) {
		
		// The position of the top-left corner of the particle box
		topLeft = new Pt(argTopLeft);
		
		// The dimensions of the particle box
		width = argWidth;
		height = argHeight;
		
		// Create all of the different particles
		particles = new ArrayList<>();
		createParticles(numA, Species.A);
		createParticles(numB, Species.B);
		createParticles(numC, Species.C);
		createParticles(numD, Species.D);
	}
	
	private void createParticles(int num, Species s) {
		
		// Create a certain number of particles of a certain type
		for(int i = 0; i < num; i++) {
			
			Particle p = new Particle(s, width, height, topLeft);
			particles.add(p);
		}
	}
	
	public void update() {
		
		// update all of the particles
		Particle.updateGroup(particles);
	}
	
	public void draw(Graphics2D g2d) {
		
		// Draw a white background with a black border
		g2d.setColor(Color.WHITE);
		g2d.fillRect(topLeft.intX(), topLeft.intY(), width, height);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(topLeft.intX(), topLeft.intY(), width, height);
		
		// Draw each particle in the box
		for(Particle p : particles) {
			p.draw(g2d);
		}
	}
	
	/*
	 * Accessors
	 */
	
	public int[] getNumOfEach() {
		
		int[] typeCounts = {0, 0, 0, 0};
		for(Particle p : particles) {
			
			typeCounts[p.species.ordinal()]++;
		}
		return typeCounts;
	}
	
	public int width() { 
		return width;
	}
	
	public int height() {
		return height;
	}
}
