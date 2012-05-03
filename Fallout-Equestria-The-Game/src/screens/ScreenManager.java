package screens;

public class ScreenManager {
	private int width;
	private int height;
	
	private Screen activeScreen;
	
	public ScreenManager(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void transition(Screen fromScreen, Screen toScreen, int fromTime, int toTime) {
		fromScreen.switchFrom(fromTime);
		toScreen.switchTo(toTime);
		
		this.setActiveScreen(toScreen);
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Screen getActiveScreen() {
		return activeScreen;
	}

	public void setActiveScreen(Screen screen) {
		this.activeScreen = screen;
	}
}
