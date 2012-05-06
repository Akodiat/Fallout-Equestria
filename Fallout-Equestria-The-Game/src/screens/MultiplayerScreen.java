package screens;

import math.Point2;
import utils.GameTime;
import GUI.controls.Panel;

public class MultiplayerScreen extends GUIScreen{
	private Panel contentPanel;
	
	public MultiplayerScreen() {
		initialize();
	}
	
	@Override
	public void initialize() {
		
	}
	
	@Override
	public void update(GameTime time, boolean isTop) {
		contentPanel.checkMouseInput(new Point2(0,0), getScreenManager().getMouse());
	}
	
	@Override
	public void draw(GameTime time) {
		contentPanel.render(getContext(), time);
	}
	
	@Override
	public void swap(GameTime time) {
		
	}
}
