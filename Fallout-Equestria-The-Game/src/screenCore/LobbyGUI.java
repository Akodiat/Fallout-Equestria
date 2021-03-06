package screenCore;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import common.messages.GoToScreenMessage;
import graphics.Color;
import math.Vector2;
import GUI.Button;
import GUI.Label;
import GUI.ListBox;
import content.ContentManager;
import utils.EventArgs;
import utils.IEventListener;
import utils.Rectangle;
import utils.time.GameTime;
import utils.time.TimeSpan;

public class LobbyGUI extends TransitioningGUIScreen{
	private ListBox<String> playerListBox;
	private Connection[] connections;
	private Object lock = new Object();
	private GoToScreenMessage message = null;
	
	public LobbyGUI(String lookAndFeelPath) {
		super(false, TimeSpan.fromSeconds(2.0f), TimeSpan.fromSeconds(1.0f), lookAndFeelPath);
	}
	
	public void initialize(ContentManager manager) {
		super.initialize(manager);
		
		if(this.ScreenManager.getNetwork().isServer()) {
			this.connections = this.ScreenManager.getNetwork().getServer().getConnections();
			
			this.ScreenManager.getNetwork().getServer().addListener(new Listener() {	
				@Override
				public void connected(Connection arg0) {
					updatePlayerList();
				}
				@Override
				public void disconnected(Connection arg0) {
					updatePlayerList();
				}
			});
		}
		
		
		Rectangle vp = this.ScreenManager.getViewport();
		int x = vp.Width - 250;
		
		if(this.ScreenManager.getNetwork().isServer()) {
			Button playButton = new Button();
			playButton.setBounds(-1000, -1000, 200, 50);
			playButton.setText("Start the game!");
			this.addGuiControl(playButton, new Vector2(vp.Width + 200, 568), new Vector2(x, 568), new Vector2(-200, 568));
			
			playButton.addClicked(new IEventListener<EventArgs>() {
				@Override
				public void onEvent(Object sender, EventArgs e) {
					String screenName = "Level1";
					
					GoToScreenMessage message = new GoToScreenMessage();
					message.newScreen = screenName;
					
					ScreenManager.getNetwork().removeAllListeners();
					
					LobbyGUI.this.getScreenManager().removeAllScreens();
					LobbyGUI.this.getScreenManager().addScreen(screenName);
					LobbyGUI.this.getScreenManager().addScreen("ChatScreen");
					ScreenManager.getNetwork().getServer().sendToAllTCP(message);
				}
			});
			
			Button closeButton = new Button();
			closeButton.setBounds(-1000, -1000, 200, 50);
			closeButton.setText("Close server");
			this.addGuiControl(closeButton, new Vector2(vp.Width + 200, 678), new Vector2(x, 678),new Vector2(-200, 678));
			
			closeButton.addClicked(new IEventListener<EventArgs>() {
				@Override
				public void onEvent(Object sender, EventArgs e) {
					closeServer();
				}
			});
			
		} else {
			
			Button backBtn = new Button();
			backBtn.setBounds(-1000, -1000, 200, 50);
			backBtn.setText("Back");
			this.addGuiControl(backBtn, new Vector2(vp.Width + 200), new Vector2(x,678), new Vector2(-200, 678));
			backBtn.addClicked(new IEventListener<EventArgs>() {
				@Override
				public void onEvent(Object sender, EventArgs e) {
					gotoConnectScreen();
				}
			});
		}
		
		
		
		Label playersLabel = new Label();
		playersLabel.setBounds(-1000, -1000, 200, 30);
		playersLabel.setText("Connected players:");
		this.addGuiControl(playersLabel, new Vector2(x, -30), new Vector2(x, 30), new Vector2(x, -30));
		
		this.playerListBox = new ListBox<String>();
		playerListBox.setBounds(-1000, -1000, 200, 400);
		playerListBox.setFont(manager.loadFont("Andale Mono20.xml"));
		playerListBox.setBgColor(new Color(0,0,0,255));
		playerListBox.setFgColor(Color.White);
		this.addGuiControl(playerListBox, new Vector2(vp.Width, 60), new Vector2(x, 60), new Vector2(vp.Width, 60));
	}
	
	
	public void updatePlayerList() {
		this.connections = this.ScreenManager.getNetwork().getServer().getConnections();
		for(int i = 0; i < connections.length; i++) {
			if(connections[i].isConnected()) {
				//playerListBox.addItem(connections[i].getRemoteAddressTCP().getHostName());
			} else {
				playerListBox.removeItem(connections[i].getRemoteAddressTCP().getHostName());
			}
		}
	}
	
	public void closeServer() {
		this.getScreenManager().getNetwork().getServer().stop();
		System.exit(0);
	}
	
	public void gotoConnectScreen() {
		this.ScreenManager.addScreen("Connect");
	}
	
	
	@Override
	public void onTransitionFinished() {
		if(this.ScreenManager.getNetwork().isClient()){
			this.ScreenManager.getNetwork().getClient().addListener(new Listener(){
				@Override
				public void received(Connection connection, Object message) {
					if(message instanceof GoToScreenMessage){
						synchronized (lock) {
							LobbyGUI.this.ScreenManager.getNetwork().removeAllListeners();
							GoToScreenMessage screenMessage = (GoToScreenMessage) message;
							LobbyGUI.this.message = screenMessage;			
						}
					}
				}	
			});
		}
	}
	private void changeScreen(String newScreen){
			ScreenManager.getNetwork().removeAllListeners();
			ScreenManager.removeAllScreens();
			ScreenManager.addScreen(newScreen);
			ScreenManager.addScreen("ChatScreen");
	}
	@Override
	public void update(GameTime time, boolean otherScreeenHasFocus,
			boolean coveredByOtherScreen) {
		super.update(time, otherScreeenHasFocus, coveredByOtherScreen);
		
		synchronized(lock){
			if(this.message != null){
				changeScreen(this.message.newScreen);
			}
		}
	}
	
}
