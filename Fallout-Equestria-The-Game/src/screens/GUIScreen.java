package screens;

import content.ContentManager;
import graphics.SpriteBatch;
import graphics.Texture2D;
import GUI.GUIFocusManager;
import GUI.VisibleElement;
import GUI.graphics.GUIRenderingContext;
import GUI.graphics.LookAndFeel;
import utils.Camera2D;
import utils.GameTime;
import utils.Mouse;

public class GUIScreen implements Screen{
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Mouse mouse;
	
	private GUIRenderingContext context;
	private GUIFocusManager manager;

	@Override
	public void initialize() {
		this.camera = new Camera2D(screenDim, screenDim);
		this.spriteBatch = new SpriteBatch(screenDim);
		this.mouse = new Mouse();
		
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
		
		context = new GUIRenderingContext(spriteBatch, lookAndFeel, ContentManager.loadShaderEffect("GrayScale.effect"));
	}

	@Override
	public void update() {
		this.mouse.poll(camera);
	}

	@Override
	public void draw(GameTime time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleInput(Mouse m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFocused() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void switchTo(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void switchFrom(int time) {
		// TODO Auto-generated method stub
		
	}
}
