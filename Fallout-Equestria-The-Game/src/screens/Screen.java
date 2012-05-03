package screens;

public interface Screen {
	public void update();
	public void draw();
	public void handleInput();
	public boolean isFocused();
}
