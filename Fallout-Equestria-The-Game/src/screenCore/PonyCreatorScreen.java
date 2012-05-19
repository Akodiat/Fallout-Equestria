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

import GUI.IItemFormater;
import GUI.ItemEventArgs;
import GUI.ScrollEventArgs;
import GUI.controls.Button;
import GUI.controls.ComboBox;
import GUI.controls.ImageBox;
import GUI.controls.Label;
import GUI.controls.Panel;
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
import content.PlayerCharacteristicsWriter;
import entityFramework.IEntityArchetype;
import graphics.Color;
import graphics.ShaderEffect;
import graphics.SpriteBatch;
import graphics.Texture2D;


public class PonyCreatorScreen extends TransitioningGUIScreen {

	public static final String PonyArchetypePath = "Player.archetype";
	private GUIRenderingContext context;

	private ContentManager contentManager = new ContentManager("resources");
	private PlayerCharacteristicsWriter charWriter = new PlayerCharacteristicsWriter(contentManager,"trololo");
	
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
	
	private ComboBox<ManeEntries> maneComboBox;
	private ComboBox<EyeEntries> eyeComboBox;
	
	private List<ManeEntries> maneStyles;
	private List<EyeEntries> eyeStyles;
	
	private Label nameLabel;
	private Label bodyLabel;
	private Label eyeLabel;
	private Label maneLabel;

	private Panel bodyPanel;
	private Panel eyePanel;
	private Panel manePanel;
	
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
		ManeEntries rDManeStyle = new ManeEntries("Rainbow style!", new ManeStyle("RDUPPERMANE", "RDLOWERMANE", "RDUPPERTAIL", "RDLOWERTAIL"), this.assetDictionary);
		maneStyles.add(rDManeStyle);	
		ManeEntries tSManeStyle = new ManeEntries("Twilight style!", new ManeStyle("TSUPPERMANE", "TSLOWERMANE", "TSUPPERTAIL", "TSLOWERTAIL"), this.assetDictionary);
		maneStyles.add(tSManeStyle);
		
		this.maneComboBox = new ComboBox<ManeEntries>();
		this.maneComboBox.setBounds(0,200, 250, 30);
		this.maneComboBox.setBgColor(new Color(0,0,0,0));
		this.maneComboBox.setFont(contentManager.loadFont("Monofonto24.xml"));
		this.maneComboBox.addItem(rDManeStyle);
		this.maneComboBox.addItem(tSManeStyle);
		this.maneComboBox.setItemFormater(new IItemFormater<ManeEntries>(){
			@Override
			public String formatItem(ManeEntries item) {
				return item.name;
			}
		});
		this.maneComboBox.addSelectedChangedListener(new IEventListener<ItemEventArgs<ManeEntries>>() {
			@Override
			public void onEvent(Object sender, ItemEventArgs<ManeEntries> e) {
				setManeStyle();
			}
		});
		
		eyeStyles = new ArrayList<EyeEntries>();
		EyeEntries rDEyeStyle = new EyeEntries("Rainbow style!", "RDEYE", this.assetDictionary);
		eyeStyles.add(rDEyeStyle);
		EyeEntries tSEyeStyle = new EyeEntries("Twilight style!", "TSEYE", this.assetDictionary);
		eyeStyles.add(tSEyeStyle);
		
		this.eyeComboBox = new ComboBox<EyeEntries>();
		this.eyeComboBox.setBounds(0,200, 250, 30);
		this.eyeComboBox.setBgColor(new Color(0,0,0,0));
		this.eyeComboBox.setFont(contentManager.loadFont("Monofonto24.xml"));
		this.eyeComboBox.addItem(rDEyeStyle);
		this.eyeComboBox.addItem(tSEyeStyle);
		this.eyeComboBox.setItemFormater(new IItemFormater<EyeEntries>(){
			@Override
			public String formatItem(EyeEntries item) {
				return item.name;
			}
		});
		eyeComboBox.addSelectedChangedListener(new IEventListener<ItemEventArgs<EyeEntries>>() {
			@Override
			public void onEvent(Object sender, ItemEventArgs<EyeEntries> e) {
				setEyeStyle();
			}
		});
		
		PonyBox test = new PonyBox();
		test.setPonyName("PLAYERPONY");
		test.setPonyPlayer(pony.clone());
		test.setBounds(0, 0, 1366, 768);
		super.addGuiControl(test, new Vector2(0,this.ScreenManager.getViewport().Height), new Vector2(200,200), 
				new Vector2(0,this.ScreenManager.getViewport().Height));


		this.textfield = new Textfield();
		this.textfield.setBounds(0,0,250,25);
		this.textfield.setText("");
		this.textfield.setFont(contentManager.loadFont("Monofonto24.xml"));
		this.textfield.setFgColor(Color.White);
		this.textfield.setMaxLength(25);
		super.addGuiControl(this.textfield, new Vector2(-100,this.ScreenManager.getViewport().Height), new Vector2(100,25), 
				new Vector2(0,this.ScreenManager.getViewport().Height));
		
		this.bodyRedSlider = new Slider();
		this.bodyRedSlider.setFgColor(new Color(255,50,50,255));
		this.bodyRedSlider.setBounds(0, 50, 200, 30);
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
		this.bodyGreenSlider.setBounds(new Rectangle(0,100,250,30));
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
		this.bodyBlueSlider.setBounds(new Rectangle(0,150,250,30));
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
		this.maneRedSlider.setBounds(0, 50, 200, 30);
		this.maneRedSlider.setFgColor(new Color(255,50,50,255));
//		super.addGuiControl(this.maneRedSlider, new Vector2(-100,this.ScreenManager.getViewport().Height), new Vector2(100,500), 
//				new Vector2(0,this.ScreenManager.getViewport().Height));
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
//		super.addGuiControl(this.maneGreenSlider, new Vector2(-100,this.ScreenManager.getViewport().Height), new Vector2(100,550), 
//				new Vector2(0,this.ScreenManager.getViewport().Height));
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
//		super.addGuiControl(this.maneBlueSlider, new Vector2(-100,this.ScreenManager.getViewport().Height), new Vector2(100,600), 
//				new Vector2(0,this.ScreenManager.getViewport().Height));
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
		
		this.bodyLabel = new Label();
		this.bodyLabel.setText("Body");
		this.bodyLabel.setBgColor(Color.Transparent);
		
		this.button = new Button();
		this.button.setText("Done!");
		this.button.setBounds(250,710,200,50);
		super.addGuiControl(button, new Vector2(this.ScreenManager.getViewport().Width - 250, this.ScreenManager.getViewport().Height), 
									 new Vector2(this.ScreenManager.getViewport().Width - 250,this.ScreenManager.getViewport().Height-200), 
									 new Vector2(this.ScreenManager.getViewport().Width - 250, this.ScreenManager.getViewport().Height));

		LookAndFeel feel = contentManager.load(this.lookAndFeelPath, LookAndFeel.class);
		
		feel.setDefaultFont(contentManager.loadFont("Monofonto24.xml"));
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
		feel.setDefaultFont(contentManager.loadFont("Monofonto24.xml"));
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
		super.addGuiControl(button3, new Vector2(this.ScreenManager.getViewport().Width - 250, this.ScreenManager.getViewport().Height), 
									 new Vector2(this.ScreenManager.getViewport().Width - 250,this.ScreenManager.getViewport().Height-300), 
									 new Vector2(this.ScreenManager.getViewport().Width - 250, this.ScreenManager.getViewport().Height));
		feel.setDefaultFont(contentManager.loadFont("Monofonto24.xml"));
		context = new GUIRenderingContext(this.ScreenManager.getSpriteBatch(), feel, dissabledEffect);

		this.button3.addClicked(new IEventListener<EventArgs>() {

			@Override
			public void onEvent(Object sender, EventArgs e) {
				randomizeAttributes();
			}
		});
		
		this.bodyPanel = new Panel();
		this.bodyPanel.setBounds(0, 0, 250, 500);
		this.bodyPanel.setBgColor(new Color(0,0,0,0.3f));
		this.bodyPanel.addChild(this.bodyRedSlider);
		this.bodyPanel.addChild(this.bodyGreenSlider);
		this.bodyPanel.addChild(this.bodyBlueSlider);
		super.addGuiControl(this.bodyPanel, new Vector2(0,this.ScreenManager.getViewport().Height), new Vector2(0,300), 
		new Vector2(0,this.ScreenManager.getViewport().Height));
		
		this.eyePanel = new Panel();
		this.eyePanel.setBounds(0, 0, 250, 500);
		this.eyePanel.setBgColor(new Color(0,0,0,0.3f));
		this.eyePanel.addChild(this.eyeComboBox);
		this.eyePanel.addChild(this.eyeRedSlider);
		this.eyePanel.addChild(this.eyeGreenSlider);
		this.eyePanel.addChild(this.eyeBlueSlider);
		super.addGuiControl(this.eyePanel, new Vector2(0,this.ScreenManager.getViewport().Height), new Vector2(300,300), 
		new Vector2(0,this.ScreenManager.getViewport().Height));
		
		this.manePanel = new Panel();
		this.manePanel.setBounds(0, 0, 250, 500);
		this.manePanel.setBgColor(new Color(0,0,0,0.3f));
		this.manePanel.addChild(this.maneComboBox);
		this.manePanel.addChild(this.maneRedSlider);
		this.manePanel.addChild(this.maneGreenSlider);
		this.manePanel.addChild(this.maneBlueSlider);
		super.addGuiControl(this.manePanel, new Vector2(0,this.ScreenManager.getViewport().Height), new Vector2(300,300), 
		new Vector2(0,this.ScreenManager.getViewport().Height));

		setBodyColor();
		setEyeColor();
		setManeColor();
	}
	protected void randomizeAttributes() {
		this.bodyRedSlider.setScrollValue((int)(Math.random()*this.bodyRedSlider.getScrollMax()));
		this.bodyGreenSlider.setScrollValue((int)(Math.random()*this.bodyGreenSlider.getScrollMax()));
		this.bodyBlueSlider.setScrollValue((int)(Math.random()*this.bodyBlueSlider.getScrollMax()));
		
		this.eyeRedSlider.setScrollValue((int)(Math.random()*this.eyeRedSlider.getScrollMax()));
		this.eyeGreenSlider.setScrollValue((int)(Math.random()*this.eyeGreenSlider.getScrollMax()));
		this.eyeBlueSlider.setScrollValue((int)(Math.random()*this.eyeBlueSlider.getScrollMax()));
		
		this.maneRedSlider.setScrollValue((int)(Math.random()*this.maneRedSlider.getScrollMax()));
		this.maneGreenSlider.setScrollValue((int)(Math.random()*this.maneGreenSlider.getScrollMax()));
		this.maneBlueSlider.setScrollValue((int)(Math.random()*this.maneBlueSlider.getScrollMax()));
	}

	protected void savePlayerCharacteristics() {
		this.character.archetypePath = PonyArchetypePath;
		
		this.character.bodyColor = getBodyColor();
		this.character.eyeColor = getEyeColor();
		this.character.maneColor = getManeColor();
		
		this.character.maneStyle = this.maneComboBox.getSelectedItem().maneStyle;
		this.character.eyeTexture = this.eyeComboBox.getSelectedItem().eyePath;
		
		this.character.name = this.textfield.getText();
		this.character.race = Race.EARTHPONY.getValue();
		
		this.character.special = new SpecialStats(5, 3, 6, 7, 5, 7, 8);
		
		this.charWriter.savePlayerCharacteristics(this.character);
	}

	protected void setEyeStyle() {
		this.pony.setBoneTexture(Bones.EYE.getValue(), this.eyeComboBox.getSelectedItem().eyeEntry);
	}

	protected void setManeStyle() {
		this.pony.setBoneTexture(Bones.UPPERMANE.getValue(), this.maneComboBox.getSelectedItem().upperManeEntry);
		this.pony.setBoneTexture(Bones.LOWERMANE.getValue(), this.maneComboBox.getSelectedItem().lowerManeEntry);
		this.pony.setBoneTexture(Bones.UPPERTAIL.getValue(), this.maneComboBox.getSelectedItem().upperTailEntry);
		this.pony.setBoneTexture(Bones.LOWERTAIL.getValue(), this.maneComboBox.getSelectedItem().lowerTailEntry);
	}

	public void goBack() {
		this.exitScreen();
	}
	
	protected void setBodyColor() {
		Color color = new Color(this.bodyRedSlider.getScrollValue(),this.bodyGreenSlider.getScrollValue(),this.bodyBlueSlider.getScrollValue(),255);
		setBodyColor(color);
	}
	protected void setBodyColor(Color color) {
		PonyColorChangeHelper.setBodyColor(color, pony);
	}
	protected void setEyeColor() {
		Color color = new Color(this.eyeRedSlider.getScrollValue(),this.eyeGreenSlider.getScrollValue(),this.eyeBlueSlider.getScrollValue(),255);
		setEyeColor(color);
	}
	protected void setEyeColor(Color color) {
		PonyColorChangeHelper.setEyeColor(color, pony);
	}
	protected void setManeColor() {
		Color color = new Color(this.maneRedSlider.getScrollValue(),this.maneGreenSlider.getScrollValue(),this.maneBlueSlider.getScrollValue(),255);
		setManeColor(color);
	}
	protected void setManeColor(Color color) {
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
		String name;
		TextureEntry upperManeEntry;
		TextureEntry lowerManeEntry;
		TextureEntry upperTailEntry;
		TextureEntry lowerTailEntry;
		ManeStyle maneStyle;
		public ManeEntries(String name, ManeStyle maneStyle, TextureDictionary assetDict) {
			this.name = name;
			this.upperManeEntry = assetDict.extractTextureEntry(maneStyle.upperManeStyle);
			this.lowerManeEntry = assetDict.extractTextureEntry(maneStyle.lowerManeStyle);
			this.upperTailEntry = assetDict.extractTextureEntry(maneStyle.upperTailStyle);
			this.lowerTailEntry = assetDict.extractTextureEntry(maneStyle.lowerTailStyle);
			this.maneStyle = maneStyle;
		}
	}
	
	private class EyeEntries{
		String name;
		TextureEntry eyeEntry;
		String eyePath;
		public EyeEntries(String name, String eyePath, TextureDictionary assetDict) {
			this.name = name;
			this.eyeEntry = assetDict.extractTextureEntry(eyePath);
			this.eyePath = eyePath;
		}
	}
	
}
