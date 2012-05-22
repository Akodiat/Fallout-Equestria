package screenCore;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import common.messages.GoToScreenMessage;
import components.BehaviourComp;

import graphics.Color;
import math.Vector2;
import misc.ChatHelper;
import misc.EntityGroups;
import GUI.Button;
import GUI.ChatPanel;
import GUI.Label;
import GUI.ListBox;
import content.ContentManager;
import entityFramework.IEntity;
import utils.EventArgs;
import utils.IEventListener;
import utils.Rectangle;
import utils.input.Keyboard;
import utils.input.Keys;
import utils.input.Mouse;
import utils.time.GameTime;
import utils.time.TimeSpan;

public class LobbyGUI extends TransitioningGUIScreen{
	private ListBox<String> playerListBox;
	private Connection[] connections;
	private Object lock = new Object();
	private GoToScreenMessage message = null;
	private ChatHelper chatHelper;
	private List<Level> levels = new ArrayList<Level>();
	private ChatPanel chat;
	
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
		
		for(int i = 0; i < this.ScreenManager.getScreens().size(); i++) {
			GameScreen screen = this.ScreenManager.getScreens().get(i);
			
			if(screen instanceof Level) {
				levels.add((Level) screen);
			}
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
		
		this.chat = new ChatPanel();
		chat.setBounds(-1016, -1000,1016,228);
		chat.setFont(manager.loadFont("arialb20.xml"));
		this.addGuiControl(chat, new Vector2(0,768), new Vector2(0,500), new Vector2(0,768));
		
		this.chatHelper = new ChatHelper(chat, this.ScreenManager.getNetwork(), this.ScreenManager.getPlayerCharacteristics().name);
		
		chat.addFocusGainedEvent(new IEventListener<EventArgs>() {
			@Override
			public void onEvent(Object sender, EventArgs e) {
				for(int i = 0; i < levels.size(); i++) {
					levels.get(i).setTyping(true);
					if(ScreenManager.getNetwork().isServer()) {
						Set<IEntity> entities = levels.get(i).World.getEntityManager().getEntityGroup(EntityGroups.Players.toString());
						for(IEntity entity : entities) {
							entity.getComponent(BehaviourComp.class).setEnabled(false);
						}
					}
				}
			}	
		});
		
		chat.addFocusLostEvent(new IEventListener<EventArgs>() {
			@Override
			public void onEvent(Object sender, EventArgs e) {
				for(int i = 0; i < levels.size(); i++) {
					levels.get(i).setTyping(false);
					if(ScreenManager.getNetwork().isServer()) {
						Set<IEntity> entities = levels.get(i).World.getEntityManager().getEntityGroup(EntityGroups.Players.toString());
						for(IEntity entity : entities) {
							entity.getComponent(BehaviourComp.class).setEnabled(true);
						}
					}
				}
			}	
		});
		
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
		this.chatHelper.intiialize();
	}
	private void changeScreen(String newScreen){
			ScreenManager.getNetwork().removeAllListeners();
			ScreenManager.removeAllScreens();
			ScreenManager.addScreen(newScreen);
	}
	@Override
	public void update(GameTime time, boolean otherScreeenHasFocus,
			boolean coveredByOtherScreen) {
		super.update(time, otherScreeenHasFocus, coveredByOtherScreen);
		this.chatHelper.update();
		
		synchronized(lock){
			if(this.message != null){
				changeScreen(this.message.newScreen);
			}
		}
	}

	@Override
	public void handleInput(Mouse m, Keyboard k) {
		super.handleInput(m, k);
		
		if(k.wasKeyPressed(Keys.Enter)) {
			chat.setFocused(!chat.isFocused());
		}
	}
	
}
