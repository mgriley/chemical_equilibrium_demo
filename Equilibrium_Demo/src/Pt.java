

import java.awt.Graphics2D;


public class Pt {
	
	private float x, y;
	
	/*
	 * Constructors
	 */
	
	public Pt() {
		x = 0;
		y = 0;
	}
	
	public Pt (float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Pt(Pt a) {
		this.x = a.x;
		this.y = a.y;
	}
	
	// Generate a point that represents the 2D vector from pt "from" to point "to"
	public Pt(Pt from, Pt to) {
		
		x = to.x - from.x;
		y = to.y - from.y;
	}
	
	/*
	 * Accessors
	 */
	
	public float x() {
		return x;
	}
	
	public float y() {
		return y;
	}
	
	public int intX() {
		return (int) x;
	}
	
	public int intY() {
		return (int) y;
	}
	
	public float getMagnitude() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public Pt getNormalized() {
		
		Pt newPt = new Pt(this);
		newPt.normalize();
		return newPt;
	}
	
	public boolean isContainedIn(float minX, float minY, float maxX, float maxY) {
		
		boolean xContained = minX < x && x < maxX;
		boolean yContained = minY < y && y < maxY;
		
		return xContained && yContained;
	}
	
	public Pt getMidPt(Pt a) {
		
		float midX = (x + a.x) / 2;
		float midY = (y + a.y) / 2;
		return new Pt(midX, midY);
	}
	
	/*
	 * Distance operations
	 */
	
	public float distToPt(Pt a) {
		return (float) Math.sqrt(Math.pow(a.x - x, 2) + Math.pow(a.y - y, 2));
	}
	
	public float manhattanDistToPt(Pt a) {
		return Math.abs(a.x - x) + Math.abs(a.y - y);
	}
	
	public float distSqToPt(Pt a) {
		return (float) Math.pow(a.x - x, 2) + (float) Math.pow(a.y - y, 2);
	}
	 
	public float distToPt(float x, float y) {
		return distToPt(new Pt(x, y));
	}
	
	public float distSqToPt(float x, float y) {
		return distSqToPt(new Pt(x, y));
	}
	
	public static boolean isDistLessThan(Pt a, Pt b, float r) {
		return a.distSqToPt(b) < r * r;
	}
	
	/*
	 * Mutators
	 */
	
	public void setX(float newX) {
		x = newX;
	}
	
	public void setY(float newY) {
		y = newY;
	}
	
	public void set(float newX, float newY) {
		x = newX;
		y = newY;
	}
	
	public void set(Pt a) {
		x = a.x;
		y = a.y;
	}
	
	public void setMagnitude(float mag) {
		
		normalize();
		multiply(mag);
	}
	
	public void move(float offsetX, float offsetY) {
		x += offsetX;
		y += offsetY;
	}
	
	public void move(Pt offset) {
		move(offset.x, offset.y);
	}
	
	// Precondition: mag != 0
	public void normalize() {
		float mag = getMagnitude();
		x /= mag;
		y /= mag;
	}
	
	public void multiply(float a) {
		x *= a;
		y *= a;
	}
	
	public Pt add(Pt a) {
		x += a.x;
		y += a.y;
		return this;
	}
	
	public void constrain(int minX, int minY, int maxX, int maxY) {
		
		x = Math.max(minX, x);
		x = Math.min(maxX, x);
		
		y = Math.max(minY, y);
		y = Math.min(maxY, y);
	}
	
	/*
	 * Other
	 */
	
	public void connect(Graphics2D g2d, Pt a) {
		
		g2d.drawLine(intX(), intY(), a.intX(), a.intY());
	}
	
	public String toString() {
		return "x: " + x + ", y: " + y;
	}
	
	@Override
	public boolean equals(Object obj) {
		Pt p = (Pt) obj;
		return this.x == p.x && this.y == p.y;
	}
	
	public Pt clone() {
		return new Pt(x, y);
	}
}
