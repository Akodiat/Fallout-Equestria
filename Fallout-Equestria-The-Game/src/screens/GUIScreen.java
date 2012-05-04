package screens;

import content.ContentManager;
import graphics.Color;
import graphics.Texture2D;
import GUI.GUIFocusManager;
import GUI.graphics.VisibleElement;
import GUI.graphics.GUIRenderingContext;
import GUI.graphics.LookAndFeel;
import utils.GameTime;
import utils.Mouse;

public class GUIScreen implements Screen{
	private ScreenManager screenManager;
	private boolean isFocused;
	private TransitionState state;

	private GUIRenderingContext context;
	private GUIFocusManager manager;

	@Override
	public void initialize() {
		this.state = TransitionState.NONE;
		
		Texture2D backgroundTexture = ContentManager.loadTexture("GUI/Fallout-2-icon.png");
		Texture2D buttonOverTexture = ContentManager.loadTexture("GUI/Fallout-2-2-icon.png");
		Texture2D buttonDownTexture = ContentManager.loadTexture("GUI/fallout_point_lookout.png");
		Texture2D sliderAndScrollBG = ContentManager.loadTexture("GUI/guiBackground.png");
		Texture2D textboxBG 		= ContentManager.loadTexture("GUI/guiBackground.png");
		Texture2D scrollButtonBG    = ContentManager.loadTexture("GUI/guiBackground.png");
		Texture2D textAreaBackground= ContentManager.loadTexture("GUI/guiBackground.png");
		
		LookAndFeel lookAndFeel = new LookAndFeel();
		lookAndFeel.setElement("Button_Background", new VisibleElement(backgroundTexture, backgroundTexture.getBounds()));
		lookAndFeel.setElement("Button_Over", new VisibleElement(buttonOverTexture, buttonOverTexture.getBounds()));
		lookAndFeel.setElement("Button_Down", new VisibleElement(buttonDownTexture, buttonDownTexture.getBounds()));
		lookAndFeel.setElement("Slider_Background", new VisibleElement(sliderAndScrollBG, sliderAndScrollBG.getBounds()));
		lookAndFeel.setElement("ScrollBar_Background", new VisibleElement(sliderAndScrollBG, sliderAndScrollBG.getBounds()));
		lookAndFeel.setElement("Textfield_Background", new VisibleElement(textboxBG, textboxBG.getBounds()));
		lookAndFeel.setElement("ScrollBar_Button_Background", new VisibleElement(scrollButtonBG, scrollButtonBG.getBounds()));
		lookAndFeel.setElement("ScrollBar_Button_Down", new VisibleElement(scrollButtonBG, scrollButtonBG.getBounds()));
		lookAndFeel.setElement("ScrollBar_Button_Over", new VisibleElement(scrollButtonBG, scrollButtonBG.getBounds()));
		lookAndFeel.setElement("TextArea_Background", new VisibleElement(textAreaBackground, textAreaBackground.getBounds()));	
		lookAndFeel.setDefaultFont(ContentManager.loadFont("arialb20.xml"));
		
		context = new GUIRenderingContext(screenManager.getSpriteBatch(), lookAndFeel, ContentManager.loadShaderEffect("GrayScale.effect"));
	}

	@Override
	public void update(GameTime time, boolean isTop) {
		screenManager.getMouse().poll(screenManager.getCamera());
	}

	@Override
	public void draw(GameTime time) {
		screenManager.getSpriteBatch().clearScreen(Color.Black);
	}

	@Override
	public void handleInput(Mouse m) {
		
	}

	@Override
	public boolean isFocused() {
		return isFocused;
	}

	@Override
	public void swap(GameTime time) {
		
	}
	
	public GUIRenderingContext getContext() {
		return context;
	}

	public void setContext(GUIRenderingContext context) {
		this.context = context;
	}

	public GUIFocusManager getManager() {
		return manager;
	}

	public void setManager(GUIFocusManager manager) {
		this.manager = manager;
	}

	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public void setScreenManager(ScreenManager screenManager) {
		this.screenManager = screenManager;
	}

	@Override
	public TransitionState getTransitionState() {
		return this.state;
	}

	public void setTransitionState(TransitionState state) {
		// TODO Auto-generated method stub
		
	}
}
