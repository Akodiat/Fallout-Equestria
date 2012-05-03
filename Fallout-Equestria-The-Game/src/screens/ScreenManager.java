package screens;

public class ScreenManager {
	private int width;
	private int height;
	
	private Screen activeScreen;
	
	public ScreenManager(Screen screen, int width, int height) {
		this.width = width;
		this.height = height;
		this.setActiveScreen(screen);
	}
	
	public void transition(Screen newScreen, int time) {
		this.setActiveScreen(newScreen);
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
