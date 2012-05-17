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
		joinBtn.setBounds(-1000, -1000, 200, 50);
		joinBtn.setText("Join game");
		this.addGuiControl(joinBtn, new Vector2(1366/2-100, 768), new Vector2(1366/2 -100, 240), new Vector2(1366/2-100, 768));
		
		Button hostBtn = new Button();
		hostBtn.setBounds(-1000, -1000, 200, 50);
		hostBtn.setText("Host game");
		this.addGuiControl(hostBtn, new Vector2(1366/2-100, 768), new Vector2(1366/2 -100, 340), new Vector2(1366/2-100, 768));
		
		Button backBtn = new Button(); 
		backBtn.setBounds(-1000, -1000, 200, 50);
		backBtn.setText("Cancel");
		this.addGuiControl(backBtn, new Vector2(1366/2-100, 768), new Vector2(1366/2-100, 440), new Vector2(1366/2-100, 768));
		
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
		this.ScreenManager.getNetwork().HostGame();
		
		this.ScreenManager.removeAllScreens();
		this.ScreenManager.addScreen("Lobby");
		this.ScreenManager.addScreen("LobbyGUI");
	}
	
	public void gotoConnectScreen() {
		this.ScreenManager.addScreen("Connect");
	}
	
	public void goBack() {
		this.exitScreen();
	}
}
