package utils;

public class GameTime {
	public final float DeltaTime;
	public final float TotalTime;
	
	protected GameTime(float totalTime, float deltaTime) {
		this.DeltaTime = deltaTime;
		this.TotalTime = totalTime;
	}
}
