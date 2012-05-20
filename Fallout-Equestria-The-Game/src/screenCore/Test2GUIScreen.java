package screenCore;

import graphics.Color;
import math.Vector2;
import GUI.Button;
import content.ContentManager;
import utils.EventArgs;
import utils.IEventListener;
import utils.Rectangle;
import utils.time.TimeSpan;

public class Test2GUIScreen extends TransitioningGUIScreen {

	public Test2GUIScreen(String lookAndFeelPath) {
		super(false, TimeSpan.fromSeconds(1.0f), TimeSpan.fromSeconds(.5f), lookAndFeelPath);
	}
	
	public void initialize(ContentManager manager) {
		super.initialize(manager);	
		
		Rectangle vp = this.ScreenManager.getViewport();
		int x = vp.Width - 250;
		
		Button button0 = new Button();
		button0.setBounds(x,40,200,50);
		button0.setBgColor(Color.EmeraldGreen);
		button0.setText("GameOfLife");
		this.addGuiControl(button0, new Vector2(vp.Width + 200,900), new Vector2(x,40),new Vector2(-200,40));
		
		button0.addClicked(new IEventListener<EventArgs>() {
			public void onEvent(Object sender, EventArgs e) {
				showGOL();
			}
		});
		
		Button button1 = new Button();
		button1.setBounds(x,140,200,50);
		button1.setText("Pause Screen");
		this.addGuiControl(button1, new Vector2(vp.Width + 200,140), new Vector2(x,140),new Vector2(-200,140));
		
		button1.addClicked(new IEventListener<EventArgs>() {
			public void onEvent(Object sender, EventArgs e) {
				showPauseScreen();
			}
		});
		
		Button button2 = new Button();
		button2.setBounds(x,240,200,50);
		button2.setText("Back");
		this.addGuiControl(button2, new Vector2(vp.Width + 200,240), new Vector2(x,240),new Vector2(-200,240));
		
		button2.addClicked(new IEventListener<EventArgs>() {	
			public void onEvent(Object sender, EventArgs e) {
				removeThis();
			}
		});
	}

	protected void showGOL() {
		this.ScreenManager.removeAllScreens();
		this.ScreenManager.addScreen("GOLScreen");
	}

	protected void showPauseScreen() {
		this.ScreenManager.addScreen("PauseScreen");
		
	}

	protected void removeThis() {
		this.exitScreen();
	}

}
