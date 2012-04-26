package demos;

import org.newdawn.slick.openal.Audio;

import misc.IEventListener;
import content.ContentManager;
import GUI.ButtonEventArgs;
import GUI.GUIFactory;
import GUI.MouseEventArgs;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityWorld;
import graphics.Color;
import graphics.SpriteBatch;
import utils.Camera2D;
import utils.GameTime;
import utils.Mouse;
import utils.Rectangle;

public class MenuDemo extends Demo{
	
	private static Rectangle screenRect = new Rectangle(0,0,1366,768);
	
	private IEntityWorld world;
	private SpriteBatch spriteBatch;
	private Camera2D camera;
	private Mouse mouse;
	
	public static void main(String[] args) {
		new MenuDemo().start();
	}
	
	public MenuDemo() {
		super(screenRect , 60);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void update(GameTime time) {
		this.mouse.poll(camera);
		this.world.update(time);
		this.world.getEntityManager().destoryKilledEntities();
	}

	@Override
	public void render(GameTime time) {
		this.spriteBatch.clearScreen(Color.Black);
		this.spriteBatch.begin();
		this.world.render();
		this.spriteBatch.end();
	}


	@Override
	protected void initialize() {
		this.spriteBatch = new SpriteBatch(screenRect);
		this.mouse = new Mouse();
		this.camera = new Camera2D(screenRect, screenRect);
		world = WorldBuilder.buildGUIWorld(mouse, this.spriteBatch);
		world.initialize();
		
		
		GUIFactory factory = new GUIFactory(world.getEntityManager());
		
		IEntityArchetype archetype = ContentManager.loadArchetype("FalloutButton.archetype");
		
		int buttonX = screenRect.Width - 250;
		
		factory.createButton(new Rectangle(buttonX,100,200,40), archetype, "SinglePlayer", new IEventListener<ButtonEventArgs>() {
			
			@Override
			public void onEvent(Object sender, ButtonEventArgs e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		factory.createGUI(new Rectangle(buttonX,150,200,40), archetype, "MultiPlayer", new IEventListener<MouseEventArgs>() {	
			@Override
			public void onEvent(Object sender, MouseEventArgs e) {
			}
		});
		
		factory.createGUI(new Rectangle(buttonX,200,200,40), archetype, "Exit", new IEventListener<MouseEventArgs>() {
			
			@Override
			public void onEvent(Object sender, MouseEventArgs e) {
			}
		});
		
		factory.createGUI(new Rectangle(100,100,500,40), ContentManager.loadArchetype("StandardTextbox.archetype"));
		
		factory.createLabel(new Rectangle(400,300, 140, 40), ContentManager.loadArchetype("StandardLabel.archetype"), "This a label!");
		
		
	}
}
