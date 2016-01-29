import java.awt.Color;


public enum Species {

	// 1A(aq) + 1B(aq) <equil> 1C(aq) + 1D(aq)
	
	A (Color.RED, 4),
	B (Color.BLUE, 2),
	C (Color.GREEN, 4),
	D (Color.ORANGE, 2);
	
	private Color color; // the color associated with this species (for drawing purposes)
	private int ptR; // the radius of a graph point representing this species
	
	private Species(Color argColor, int argPtR) {
		color = argColor;
		ptR = argPtR;
	}
	
	/*
	 * Accessors
	 */
	
	public Color color() {
		return color;
	}
	
	public int ptRadius() {
		return ptR;
	}
	
	/*
	 * Reaction Utilities
	 */
	
	public static boolean willForwardsRxOccur(Species one, Species two) {
		
		return (one == A && two == B) || (one == B && two == A);
	}
	
	public static boolean willReverseRxOccur(Species one, Species two) {
		
		return (one == C && two == D) || (one == D && two == C);
	}
}
