package screenCore;

import math.Point2;
import graphics.Color;
import graphics.ShaderEffect;
import graphics.SpriteBatch;
import content.ContentManager;
import utils.input.Keyboard;
import utils.input.Mouse;
import utils.time.GameTime;
import utils.time.TimeSpan;
import GUI.GUIFocusManager;
import GUI.GUIRenderingContext;
import GUI.LookAndFeel;
import GUI.Panel;

public class GUIScreen extends GameScreen {
	private GUIRenderingContext renderingContext;
	
	@SuppressWarnings("unused")
	private GUIFocusManager focusManager;
	protected Panel controlPanel;
	protected final String lookAndFeelPath;
	
		
	public GUIScreen(boolean popup, TimeSpan transOnTime, TimeSpan transOffTime, String lookAndFeelPath) {
		super(popup, transOnTime, transOffTime);
		this.lookAndFeelPath = lookAndFeelPath;
	}

	@Override
	public void initialize(ContentManager contentManager) {
		LookAndFeel feel = contentManager.load(lookAndFeelPath, LookAndFeel.class);
		feel.setDefaultFont(contentManager.loadFont("Monofonto24.xml"));
		ShaderEffect dissabledEffect = contentManager.loadShaderEffect("GrayScale.effect");
		renderingContext = new GUIRenderingContext(this.ScreenManager.getSpriteBatch(), feel, dissabledEffect);
		
		
		controlPanel = new Panel();
		controlPanel.setBounds(this.ScreenManager.getViewport());
		controlPanel.setBgColor(new Color(0,0,0,0));
		
		focusManager = new GUIFocusManager(controlPanel);
	}

	@Override
	public void handleInput(Mouse mouse, Keyboard keyboard) {
		controlPanel.checkMouseInput(new Point2(0,0), mouse);
		controlPanel.checkKeyboardInput(keyboard);
	}

	@Override
	public void render(GameTime time, SpriteBatch spriteBatch) {
		controlPanel.render(renderingContext, time);
		
	}
	
}
