package screenCore;

import java.util.ArrayList;
import java.util.List;

import animation.AnimationPlayer;
import animation.Bones;
import animation.ManeStyle;
import animation.PonyColorChangeHelper;
import animation.TextureDictionary;
import animation.TextureEntry;

import common.PlayerCharacteristics;
import common.Race;
import common.SpecialStats;
import components.SpecialComp;

import GUI.ScrollEventArgs;
import GUI.controls.Button;
import GUI.controls.ImageBox;
import GUI.controls.PonyBox;
import GUI.controls.Slider;
import GUI.controls.Spinner;
import GUI.controls.Textfield;
import GUI.graphics.GUIRenderingContext;
import GUI.graphics.LookAndFeel;
import math.Vector2;
import misc.EventArgs;
import misc.IEventListener;
import utils.GameTime;
import utils.Rectangle;
import utils.TimeSpan;
import content.ContentManager;
import entityFramework.IEntityArchetype;
import graphics.Color;
import graphics.ShaderEffect;
import graphics.SpriteBatch;
import graphics.Texture2D;


public class PonyCreatorScreen extends TransitioningGUIScreen {

	public static final String PonyArchetypePath = "Player.archetype";
	private GUIRenderingContext context;

	private ContentManager contentManager = new ContentManager("resources");
	private AnimationPlayer pony;
	private PlayerCharacteristics character = new PlayerCharacteristics();
	private TextureDictionary assetDictionary = this.contentManager.load("rddict.tdict", TextureDictionary.class);

	private Vector2 ponyPosition = new Vector2(1150,200);
	private Vector2 ponyScale = new Vector2(2,2);
	
	private ImageBox bG;
	
	private Button button;
	private Button button2;
	private Button button3;
	private Textfield textfield;
	
	private Slider bodyRedSlider;
	private Slider bodyGreenSlider;
	private Slider bodyBlueSlider;
	
	private Slider eyeRedSlider;
	private Slider eyeGreenSlider;
	private Slider eyeBlueSlider;
	
	private Slider maneRedSlider;
	private Slider maneGreenSlider;
	private Slider maneBlueSlider;
	
	private Spinner maneStyleSpinner;
	private Spinner eyeStyleSpinner;
	
	private List<ManeEntries> maneStyles;
	private List<EyeEntries> eyeStyles;

	public PonyCreatorScreen(String lookAndFeelPath) {
		super(false, TimeSpan.fromSeconds(1d), TimeSpan.fromSeconds(0.5d), lookAndFeelPath);
	}

	@Override
	public void initialize(ContentManager contentManager) {
		super.initialize(contentManager);
		
		this.bG = new ImageBox();
		this.bG.setImage(contentManager.load("darkPonyville.png", Texture2D.class));
		this.bG.setBounds(this.ScreenManager.getViewport());
		super.addGuiControl(this.bG, new Vector2(0,this.ScreenManager.getViewport().Height), new Vector2(0,0), 
								new Vector2(0,this.ScreenManager.getViewport().Height));
		addPony();
		
		maneStyles = new ArrayList<ManeEntries>();
		ManeEntries rDManeStyle = new ManeEntries(new ManeStyle("RDUPPERMANE", "RDLOWERMANE", "RDUPPERTAIL", "RDLOWERTAIL"), this.assetDictionary);
		maneStyles.add(rDManeStyle);	
		ManeEntries tSManeStyle = new ManeEntries(new ManeStyle("TSUPPERMANE", "TSLOWERMANE", "TSUPPERTAIL", "TSLOWERTAIL"), this.assetDictionary);
		maneStyles.add(tSManeStyle);
		
		eyeStyles = new ArrayList<EyeEntries>();
		EyeEntries rDEyeStyle = new EyeEntries("RDEYE", this.assetDictionary);
		eyeStyles.add(rDEyeStyle);
		EyeEntries tSEyeStyle = new EyeEntries("TSEYE", this.assetDictionary);
		eyeStyles.add(tSEyeStyle);
		
		PonyBox test = new PonyBox();
		test.setPonyName("PLAYERPONY");
		test.setPonyPlayer(pony.clone());
		test.setBounds(0, 0, 1366, 768);
		super.addGuiControl(test, new Vector2(0,this.ScreenManager.getViewport().Height), new Vector2(200,200), 
				new Vector2(0,this.ScreenManager.getViewport().Height));


		this.textfield = new Textfield();
		this.textfield.setBounds(0,0,250,25);
		this.textfield.setText("");
		this.textfield.setFont(contentManager.loadFont("Andale Mono20.xml"));
		this.textfield.setFgColor(Color.White);
		this.textfield.setMaxLength(25);
		super.addGuiControl(this.textfield, new Vector2(-100,this.ScreenManager.getViewport().Height), new Vector2(100,25), 
				new Vector2(0,this.ScreenManager.getViewport().Height));
		
		this.bodyRedSlider = new Slider();
		this.bodyRedSlider.setFgColor(new Color(255,50,50,255));
		this.bodyRedSlider.setBounds(0, 0, 200, 30);
		super.addGuiControl(this.bodyRedSlider, new Vector2(-100,this.ScreenManager.getViewport().Height), new Vector2(100,100), 
				new Vector2(0,this.ScreenManager.getViewport().Height));
		this.bodyRedSlider.setBounds(new Rectangle(0,0,250,30));
		this.bodyRedSlider.setScrollMax(255);
		this.bodyRedSlider.setScrollValue(255);
		this.bodyRedSlider.setHorizontal(true);
		this.bodyRedSlider.addScrollListener(new IEventListener<ScrollEventArgs>() {
			
			@Override
			public void onEvent(Object sender, ScrollEventArgs e) {
				setBodyColor();
			}
		});
		this.bodyGreenSlider = new Slider();
		this.bodyGreenSlider.setBounds(0, 0, 200, 30);
		this.bodyGreenSlider.setFgColor(new Color(50,255,50,255));
		super.addGuiControl(this.bodyGreenSlider, new Vector2(-100,this.ScreenManager.getViewport().Height), new Vector2(100,150), 
				new Vector2(0,this.ScreenManager.getViewport().Height));
		this.bodyGreenSlider.setBounds(new Rectangle(0,0,250,30));
		this.bodyGreenSlider.setScrollMax(255);
		this.bodyGreenSlider.setScrollValue(255);
		this.bodyGreenSlider.setHorizontal(true);
		this.bodyGreenSlider.addScrollListener(new IEventListener<ScrollEventArgs>() {
			
			@Override
			public void onEvent(Object sender, ScrollEventArgs e) {
				setBodyColor();
			}
		});
		this.bodyBlueSlider = new Slider();
		this.bodyBlueSlider.setBounds(0, 0, 200, 30);
		this.bodyBlueSlider.setFgColor(new Color(50,50,255,255));
		super.addGuiControl(this.bodyBlueSlider, new Vector2(-100,this.ScreenManager.getViewport().Height), new Vector2(100,200), 
				new Vector2(0,this.ScreenManager.getViewport().Height));
		this.bodyBlueSlider.setBounds(new Rectangle(0,0,250,30));
		this.bodyBlueSlider.setScrollMax(255);
		this.bodyBlueSlider.setScrollValue(255);
		this.bodyBlueSlider.setHorizontal(true);
		this.bodyBlueSlider.addScrollListener(new IEventListener<ScrollEventArgs>() {
			
			@Override
			public void onEvent(Object sender, ScrollEventArgs e) {
				setBodyColor();
			}
		});

		this.eyeRedSlider = new Slider();
		this.eyeRedSlider.setBounds(0, 0, 200, 30);
		this.eyeRedSlider.setFgColor(new Color(255,50,50,255));
		super.addGuiControl(this.eyeRedSlider, new Vector2(-100,this.ScreenManager.getViewport().Height), new Vector2(100,300), 
				new Vector2(0,this.ScreenManager.getViewport().Height));
		this.eyeRedSlider.setBounds(new Rectangle(0,0,250,30));
		this.eyeRedSlider.setScrollMax(255);
		this.eyeRedSlider.setScrollValue(255);
		this.eyeRedSlider.setHorizontal(true);
		this.eyeRedSlider.addScrollListener(new IEventListener<ScrollEventArgs>() {
			
			@Override
			public void onEvent(Object sender, ScrollEventArgs e) {
				setEyeColor();
			}
		});
		this.eyeGreenSlider = new Slider();
		this.eyeGreenSlider.setBounds(0, 0, 200, 30);
		this.eyeGreenSlider.setFgColor(new Color(50,255,50,255));
		super.addGuiControl(this.eyeGreenSlider, new Vector2(-100,this.ScreenManager.getViewport().Height), new Vector2(100,350), 
				new Vector2(0,this.ScreenManager.getViewport().Height));
		this.eyeGreenSlider.setBounds(new Rectangle(0,0,250,30));
		this.eyeGreenSlider.setScrollMax(255);
		this.eyeGreenSlider.setScrollValue(255);
		this.eyeGreenSlider.setHorizontal(true);
		this.eyeGreenSlider.addScrollListener(new IEventListener<ScrollEventArgs>() {
			
			@Override
			public void onEvent(Object sender, ScrollEventArgs e) {
				setEyeColor();
			}
		});
		this.eyeBlueSlider = new Slider();
		this.eyeBlueSlider.setBounds(0, 0, 200, 30);
		this.eyeBlueSlider.setFgColor(new Color(50,50,255,255));
		super.addGuiControl(this.eyeBlueSlider, new Vector2(-100,this.ScreenManager.getViewport().Height), new Vector2(100,400), 
				new Vector2(0,this.ScreenManager.getViewport().Height));
		this.eyeBlueSlider.setBounds(new Rectangle(0,0,250,30));
		this.eyeBlueSlider.setScrollMax(255);
		this.eyeBlueSlider.setScrollValue(255);
		this.eyeBlueSlider.setHorizontal(true);
		this.eyeBlueSlider.addScrollListener(new IEventListener<ScrollEventArgs>() {
			
			@Override
			public void onEvent(Object sender, ScrollEventArgs e) {
				setEyeColor();
			}
		});
		
		this.maneRedSlider = new Slider();
		this.maneRedSlider.setBounds(0, 0, 200, 30);
		this.maneRedSlider.setFgColor(new Color(255,50,50,255));
		super.addGuiControl(this.maneRedSlider, new Vector2(-100,this.ScreenManager.getViewport().Height), new Vector2(100,500), 
				new Vector2(0,this.ScreenManager.getViewport().Height));
		this.maneRedSlider.setBounds(new Rectangle(0,0,250,30));
		this.maneRedSlider.setScrollMax(255);
		this.maneRedSlider.setScrollValue(255);
		this.maneRedSlider.setHorizontal(true);
		this.maneRedSlider.addScrollListener(new IEventListener<ScrollEventArgs>() {
			
			@Override
			public void onEvent(Object sender, ScrollEventArgs e) {
				setManeColor();
			}
		});
		this.maneGreenSlider = new Slider();
		this.maneGreenSlider.setBounds(0, 0, 200, 30);
		this.maneGreenSlider.setFgColor(new Color(50,255,50,255));
		super.addGuiControl(this.maneGreenSlider, new Vector2(-100,this.ScreenManager.getViewport().Height), new Vector2(100,550), 
				new Vector2(0,this.ScreenManager.getViewport().Height));
		this.maneGreenSlider.setBounds(new Rectangle(0,0,250,30));
		this.maneGreenSlider.setScrollMax(255);
		this.maneGreenSlider.setScrollValue(255);
		this.maneGreenSlider.setHorizontal(true);
		this.maneGreenSlider.addScrollListener(new IEventListener<ScrollEventArgs>() {
			@Override
			public void onEvent(Object sender, ScrollEventArgs e) {
				setManeColor();
			}
		});
		this.maneBlueSlider = new Slider();
		this.maneBlueSlider.setBounds(0, 0, 200, 30);
		this.maneBlueSlider.setFgColor(new Color(50,50,255,255));
		super.addGuiControl(this.maneBlueSlider, new Vector2(-100,this.ScreenManager.getViewport().Height), new Vector2(100,600), 
				new Vector2(0,this.ScreenManager.getViewport().Height));
		this.maneBlueSlider.setBounds(new Rectangle(0,0,250,30));
		this.maneBlueSlider.setScrollMax(255);
		this.maneBlueSlider.setScrollValue(255);
		this.maneBlueSlider.setHorizontal(true);
		this.maneBlueSlider.addScrollListener(new IEventListener<ScrollEventArgs>() {
			
			@Override
			public void onEvent(Object sender, ScrollEventArgs e) {
				setManeColor();
			}
		});
		
		Texture2D minus = contentManager.loadTexture("GUI/minus.png");
		Texture2D plus = contentManager.loadTexture("GUI/plus.png");

		this.maneStyleSpinner = new Spinner(this.maneStyles.size(),1,1,1,plus,minus);
		this.maneStyleSpinner.setBounds(20, 20, 160, 40);
		this.maneStyleSpinner.setBgColor(Color.Transparent);
		this.maneStyleSpinner.setFont(contentManager.loadFont("arialb20.xml"));
		super.addGuiControl(this.maneStyleSpinner, 
				new Vector2(250, this.ScreenManager.getViewport().Height), 
				new Vector2(250,this.ScreenManager.getViewport().Height-100), 
				new Vector2(250, this.ScreenManager.getViewport().Height));
		this.maneStyleSpinner.addClicked(new IEventListener<EventArgs>() {

			@Override
			public void onEvent(Object sender, EventArgs e) {
				setManeStyle();
			}
		});
		
		this.eyeStyleSpinner = new Spinner(this.eyeStyles.size(),1,1,1,plus,minus);
		this.eyeStyleSpinner.setBounds(20, 20, 160, 40);
		this.eyeStyleSpinner.setBgColor(Color.Transparent);
		this.eyeStyleSpinner.setFont(contentManager.loadFont("arialb20.xml"));
		super.addGuiControl(this.eyeStyleSpinner, 
				new Vector2(250, this.ScreenManager.getViewport().Height), 
				new Vector2(450,this.ScreenManager.getViewport().Height-100), 
				new Vector2(250, this.ScreenManager.getViewport().Height));
		this.eyeStyleSpinner.addClicked(new IEventListener<EventArgs>() {

			@Override
			public void onEvent(Object sender, EventArgs e) {
				setEyeStyle();
			}
		});
		this.button = new Button();
		this.button.setText("Done!");
		this.button.setBounds(250,710,200,50);
		super.addGuiControl(button, new Vector2(this.ScreenManager.getViewport().Width - 250, this.ScreenManager.getViewport().Height), 
									 new Vector2(this.ScreenManager.getViewport().Width - 250,this.ScreenManager.getViewport().Height-200), 
									 new Vector2(this.ScreenManager.getViewport().Width - 250, this.ScreenManager.getViewport().Height));

		LookAndFeel feel = contentManager.load(this.lookAndFeelPath, LookAndFeel.class);
		
		feel.setDefaultFont(contentManager.loadFont("arialb20.xml"));
		ShaderEffect dissabledEffect = contentManager.loadShaderEffect("GrayScale.effect");
		context = new GUIRenderingContext(this.ScreenManager.getSpriteBatch(), feel, dissabledEffect);

		this.button.addClicked(new IEventListener<EventArgs>() {

			@Override
			public void onEvent(Object sender, EventArgs e) {
				savePlayerCharacteristics();
				goBack();
			}
		});

		this.button2 = new Button();
		this.button2.setText("Back");
		this.button2.setBounds(250,710,200,50);
		super.addGuiControl(button2, new Vector2(this.ScreenManager.getViewport().Width - 250, this.ScreenManager.getViewport().Height), 
									 new Vector2(this.ScreenManager.getViewport().Width - 250,this.ScreenManager.getViewport().Height-100), 
									 new Vector2(this.ScreenManager.getViewport().Width - 250, this.ScreenManager.getViewport().Height));
		feel.setDefaultFont(contentManager.loadFont("arialb20.xml"));
		context = new GUIRenderingContext(this.ScreenManager.getSpriteBatch(), feel, dissabledEffect);

		this.button2.addClicked(new IEventListener<EventArgs>() {

			@Override
			public void onEvent(Object sender, EventArgs e) {
				goBack();
			}
		});
		
		this.button3 = new Button();
		this.button3.setText("Randomize");
		this.button3.setBounds(250,710,200,50);
		super.addGuiControl(button2, new Vector2(this.ScreenManager.getViewport().Width - 250, this.ScreenManager.getViewport().Height), 
									 new Vector2(this.ScreenManager.getViewport().Width - 250,this.ScreenManager.getViewport().Height-100), 
									 new Vector2(this.ScreenManager.getViewport().Width - 250, this.ScreenManager.getViewport().Height));
		feel.setDefaultFont(contentManager.loadFont("arialb20.xml"));
		context = new GUIRenderingContext(this.ScreenManager.getSpriteBatch(), feel, dissabledEffect);

		this.button3.addClicked(new IEventListener<EventArgs>() {

			@Override
			public void onEvent(Object sender, EventArgs e) {
				randomizeAttributes();
			}
		});

		
		setBodyColor();
		setEyeColor();
		setManeColor();
	}
	protected void randomizeAttributes() {
		// TODO Auto-generated method stub
		
	}

	protected void savePlayerCharacteristics() {
		this.character.archetypePath = PonyArchetypePath;
		
		this.character.bodyColor = getBodyColor();
		this.character.eyeColor = getEyeColor();
		this.character.maneColor = getManeColor();
		
		this.character.maneStyle = this.maneStyles.get((int)this.maneStyleSpinner.getValue()-1).maneStyle;
		this.character.eyeTexture = this.eyeStyles.get((int)this.eyeStyleSpinner.getValue()-1).eyePath;
		
		this.character.name = this.textfield.getText();
		this.character.race = Race.EARTHPONY.getValue();
		
		this.character.special = new SpecialStats(5, 3, 6, 7, 5, 7, 8);
	}

	protected void setEyeStyle() {
		int eyeIndex = (int)this.eyeStyleSpinner.getValue()-1;
		this.pony.setBoneTexture(Bones.EYE.getValue(), this.eyeStyles.get(eyeIndex).eyeEntry);
	}

	protected void setManeStyle() {
		int maneIndex = (int)this.maneStyleSpinner.getValue()-1;
		this.pony.setBoneTexture(Bones.UPPERMANE.getValue(), this.maneStyles.get(maneIndex).upperManeEntry);
		this.pony.setBoneTexture(Bones.LOWERMANE.getValue(), this.maneStyles.get(maneIndex).lowerManeEntry);
		this.pony.setBoneTexture(Bones.UPPERTAIL.getValue(), this.maneStyles.get(maneIndex).upperTailEntry);
		this.pony.setBoneTexture(Bones.LOWERTAIL.getValue(), this.maneStyles.get(maneIndex).lowerTailEntry);
	}

	public void goBack() {
		this.exitScreen();
	}
	
	protected void setBodyColor() {
		Color color = new Color(this.bodyRedSlider.getScrollValue(),this.bodyGreenSlider.getScrollValue(),this.bodyBlueSlider.getScrollValue(),255);
		PonyColorChangeHelper.setBodyColor(color, pony);
	}
	protected void setEyeColor() {
		Color color = new Color(this.eyeRedSlider.getScrollValue(),this.eyeGreenSlider.getScrollValue(),this.eyeBlueSlider.getScrollValue(),255);
		PonyColorChangeHelper.setEyeColor(color, pony);
	}
	protected void setManeColor() {
		Color color = new Color(this.maneRedSlider.getScrollValue(),this.maneGreenSlider.getScrollValue(),this.maneBlueSlider.getScrollValue(),255);
		PonyColorChangeHelper.setManeColor(color, pony);
	}
	
	protected Color getBodyColor() {
		Color color = new Color(this.bodyRedSlider.getScrollValue(),this.bodyGreenSlider.getScrollValue(),this.bodyBlueSlider.getScrollValue(),255);
		return color;
	}
	protected Color getEyeColor() {
		Color color = new Color(this.eyeRedSlider.getScrollValue(),this.eyeGreenSlider.getScrollValue(),this.eyeBlueSlider.getScrollValue(),255);
		return color;
	}
	protected Color getManeColor() {
		Color color = new Color(this.maneRedSlider.getScrollValue(),this.maneGreenSlider.getScrollValue(),this.maneBlueSlider.getScrollValue(),255);
		return color;
	}

	protected void showPauseScreen() {
		this.ScreenManager.addScreen("PauseScreen");
	}


	@Override
	public void update(GameTime time, boolean otherScreeenHasFocus,
			boolean coveredByOtherScreen) {
		if(!otherScreeenHasFocus) {
			super.update(time, otherScreeenHasFocus, coveredByOtherScreen);
			this.pony.update(time);
		}
	}

	private void addPony() {	
		this.pony = this.contentManager.load("rdset.animset", AnimationPlayer.class);;
		this.pony.startAnimation("idle");
	}
	
	@Override
	public void render(GameTime time, SpriteBatch batch) {
		super.render(time, batch);
		batch.begin();
		this.pony.draw(batch, ponyPosition, false, 0, Color.White, ponyScale);
		batch.end();
	}
	
	private class ManeEntries{
		TextureEntry upperManeEntry;
		TextureEntry lowerManeEntry;
		TextureEntry upperTailEntry;
		TextureEntry lowerTailEntry;
		ManeStyle maneStyle;
		public ManeEntries(ManeStyle maneStyle, TextureDictionary assetDict) {
			this.upperManeEntry = assetDict.extractTextureEntry(maneStyle.upperManeStyle);
			this.lowerManeEntry = assetDict.extractTextureEntry(maneStyle.lowerManeStyle);
			this.upperTailEntry = assetDict.extractTextureEntry(maneStyle.upperTailStyle);
			this.lowerTailEntry = assetDict.extractTextureEntry(maneStyle.lowerTailStyle);
			this.maneStyle = maneStyle;
		}
	}
	
	private class EyeEntries{
		TextureEntry eyeEntry;
		String eyePath;
		public EyeEntries(String eyePath, TextureDictionary assetDict) {
			this.eyeEntry = assetDict.extractTextureEntry(eyePath);
			this.eyePath = eyePath;
		}
	}
	
}
