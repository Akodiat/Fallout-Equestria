package screenCore;

import animation.AnimationPlayer;
import animation.Bones;
import animation.PonyColorChangeHelper;
import animation.TextureDictionary;

import components.AnimationComp;
import components.TransformationComp;

import GUI.GUIFocusManager;
import GUI.controls.Button;
import GUI.controls.Panel;
import GUI.controls.Textfield;
import GUI.graphics.GUIRenderingContext;
import GUI.graphics.LookAndFeel;
import math.Point2;
import math.Vector2;
import misc.EventArgs;
import misc.IEventListener;
import utils.Camera2D;
import utils.GameTime;
import utils.Keyboard;
import utils.Mouse;
import utils.Rectangle;
import utils.TimeSpan;
import content.ContentManager;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entitySystems.AnimationSystem;
import entitySystems.RenderingSystem;
import graphics.Color;
import graphics.ShaderEffect;
import graphics.SpriteBatch;


public class PonyCreatorScreen extends EntityScreen {


	public static final String playerBasePath = "player.archetype";
	private GUIRenderingContext context;
	private Panel panel;
	private Button button;
	private Button button2;
	private Textfield textfield;
<<<<<<< HEAD
<<<<<<< HEAD

=======
	
>>>>>>> Removed annoying debugsysos
=======

>>>>>>> Added a nonfunctional textfield.
	private AnimationSystem animSys;
	private ContentManager contentManager = new ContentManager("resources");
	private IEntity pony;


	public PonyCreatorScreen() {
		super(false, TimeSpan.Zero, TimeSpan.Zero);
	}

	@Override
	protected void loadContent(ContentManager contentManager) {

		panel = new Panel();
		panel.setBounds(this.ScreenManager.getViewport());
<<<<<<< HEAD
<<<<<<< HEAD
		panel.setBgColor(new Color(0,0,0,0));


=======
		panel.setBgColor(Color.);
		
		
>>>>>>> Removed annoying debugsysos
=======
		panel.setBgColor(new Color(0,0,0,0));


>>>>>>> Added a nonfunctional textfield.
		this.button = new Button();
		this.button.setText("Do stuff");
		this.button.setBounds(0,710,200,50);
		this.panel.addChild(button);
		new GUIFocusManager(button);

		this.button2 = new Button();
		this.button2.setText("Do other stuff?");
		this.button2.setBounds(250,710,200,50);
		this.panel.addChild(button2);
<<<<<<< HEAD
<<<<<<< HEAD

		this.textfield = new Textfield();
		this.textfield.setText("");
		this.textfield.setBounds(100,100,200,30);
=======

		this.textfield = new Textfield();
		this.textfield.setText("");
		this.textfield.setBounds(100,100,300,30);
>>>>>>> Added a nonfunctional textfield.
		this.textfield.setFont(this.contentManager.loadFont("Andale Mono20.xml"));
		this.textfield.setFgColor(Color.White);
		this.textfield.setMaxLength(25);
		this.panel.addChild(this.textfield);

<<<<<<< HEAD
=======
		
		
		
>>>>>>> Removed annoying debugsysos
=======
>>>>>>> Added a nonfunctional textfield.
		LookAndFeel feel = contentManager.load("gui.tdict", LookAndFeel.class);
		feel.setDefaultFont(contentManager.loadFont("arialb20.xml"));
		ShaderEffect dissabledEffect = contentManager.loadShaderEffect("GrayScale.effect");
		context = new GUIRenderingContext(this.ScreenManager.getSpriteBatch(), feel, dissabledEffect);

		this.button.addClicked(new IEventListener<EventArgs>() {
			@Override
			public void onEvent(Object sender, EventArgs e) {
				System.out.println("Stuff has been done!");
			}
		});

		this.button2.addClicked(new IEventListener<EventArgs>() {

			@Override
			public void onEvent(Object sender, EventArgs e) {
				System.out.println("Other stuff has been done!");
			}
		});

	}

	protected void showPauseScreen() {
		this.ScreenManager.addScreen("PauseScreen");
	}


	@Override
	public void update(GameTime time, boolean otherScreeenHasFocus,
			boolean coveredByOtherScreen) {
		if(!otherScreeenHasFocus) {
			super.update(time, otherScreeenHasFocus, coveredByOtherScreen);
		}
	}

	@Override
	protected void addEntities(IEntityManager entityManager) {	
		this.pony = entityManager.createEntity(this.contentManager.load(playerBasePath, IEntityArchetype.class));

		TransformationComp transformationComp = pony.getComponent(TransformationComp.class);
		transformationComp.setPosition(this.ScreenManager.getViewport().Width - (transformationComp.getOrigin().X*4), (transformationComp.getOrigin().Y)*7);
		transformationComp.setScale(new Vector2(2,2));
<<<<<<< HEAD
<<<<<<< HEAD

=======
		
>>>>>>> Removed annoying debugsysos
=======

>>>>>>> Added a nonfunctional textfield.
		AnimationPlayer player = this.contentManager.load("rdset.animset", AnimationPlayer.class);;
		player.startAnimation("idle");
		AnimationComp animComp = new AnimationComp(player);
		PonyColorChangeHelper.setBodyColor(Color.Wheat, animComp);
		PonyColorChangeHelper.setEyeColor(Color.Chocolate, animComp);
		PonyColorChangeHelper.setManeColor(Color.Chocolate, animComp);
		TextureDictionary dict = this.contentManager.load("rddict.tdict", TextureDictionary.class);
		player.setBoneTexture(Bones.EYE.getValue(), dict.extractTextureEntry("TSEYE"));
		this.pony.addComponent(animComp); 
<<<<<<< HEAD
<<<<<<< HEAD

=======
		
>>>>>>> Removed annoying debugsysos
=======

>>>>>>> Added a nonfunctional textfield.
		this.pony.refresh();
	}

	@Override
	public void handleInput(Mouse mouse, Keyboard keyboard) {
		this.panel.checkMouseInput(new Point2(0,0), mouse);
		this.panel.checkKeyboardInput(keyboard);
	}

	@Override
	public void render(GameTime time, SpriteBatch batch) {
		batch.begin();
		this.World.render();
		batch.end();
		this.panel.render(context, time);
	}

	@Override
	protected void addEntitySystems(IEntitySystemManager manager) {
		Rectangle vp = this.ScreenManager.getViewport();
		Rectangle worldBounds = new Rectangle(0, 0, vp.Width, vp.Height);

		this.animSys = new AnimationSystem(this.World, this.ScreenManager.getSpriteBatch(), 
				new Camera2D(worldBounds, vp));
		manager.addRenderEntitySystem(this.animSys);
		manager.addRenderEntitySystem(new RenderingSystem(this.World, this.ScreenManager.getSpriteBatch()));

	}

}
