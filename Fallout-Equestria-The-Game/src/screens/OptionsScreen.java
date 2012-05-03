package screens;

import math.Point2;
import utils.GameTime;
import utils.TimeSpan;
import GUI.controls.Panel;

public class OptionsScreen extends GUIScreen {
	private Panel contentPanel;
	private TransitionState state;
	
	public OptionsScreen() {
		initialize();
		this.state = TransitionState.NONE;
	}
	
	@Override
	public void initialize() {
		
	}
	
	@Override
	public void update() {
		contentPanel.checkMouseInput(new Point2(0,0), getScreenManager().getMouse());
		contentPanel.checkKeyboardInput();
	}
	
	@Override
	public void draw(GameTime time) {
		contentPanel.render(getContext(), time);
	}
	
	@Override
	public void switchTo(TimeSpan time) {
		this.state = TransitionState.TO;
	}
	
	@Override
	public void switchFrom(TimeSpan time) {
		this.state = TransitionState.FROM;
	}
}
