package screenCore;

import math.Vector2;
import misc.EventArgs;
import misc.IEventListener;
import GUI.controls.Button;
import content.ContentManager;
import utils.TimeSpan;

public class MultiplayerScreen extends TransitioningGUIScreen{

	public MultiplayerScreen(String lookAndFeelPath) {
		super(false, TimeSpan.fromSeconds(1.0f), TimeSpan.fromSeconds(1.0f), lookAndFeelPath);
	}
	
	public void initialize(ContentManager manager) {
		super.initialize(manager);
		
		Button joinBtn = new Button();
		joinBtn.setBounds(1366/2 - 200, 240, 400, 40);
		joinBtn.setText("Join game");
		this.addGuiControl(joinBtn, new Vector2(1366/2-200, 768), new Vector2(1366/2 -200, 240), new Vector2(1366/2-200, 768));
		
		Button hostBtn = new Button();
		hostBtn.setBounds(1366/2 -200, 300, 400, 40);
		hostBtn.setText("Host game");
		this.addGuiControl(hostBtn, new Vector2(1366/2-200, 768), new Vector2(1366/2 -200, 300), new Vector2(1366/2-200, 768));
		
		Button backBtn = new Button(); 
		backBtn.setBounds(1366/2 -200, 360, 400, 40);
		backBtn.setText("Cancel");
		this.addGuiControl(backBtn, new Vector2(1366/2-200, 768), new Vector2(1366/2-200, 360), new Vector2(1366/2-200, 768));
		
		joinBtn.addClicked(new IEventListener<EventArgs>() {
			public void onEvent(Object sender, EventArgs e) {
				gotoConnectScreen();		
			}
		});
		
		hostBtn.addClicked(new IEventListener<EventArgs>() {
			public void onEvent(Object sender, EventArgs e) {
				gotoHostScreen();		
			}
		});
		
		backBtn.addClicked(new IEventListener<EventArgs>() {
			public void onEvent(Object sender, EventArgs e) {
				goBack();		
			}
		});
	}
	
	public void gotoHostScreen() {
		this.ScreenManager.addScreen("Host");
	}
	
	public void gotoConnectScreen() {
		this.ScreenManager.addScreen("Connect");
	}
	
	public void goBack() {
		this.exitScreen();
	}
}
