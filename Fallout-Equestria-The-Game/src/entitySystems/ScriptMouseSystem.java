package entitySystems;

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

	
	

	@Override
	public void initialize() {
		transCM 	= ComponentMapper.create(this.getDatabase(), TransformationComp.class);
		spatCM 		= ComponentMapper.create(this.getDatabase(), SpatialComp.class);
		scriptCM 	= ComponentMapper.create(this.getDatabase(), ScriptComp.class);
		
	}

	
	protected void processEntity(IEntity entity, MouseState ms) {
		TransformationComp transC 	 =  transCM.getComponent(entity);
		SpatialComp spatC			 =  spatCM.getComponent(entity);
		ScriptComp  scriptC			 =  scriptCM.getComponent(entity);	
		
		
		if(Circle.intersects(spatC.getBounds(), transC.getPosition(), ms.WorldCoords)) {
			BehaviourScript script = scriptC.getScript();
			script.onMouseOver(ms);
		}
				
	}
	
	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		MouseState ms = generateMouseState();	
		for (IEntity entity : entities) {	
			this.processEntity(entity, ms);
		}
	}


	private MouseState generateMouseState() {
		
		Rectangle viewport = camera.getViewport();
		
		Vector2 viewPos = new Vector2(Mouse.getX(), viewport.Height -Mouse.getY());
		Vector2 delta  = new Vector2(Mouse.getDX(), -Mouse.getDY());
		Vector2 worldPos = camera.getViewToWorldCoords(viewPos);
		int scrollDeltha = Mouse.getDWheel();
		
		ButtonState leftButton = Mouse.isButtonDown(0) ? ButtonState.Pressed : ButtonState.Depressed;
		ButtonState rightButton = Mouse.isButtonDown(1) ? ButtonState.Pressed : ButtonState.Depressed;
		ButtonState middleButton = Mouse.isButtonDown(2) ? ButtonState.Pressed : ButtonState.Depressed;
		
		return new MouseState(worldPos,
					   		  viewPos, 
							  delta, 
							  scrollDeltha, 
							  leftButton,
							  rightButton, 
							  middleButton);
	}

}
