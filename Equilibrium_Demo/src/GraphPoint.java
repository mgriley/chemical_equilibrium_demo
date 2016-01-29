public class GraphPoint {
	
	private float x;
	private int y;
	private Species species;
	
	public GraphPoint(float argX, int argY, Species argSpecies) {
		
		x = argX;
		y = argY;
		species = argSpecies;
	}
	
	public float getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Species species() {
		return species;
	}
}