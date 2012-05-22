package screenCore;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import components.BehaviourComp;

import content.ContentManager;
import entityFramework.IEntity;
import math.Vector2;
import misc.ChatHelper;
import misc.EntityGroups;
import GUI.ChatPanel;
import utils.EventArgs;
import utils.IEventListener;
import utils.input.Keyboard;
import utils.input.Keys;
import utils.input.Mouse;
import utils.time.GameTime;
import utils.time.TimeSpan;

public class ChatScreen extends TransitioningGUIScreen {

	private ChatHelper chatHelper;
	private ChatPanel chat;
	private List<Level> levels = new ArrayList<Level>();
	
	public ChatScreen(String lookAndFeelPath) {
		super(false, TimeSpan.fromSeconds(2.0f), TimeSpan.fromSeconds(1.0f), lookAndFeelPath);
	}
	
	@Override
	public void initialize(ContentManager contentManager) {
		super.initialize(contentManager);
		
		this.chat = new ChatPanel();
		chat.setBounds(-1016, -1000,512,228);
		chat.setFont(contentManager.loadFont("arialb20.xml"));
		this.addGuiControl(chat, new Vector2(0,768), new Vector2(0,500), new Vector2(0,768));
		
		this.chatHelper = new ChatHelper(chat, this.ScreenManager.getNetwork(), this.ScreenManager.getPlayerCharacteristics().name);
		
		

		for(int i = 0; i < this.ScreenManager.getScreens().size(); i++) {
			GameScreen screen = this.ScreenManager.getScreens().get(i);
			
			if(screen instanceof Level) {
				levels.add((Level) screen);
			}
		}
		
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
		
	}
	
	@Override
	public void onTransitionFinished() {
		super.onTransitionFinished();
		this.chatHelper.intiialize();
	}
	
	@Override
	public void update(GameTime time, boolean otherScreeenHasFocus,
			boolean coveredByOtherScreen) {
		super.update(time, otherScreeenHasFocus, false);
		this.chatHelper.update();
		if(coveredByOtherScreen) {
			handleInput(this.ScreenManager.getMouse(), this.ScreenManager.getKeyboard());
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
