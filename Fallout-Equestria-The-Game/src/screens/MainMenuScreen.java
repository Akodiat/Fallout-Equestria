package screens;

import math.Point2;
import graphics.Color;
import GUI.controls.Button;
import GUI.controls.Panel;
import utils.GameTime;
import utils.TimeSpan;

public class MainMenuScreen extends GUIScreen {
	private Panel contentPanel;
	private TransitionState state;
	
	public MainMenuScreen() {
		super();
		initialize();
		
		this.state = TransitionState.NONE;
	}
	
	@Override
	public void initialize() {
		contentPanel = new Panel();
		contentPanel.setBounds(1000, 100, 250, 400);
		
		Button singlePlayerButton = new Button();
		singlePlayerButton.setBounds(0, 0, 120, 40);
		singlePlayerButton.setText("Singleplayer");
		singlePlayerButton.setFgColor(new Color(200,50,200,255));
		contentPanel.addChild(singlePlayerButton);
		
		Button multiPlayerButton = new Button(); 
		multiPlayerButton.setBounds(0, 60, 120, 40);
		multiPlayerButton.setText("Multiplayer");
		multiPlayerButton.setFgColor(new Color(200,50,200,255));
		contentPanel.addChild(multiPlayerButton);
		
		Button optionsButton = new Button(); 
		optionsButton.setBounds(0, 120, 120, 40);
		optionsButton.setText("Options");
		optionsButton.setFgColor(new Color(200,50,200,255));
		contentPanel.addChild(optionsButton);
		
		Button poniesButton = new Button();
		poniesButton.setBounds(0, 180, 120, 40);
		poniesButton.setText("My little ponies");
		poniesButton.setFgColor(new Color(200,50,20,255));
		contentPanel.addChild(poniesButton);
		
		Button creditsButton = new Button();
		creditsButton.setBounds(0, 240, 120, 40);
		creditsButton.setText("Credits");
		creditsButton.setFgColor(new Color(200,50,200,255));
		contentPanel.addChild(creditsButton);
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
