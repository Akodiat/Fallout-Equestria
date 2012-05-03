package screens;

import utils.Mouse;

public interface Screen {
	public void initialize();
	public void update();
	public void draw();
	public void handleInput(Mouse m);
	public boolean isFocused();
}
