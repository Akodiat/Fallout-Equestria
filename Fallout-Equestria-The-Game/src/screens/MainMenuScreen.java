package screens;

import graphics.Color;
import GUI.controls.Button;
import GUI.controls.Panel;
import utils.GameTime;
import utils.Mouse;

public class MainMenuScreen extends GUIScreen {
	private boolean isFocused;
	
	public MainMenuScreen() {
		initialize();
	}
	
	@Override
	public void initialize() {
		contentPanel = new Panel();
		contentPanel.setBounds(0, 0, 1366, 768);
		
		Button singlePlayerButton = new Button();
		singlePlayerButton.setBounds(1200, 200, 120, 40);
		singlePlayerButton.setText("Singleplayer");
		singlePlayerButton.setFgColor(new Color(200,50,200,255));
		contentPanel.addChild(singlePlayerButton);
		
		Button multiPlayerButton = new Button(); 
		multiPlayerButton.setBounds(1200, 260, 120, 40);
		multiPlayerButton.setText("Multiplayer");
		multiPlayerButton.setFgColor(new Color(200,50,200,255));
		contentPanel.addChild(multiPlayerButton);
		
		Button optionsButton = new Button(); 
		optionsButton.setBounds(1200, 320, 120, 40);
		optionsButton.setText("Options");
		optionsButton.setFgColor(new Color(200,50,200,255));
		contentPanel.addChild(optionsButton);
		
		Button creditsButton = new Button();
		creditsButton.setBounds(1200, 380, 120, 40);
		creditsButton.setText("Credits");
		creditsButton.setFgColor(new Color(200,50,200,255));
		contentPanel.addChild(creditsButton);
	}
	
	@Override
	public void update() {

	}

	@Override
	public void draw(GameTime time) {
		
	}

	@Override
	public void handleInput(Mouse m) {
		
	}

	@Override
	public boolean isFocused() {
		return isFocused;
	}

	@Override
	public void switchTo(int time) {
		
	}

	@Override
	public void switchFrom(int time) {
	
	}
}
