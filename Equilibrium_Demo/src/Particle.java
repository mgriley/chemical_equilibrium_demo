import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Random;


public class Particle {
	
	private static final int RADIUS = 2; // the radius of each particle
	private static final int SPEED = 30; // the speed (pixels/s) of each particle
	
	int boxWidth, boxHeight; // the dimensions of the box containing this particle
	Pt offset; // the offset for each particle
	Pt pos; // position of the particle
	Pt velocity; // velocity of the particle
	Species species; // the type/species of the particle 
	
	Timer speedTimer;
	
	public Particle(Species argSpecies, int argBoxWidth, int argBoxHeight, Pt argOffset) {
		
		species = argSpecies;
		boxWidth = argBoxWidth;
		boxHeight = argBoxHeight;
		offset = new Pt(argOffset);
		
		// Choose a random position in the box
		int x = (int) (Math.random() * (boxWidth - RADIUS * 2)) + RADIUS;
		int y = (int) (Math.random() * (boxHeight - RADIUS * 2)) + RADIUS;
		pos = new Pt(x, y);
		
		// Choose a random velocity
		Random rand = new Random();
		float randAngle = (float) (rand.nextFloat() * 2 * Math.PI);
		float vX = SPEED * (float) Math.cos(randAngle);
		float vY = SPEED * (float) Math.sin(randAngle);
		velocity = new Pt(vX, vY);
	}
	
	public void update() {
		
		// If it's the first update, start the timer
		if(speedTimer == null) {
			speedTimer = new Timer();
		}
		
		// Check for collisions with the box wall:
		
		// Reverse the x-velocity if it hits the left or right wall
		if(pos.x() - RADIUS < 0 || boxWidth < pos.x() + RADIUS) {
			velocity.setX(velocity.x() * -1);
		}
		
		// Reverse the y-velocity if it hits the top or bottom wall
		if(pos.y() - RADIUS < 0 || boxHeight < pos.y() + RADIUS) {
			velocity.setY(velocity.y() * -1);
		}
		
		// Ensure that the particle stays within the box
		int wallOffset = 0;
		pos.constrain(RADIUS + wallOffset, RADIUS + wallOffset, boxWidth - RADIUS - wallOffset, boxHeight - RADIUS - wallOffset);
		
		// Move the particle
		
		// Move the particle, based on its velocity and the time elapsed since the last update cycle
		float dT = speedTimer.secsElapsed();
		float dX = velocity.x() * dT;
		float dY = velocity.y() * dT;
		pos.move(dX, dY);
		speedTimer.restart();
	}
	
	public static void updateGroup(List<Particle> particles) {
		
		// For each pair of particles:
		for(int i = 0; i < particles.size(); i++) {
			
			Particle pOne = particles.get(i);
			
			for(int j = i + 1; j < particles.size(); j++) {
				
				Particle pTwo = particles.get(j);
					
				// If the particles are intersecting:
				if(areIntersecting(pOne, pTwo)) {
					
					// React the particles
					react(pOne, pTwo);
				}
			}
		}
		
		// Update the position of each particle
		for(Particle p : particles) {
			p.update();
		}
	}
	
	private static void react(Particle pOne, Particle pTwo) {
		
		// Change the type of the particles
		
		if(Species.willForwardsRxOccur(pOne.species, pTwo.species)) {
			
			// Change the particles from reactants to products
			pOne.species = Species.C;
			pTwo.species = Species.D;
		}
		
		else if(Species.willReverseRxOccur(pOne.species, pTwo.species)) {
			
			// Change the particles from products to reactants
			pOne.species = Species.A;
			pTwo.species = Species.B;
		}
		
		// Change the direction of the velocities of the particles to model a perfectly elastic collision
		
		Pt normalVector = new Pt(pOne.pos, pTwo.pos);
		normalVector.setMagnitude(Particle.SPEED);
		pTwo.velocity.set(normalVector);
		
		normalVector.multiply(-1);
		pOne.velocity.set(normalVector);
		
		// Move pTwo so that it isn't intersecting pOne any longer (to avoid subsequent redundant collisions)
		
		normalVector.setMagnitude(-1 * RADIUS * 2);
		Pt newPos = new Pt(pOne.pos);
		newPos.add(normalVector);
		pTwo.pos.set(newPos);
	}
	
	// Detect if two particles are physically intersecting
	private static boolean areIntersecting(Particle pOne, Particle pTwo) {	
		return Pt.isDistLessThan(pOne.pos, pTwo.pos, Particle.RADIUS * 2);
	}
	
	public void draw(Graphics2D g2d) {
		
		// Draw the particle at this location (accounting for the offset of the particle box)
		
		int r = RADIUS;
		int x = offset.intX() + pos.intX() - r;
		int y = offset.intY() + pos.intY() - r;
		
		g2d.setColor(species.color());
		g2d.fillOval(x, y, r * 2, r * 2);
		g2d.setColor(Color.BLACK);
		g2d.drawOval(x, y, r * 2, r * 2);
	}
}
