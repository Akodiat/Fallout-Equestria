package screens;

import utils.GameTime;
import utils.Mouse;
import utils.Rectangle;

public interface Screen {
	public static final Rectangle screenDim = new Rectangle(0,0,1366,768);
	
	public void initialize();
	public void update();
	public void draw(GameTime time);
	public void handleInput(Mouse m);
	public boolean isFocused();
	public void switchTo(int time);
	public void switchFrom(int time);
}
