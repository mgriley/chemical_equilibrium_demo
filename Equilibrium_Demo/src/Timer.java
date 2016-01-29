

public class Timer {
	
	long startTime;
	
	public Timer() {
		startTime = System.currentTimeMillis();
	}
	
	public long millisElapsed() {
		return System.currentTimeMillis() - startTime;
	}
	
	public float secsElapsed() {
		return millisElapsed() / 1000.0f;
	}
	
	public void restart() {
		startTime = System.currentTimeMillis();
	}
}
