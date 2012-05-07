package screenCore;

import graphics.SpriteBatch;
import math.MathHelper;
import utils.GameTime;
import utils.Keyboard;
import utils.Mouse;
import utils.TimeSpan;
import content.ContentManager;

public abstract class GameScreen {

	private final boolean popup;
	private boolean exiting;
	private TimeSpan transitionOnTime;
	private TimeSpan transitionOffTime;
	private float transitionPosition;
	private ScreenState screenState;
	private boolean otherScreenHasFocus;
	protected ScreenManager ScreenManager;
	
	public GameScreen(boolean popup, TimeSpan transOnTime, TimeSpan transOffTime) {
		this.popup = popup;
		this.exiting = false;
		this.transitionOnTime = transOnTime;
		this.transitionOffTime = transOffTime;
		this.screenState = ScreenState.TransitionOn;
		this.transitionPosition = 1f;
		this.ScreenManager = null;
		this.otherScreenHasFocus = false;
	}

	
	public abstract void initialize(ContentManager contentManager);
	public abstract void handleInput(Mouse mouse, Keyboard keyboard);
	public abstract void render(GameTime time, SpriteBatch spriteBatch);
	
	
	public void update(GameTime time, boolean otherScreeenHasFocus,
			boolean coveredByOtherScreen) {
		this.otherScreenHasFocus = otherScreeenHasFocus;
		
		if(this.exiting) {	
			screenState = ScreenState.TransitionOff;
			if(!updateTransition(time, this.transitionOffTime, 1)) {
				this.ScreenManager.removeScreen(this);
			}
		} else if(coveredByOtherScreen) {
			if(updateTransition(time, transitionOffTime, 1)) {
				screenState = ScreenState.TransitionOff;
			} else {
				screenState = ScreenState.Hidden;
			}
		} else {
			if(updateTransition(time, transitionOnTime, -1)) {
				screenState = ScreenState.TransitionOn;
			} else {
				if(screenState == ScreenState.TransitionOn) {
					this.onTransitionFinished();
				}
				screenState = ScreenState.Active;
				
			}
		}
		
	}
	
	private boolean updateTransition(GameTime time,
			TimeSpan transitionTime, int direction) {
		float transitionDelta;
		
		if(transitionTime == TimeSpan.Zero) {
			transitionDelta = 1.0f;
		} else {
			transitionDelta = (float)(time.getElapsedTime().getTotalMiliSeconds() / 
									  transitionTime.getTotalMiliSeconds());
		}
		
		transitionPosition += transitionDelta * direction;
		
		if(direction < 0 && transitionPosition <= 0 ||
		   direction > 0 && transitionPosition >= 1) {
			transitionPosition = MathHelper.clamp(0.0f, 1.0f, transitionPosition);
			return false;
		}
		
		return true;
	}
	
	public void exitScreen()  {
		
		if(this.transitionOffTime.equals(TimeSpan.Zero)) {
			this.ScreenManager.removeScreen(this);
		} else {
			this.exiting = true;
		}
	}

	public void onTransitionFinished() {
		
	}

	public void reset() {
		this.exiting = false;
		this.transitionPosition = 1f;
	}
	
	public ScreenManager getScreenManager() {
		return ScreenManager;
	}
	protected void setScreenManager(ScreenManager screenManager) {
		ScreenManager = screenManager;
	}
	public boolean isPopup() {
		return popup;
	}
	public boolean isExiting() {
		return exiting;
	}
	public void setExiting(boolean exiting) {
		this.exiting = exiting;
	}
	public TimeSpan getTransitionOnTime() {
		return transitionOnTime;
	}
	public TimeSpan getTransitionOffTime() {
		return transitionOffTime;
	}
	public float getTransitionPosition() {
		return transitionPosition;
	}
	public ScreenState getScreenState() {
		return screenState;
	}
	public boolean doesOtherScreenHaveFocus() {
		return otherScreenHasFocus;
	}

	public void setScreenState(ScreenState state) {
		this.screenState = state;
		
	}

}
