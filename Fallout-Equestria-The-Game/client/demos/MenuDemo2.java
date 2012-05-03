package demos;

import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.openal.Audio;

import components.BehaviourComp;
import components.RenderingComp;
import components.TransformationComp;

import math.Vector2;
import misc.IEventListener;
import content.ContentManager;
import GUI.MouseEventArgs;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityWorld;
import entitySystems.CameraControlSystem;
import entitySystems.RenderingSystem;
import entitySystems.ScriptMouseSystem;
import graphics.Color;
import graphics.SpriteBatch;
import graphics.Texture2D;
import scripting.RandomMoving;
import utils.Camera2D;
import utils.GameTime;
import utils.Mouse;
import utils.Rectangle;

public class MenuDemo2 extends Demo{

	private static Rectangle screenRect = new Rectangle(0,0,1360,768);
	private static final Audio clickSound = ContentManager.loadSound("effects" + File.separator + "open.ogg");
	private static final Audio overSound = ContentManager.loadSound("effects" + File.separator + "buttonclickeded.ogg");

	Camera2D camera;
	Mouse mouse;
	
	private IEntityWorld world;
	private SpriteBatch spriteBatch;

	public static void main(String[] args) {
		new MenuDemo2().start();
	}

	public MenuDemo2() {
		super(screenRect , 60);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void update(GameTime time) {
		this.world.update(time);
		this.world.getEntityManager().destoryKilledEntities();
	}

	@Override
	public void render(GameTime time) {
		this.spriteBatch.clearScreen(Color.Black);
		this.spriteBatch.begin(null, camera.getTransformation());
		this.world.render();
		this.spriteBatch.end();
	}


	@Override
	protected void initialize() {
		try {
			System.out.println(Display.getDisplayMode().isFullscreenCapable());
			Display.setVSyncEnabled(true);
			Display.setFullscreen(true);
		} catch (LWJGLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.spriteBatch = new SpriteBatch(screenRect);
		Texture2D backGround = ContentManager.loadTexture("foebackground.png");
		this.camera = new Camera2D(backGround.getBounds(), screenRect);
		this.mouse = new Mouse();
		
		camera.setPosition(new Vector2(2000, 223));
		world = WorldBuilder.buildGUIWorld(mouse, this.spriteBatch);
		world.getSystemManager().addLogicEntitySystem(new ScriptMouseSystem(world, camera));
		world.getSystemManager().addRenderEntitySystem(new CameraControlSystem(world, camera));
		world.getSystemManager().addRenderEntitySystem(new RenderingSystem(world, spriteBatch));
		world.initialize();

		IEntityArchetype archetype = ContentManager.loadArchetype("FalloutButton.archetype");

		int buttonX = screenRect.Width - 250;

		//WALLPAPER
		IEntity wallpaper = this.world.getEntityManager().createEmptyEntity();
		TransformationComp wallpaperTC = new TransformationComp();
		wallpaper.addComponent(wallpaperTC);

		RenderingComp rC = new RenderingComp();
		rC.setTexture(backGround);
		wallpaper.addComponent(rC);

		wallpaper.refresh();

		//CAMERATARGET
		IEntity cameraTarget = this.world.getEntityManager().createEmptyEntity();
		//TRANSFORMATIONCOMP
		TransformationComp tC = new TransformationComp();
		cameraTarget.addComponent(tC);
		//BEHAVIOURCOMP
		BehaviourComp bC = new BehaviourComp();
		Rectangle cameraBounds = new Rectangle(camera.getVisibleArea().Width/2,
											   camera.getVisibleArea().Height/2,
											   camera.getWorldBounds().Width - camera.getVisibleArea().Width,
											   camera.getWorldBounds().Height  - camera.getVisibleArea().Height);
		RandomMoving rM = new RandomMoving(cameraBounds);
		bC.setBehavior(rM);
		cameraTarget.addComponent(bC);
		cameraTarget.addToGroup(CameraControlSystem.GROUP_NAME);
		cameraTarget.refresh();
	}
}
