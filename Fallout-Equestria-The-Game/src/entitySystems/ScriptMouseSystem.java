package entitySystems;

import java.util.HashSet;

import math.Vector2;

import org.lwjgl.input.Mouse;

import com.google.common.collect.ImmutableSet;

import components.ScriptComp;
import components.SpatialComp;
import components.TransformationComp;

import scripting.BehaviourScript;
import utils.ButtonState;
import utils.Camera2D;
import utils.Circle;
import utils.MouseState;
import utils.Rectangle;
import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class ScriptMouseSystem extends EntityProcessingSystem {

	private Camera2D camera;
	
	public ScriptMouseSystem(IEntityWorld world, Camera2D camera) {
		super(world, TransformationComp.class, SpatialComp.class, ScriptComp.class);
		this.camera = camera;
	}
	
	private ComponentMapper<TransformationComp> transCM;
	private ComponentMapper<SpatialComp> spatCM;
	private ComponentMapper<ScriptComp> scriptCM;

	private MouseState lastMouseState;

	@Override
	public void initialize() {
		transCM 	= ComponentMapper.create(this.getDatabase(), TransformationComp.class);
		spatCM 		= ComponentMapper.create(this.getDatabase(), SpatialComp.class);
		scriptCM 	= ComponentMapper.create(this.getDatabase(), ScriptComp.class);
		
		lastMouseState = new MouseState(Vector2.Zero, Vector2.Zero,Vector2.Zero, Vector2.Zero,
										0, ButtonState.Depressed, ButtonState.Depressed, ButtonState.Depressed);
	}

	
	protected void processEntity(IEntity entity, MouseState ms) {
		TransformationComp transC 	 =  transCM.getComponent(entity);
		SpatialComp spatC			 =  spatCM.getComponent(entity);
		ScriptComp  scriptC			 =  scriptCM.getComponent(entity);	
		
		
		if(Circle.intersects(spatC.getBounds(), transC.getPosition(), ms.WorldCoords)) {
			BehaviourScript script = scriptC.getScript();
			if(this.targetEntity == null) {
				this.targetEntity = entity;
				script.onMouseEnter(ms);
			} else {
				script.onMouseOver(ms);
			}
		}
				
	}
	
	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		MouseState ms = generateMouseState();	
		for (IEntity entity : entities) {	
			this.processEntity(entity, ms);
		}
		this.lastMouseState = ms;
	}


	private MouseState generateMouseState() {
		
		Rectangle viewport = camera.getViewport();
		
		Vector2 viewPos = new Vector2(Mouse.getX(), viewport.Height -Mouse.getY());
		Vector2 worldPos = camera.getViewToWorldCoords(viewPos);
		
		Vector2 viewDelta = Vector2.subtract(viewPos, lastMouseState.ViewCoords);
		Vector2 worldDelta = Vector2.subtract(worldPos, lastMouseState.WorldCoords);
		
		int scrollDeltha = Mouse.getDWheel();
		
		ButtonState leftButton = Mouse.isButtonDown(0) ? ButtonState.Pressed : ButtonState.Depressed;
		ButtonState rightButton = Mouse.isButtonDown(1) ? ButtonState.Pressed : ButtonState.Depressed;
		ButtonState middleButton = Mouse.isButtonDown(2) ? ButtonState.Pressed : ButtonState.Depressed;
		
		return new MouseState(worldPos,
					   		  viewPos, 
							  worldDelta,
							  viewDelta,
							  scrollDeltha, 
							  leftButton,
							  rightButton, 
							  middleButton);
	}

}
