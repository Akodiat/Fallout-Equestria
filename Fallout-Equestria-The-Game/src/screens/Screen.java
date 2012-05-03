package screens;

import utils.GameTime;
import utils.Mouse;
import utils.TimeSpan;

public interface Screen {	
	public void initialize();
	public void update();
	public void draw(GameTime time);
	public void handleInput(Mouse m);
	public boolean isFocused();
	public void switchTo(TimeSpan time);
	public void switchFrom(TimeSpan time);
}
