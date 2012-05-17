package screenCore;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import graphics.Color;
import math.Vector2;
import misc.EventArgs;
import misc.IEventListener;
import GUI.controls.Button;
import GUI.controls.ChatPanel;
import GUI.controls.Label;
import GUI.controls.ListBox;
import content.ContentManager;
import utils.Rectangle;
import utils.TimeSpan;

public class LobbyGUI extends TransitioningGUIScreen{
	private ListBox<String> playerListBox;
	private Connection[] connections;

	public LobbyGUI(String lookAndFeelPath) {
		super(false, TimeSpan.fromSeconds(2.0f), TimeSpan.fromSeconds(1.0f), lookAndFeelPath);
	}
	
	public void initialize(ContentManager manager) {
		super.initialize(manager);
		this.connections = this.ScreenManager.getNetwork().getServer().getConnections();
		
		this.ScreenManager.getNetwork().getServer().addListener(new Listener() {	
			@Override
			public void connected(Connection arg0) {
				updatePlayerList();
			}
		});
		
		Rectangle vp = this.ScreenManager.getViewport();
		int x = vp.Width - 250;
		
		if(this.ScreenManager.getNetwork().isServer()) {
			Button playButton = new Button();
			playButton.setBounds(x, 568, 200, 50);
			playButton.setText("Start the game!");
			this.addGuiControl(playButton, new Vector2(vp.Width + 200, 568), new Vector2(x, 568), new Vector2(-200, 568));
			
			playButton.addClicked(new IEventListener<EventArgs>() {
				@Override
				public void onEvent(Object sender, EventArgs e) {
					LobbyGUI.this.getScreenManager().removeAllScreens();
					LobbyGUI.this.getScreenManager().addScreen("Level1");
				}
			});
			
			Button closeButton = new Button();
			closeButton.setBounds(x, 678, 200, 50);
			closeButton.setText("Close server");
			this.addGuiControl(closeButton, new Vector2(vp.Width + 200, 678), new Vector2(x, 678),new Vector2(-200, 678));
			
		} else {
			
			Button backBtn = new Button();
			backBtn.setBounds(x, 678, 200, 50);
			backBtn.setText("Back");
			this.addGuiControl(backBtn, new Vector2(vp.Width + 200), new Vector2(x,678), new Vector2(-200, 678));
			backBtn.addClicked(new IEventListener<EventArgs>() {
				@Override
				public void onEvent(Object sender, EventArgs e) {
					gotoConnectScreen();
				}
			});
		}
		
		ChatPanel chat = new ChatPanel();
		chat.setBounds(0,500,1016,228);
		chat.setFont(manager.loadFont("arialb20.xml"));
		this.addGuiControl(chat, new Vector2(0,768), new Vector2(0,500), new Vector2(0,768));
		
		Label playersLabel = new Label();
		playersLabel.setBounds(x, 30, 200, 30);
		playersLabel.setText("Connected players:");
		this.addGuiControl(playersLabel, new Vector2(x, -30), new Vector2(x, 30), new Vector2(x, -30));
		
		this.playerListBox = new ListBox<String>();
		playerListBox.setBounds(x, 60, 200, 400);
		playerListBox.setFont(manager.loadFont("Andale Mono20.xml"));
		playerListBox.setBgColor(new Color(0,0,0,255));
		playerListBox.setFgColor(Color.Black);
		this.addGuiControl(playerListBox, new Vector2(vp.Width, 60), new Vector2(x, 60), new Vector2(vp.Width, 60));
		
		for(int i = 0; i < connections.length; i++) {
			if(connections[i].isConnected())
				playerListBox.addItem(connections[i].getRemoteAddressTCP().getHostName());
		}
	}
	
	public void updatePlayerList() {
		this.connections = this.ScreenManager.getNetwork().getServer().getConnections();
		for(int i = 0; i < connections.length; i++) {
			if(connections[i].isConnected())
				playerListBox.addItem(connections[i].getRemoteAddressTCP().getHostName());
		}
	}
	
	public void gotoConnectScreen() {
		this.ScreenManager.addScreen("Connect");
	}

}
