package demos;

import math.Point2;
import GUI.Button;
import GUI.ButtonRenderer;
import GUI.GUIControl;
import GUI.Panel;
import GUI.GUIPanelRenderer;
import GUI.GUIRenderingContext;
import GUI.Label;
import GUI.LabelRenderer;

import com.google.inject.Guice;
import components.GUIComp;

import content.ContentManager;

import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entityFramework.IEntityWorld;
import entitySystems.GUIRenderingSystem;
import entitySystems.ScriptMouseSystem;
import entitySystems.ScriptSystem;
import gameMap.Scene;
import graphics.Color;
import graphics.SpriteBatch;
import utils.Camera2D;
import utils.GameTime;
import utils.Mouse;
import utils.Rectangle;

public class GUIDemo extends Demo {
	private static final String playerAsset = "Player.archetype";
	private static final String aiAsset 	= "FollowingTextAI.archetype";
	private static Rectangle screenDim 		= new Rectangle(0,0,1366,768);

	private IEntityWorld gameWorld;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Mouse mouse;
	
	private GUIRenderingContext context;
	Panel panel;
	
	public static void main(String[] args) {
		new GUIDemo().start();
	}
	
	public GUIDemo() {
		super(screenDim, 60);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(GameTime time) {
		this.mouse.poll(camera);
		panel.checkMouseInput(new Point2(0,0), mouse);
	}

	@Override
	public void render(GameTime time) {
		this.spriteBatch.clearScreen(Color.Black);
		panel.render(context, time);
	}

	@Override
	protected void initialize() {
		this.camera = new Camera2D(screenDim, screenDim);
		this.spriteBatch = new SpriteBatch(screenDim);
		this.mouse = new Mouse();
		context = new GUIRenderingContext(spriteBatch);
		context.addRenderer(new LabelRenderer());
		context.addRenderer(new GUIPanelRenderer());
		context.addRenderer(new ButtonRenderer());
		
		Label root = new Label();
		root.setBounds(new Rectangle(0,0,200,80));
		root.setFgColor(Color.Black);
		root.setText("This is a \nmultiline label");
		root.setFont(ContentManager.loadFont("arialb20.xml"));
		
		Label node1 = new Label();
		node1.setBounds(new Rectangle(0,70,150,30));
		node1.setText("Label2 1 Level deep");
		node1.setFont(ContentManager.loadFont("arialb20.xml"));
		node1.setFgColor(Color.Gold);	
		
		panel = new Panel();
		panel.setBounds(new Rectangle(200,200,500,500));
		panel.addChild(root);
		panel.addChild(node1);
		
		Panel panel1 = new Panel();
		panel1.setBounds(new Rectangle(200,150,100,100));
		panel1.setBgColor(new Color(Color.NavyBlue, 0.2f));
		panel.addChild(panel1);
		
		Label node2 = new Label();
		node2.setBounds(new Rectangle(0,0,500,30));
		node2.setFgColor(Color.Orange);
		node2.setText("Label3 2 Levels deep");
		node2.setFont(ContentManager.loadFont("arialb20.xml"));
		panel1.addChild(node2);
		
		Button button = new Button();
		button.setBounds(new Rectangle(150,50,500,35));
		button.setImage(ContentManager.loadTexture("GUI/guiBackground.png"));
		button.setOverTexture(ContentManager.loadTexture("GUI/overButton.png"));
		button.setText("Hej i am stronk!");
		button.setFont(ContentManager.loadFont("arialb20.xml"));
		panel.addChild(button);
	}

}
