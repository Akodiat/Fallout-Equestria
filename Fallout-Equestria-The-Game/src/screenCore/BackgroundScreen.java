package screenCore;

import components.BehaviourComp;
import components.TransformationComp;

import math.Vector2;
import scripting.RandomMoving;
import utils.Camera2D;
import utils.GameTime;
import utils.Keyboard;
import utils.Mouse;
import utils.Rectangle;
import utils.TimeSpan;
import content.ContentManager;
import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entitySystems.CameraControlSystem;
import entitySystems.ScriptSystem;
import graphics.Color;
import graphics.SpriteBatch;
import graphics.Texture2D;

public class BackgroundScreen extends EntityScreen {
	private Camera2D camera;
	private final String backgroundTextureAssetPath;
	private Texture2D backgroundTexture;
	
	public BackgroundScreen(boolean popup, TimeSpan transOnTime,
			TimeSpan transOffTime, String backgroundPath) {
		super(popup, transOnTime, transOffTime);
		this.backgroundTextureAssetPath = backgroundPath;
	}
	

	@Override
	protected void loadContent(ContentManager contentManager) {
		this.backgroundTexture = contentManager.loadTexture(backgroundTextureAssetPath);
		this.camera = new Camera2D(this.backgroundTexture.getBounds(), 
								   this.ScreenManager.getSpriteBatch().getViewport());
	}


	@Override
	protected void addEntities(IEntityManager entityManager) {	
		//CAMERATARGET
		IEntity cameraTarget = entityManager.createEmptyEntity();
		
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
	
	@Override
	protected void addRenderingSystem(IEntitySystemManager systemManager) {
		//No rendering systems are needed here. Since the rendering is so basic.
	}

	@Override
	protected void addLogicSystem(IEntitySystemManager systemManager) {
		systemManager.addLogicEntitySystem(new CameraControlSystem(this.World, this.camera));
		systemManager.addLogicEntitySystem(new ScriptSystem(this.World, this.ScreenManager.getContentManager()));
	}

	@Override
	public void handleInput(Mouse mouse, Keyboard keyboard) {
		//This screen does not take input!
	}
	
	public void update(GameTime time, boolean otherScreeenHasFocus,
			boolean coveredByOtherScreen) {
		super.update(time, otherScreeenHasFocus, false);
	}

	@Override
	public void render(GameTime time, SpriteBatch spriteBatch) {	
		super.render(time, spriteBatch);
		float alpha = 1.0f - this.getTransitionPosition();
		
		spriteBatch.begin(null, camera.getTransformation());
		spriteBatch.draw(this.backgroundTexture, Vector2.Zero, new Color(Color.White, alpha));		
		spriteBatch.end();
	}

}
