package screens;

import utils.GameTime;
import utils.Mouse;

public interface Screen {	
	public void initialize();
	public void update(GameTime time, boolean isTop);
	public void draw(GameTime time);
	public void handleInput(Mouse m);
	public boolean isFocused();
	public void swap(GameTime time);
	public TransitionState getTransitionState();
}
