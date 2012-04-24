package entitySystems;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import math.Vector2;

import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

import com.google.common.collect.ImmutableSet;

import components.BehaviourComp;
import components.SpatialComp;
import components.TransformationComp;

import scripting.Behaviour;
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
		super(world, TransformationComp.class, SpatialComp.class, BehaviourComp.class);
		this.camera = camera;
		
	}
	
	private ComponentMapper<TransformationComp> transCM;
	private ComponentMapper<SpatialComp> spatCM;
	private ComponentMapper<BehaviourComp> scriptCM;

	private MouseState lastMouseState;
	
	
	private enum CollisionState {
		None,
		Over,
		MouseDrag,
		MouseDownOver
	}
	
	Map<IEntity, CollisionState> collisionStates = new HashMap<>();
	
	@Override
	public void entityAdded(IEntity entity) {
		collisionStates.put(entity, CollisionState.None);
	}
	
	@Override
	public void entityRemoved(IEntity entity) {
		CollisionState state = collisionStates.remove(entity);
		if(state == CollisionState.Over) {
			Behaviour behaviour = this.scriptCM.getComponent(entity).getBehaviour();
			behaviour.onMouseExit(this.lastMouseState);
		}
	}
	
	

	@Override
	public void initialize() {
		transCM 	= ComponentMapper.create(this.getDatabase(), TransformationComp.class);
		spatCM 		= ComponentMapper.create(this.getDatabase(), SpatialComp.class);
		scriptCM 	= ComponentMapper.create(this.getDatabase(), BehaviourComp.class);
		
		lastMouseState = new MouseState(Vector2.Zero, Vector2.Zero,Vector2.Zero, Vector2.Zero,
										0, ButtonState.Depressed, ButtonState.Depressed, ButtonState.Depressed);
	}
	
	protected void processEntity(IEntity entity, MouseState ms) {
		TransformationComp transC 	 =  transCM.getComponent(entity);
		SpatialComp spatC			 =  spatCM.getComponent(entity);
		BehaviourComp  scriptC			 =  scriptCM.getComponent(entity);	
		CollisionState state = this.collisionStates.get(entity);
		Behaviour behaviour = scriptC.getBehaviour();
		
		boolean collision = Circle.intersects(spatC.getBounds(), transC.getPosition(), ms.WorldCoords);
		
		if(collision) {
			fixCollisionMouseBehaviour(entity, ms, state, behaviour);	
		} else {
			fixNonCollisionBehaviour(entity, ms, state, behaviour);	
		}	
	}

	private void fixCollisionMouseBehaviour(IEntity entity, MouseState ms,
			CollisionState state, Behaviour behaviour) {
		if(state == CollisionState.None) {
			behaviour.onMouseEnter(ms);
			this.setState(entity, CollisionState.Over);
		} else if(state == CollisionState.Over || 
				 state == CollisionState.MouseDrag){
			behaviour.onMouseOver(ms);
		}
		if(state == CollisionState.MouseDrag) {
			behaviour.onMouseDrag(ms);
		}
		
		if(mouseGotPressed(ms)) {
			behaviour.onMouseDown(ms);
			this.setState(entity, CollisionState.MouseDrag);
		} else if(mouseGotReleased(ms)){
			if(state == CollisionState.MouseDrag) {
				behaviour.onMouseUpAsButton(ms);
				behaviour.onMouseUp(ms);
				this.setState(entity, CollisionState.Over);
			} else {
				behaviour.onMouseUp(ms);
			}
		}
	}

	private void fixNonCollisionBehaviour(IEntity entity, MouseState ms,
			CollisionState state, Behaviour behaviour) {
		if(state == CollisionState.Over) {
			behaviour.onMouseExit(ms);
			this.setState(entity, CollisionState.None);
		}
		if(state == CollisionState.MouseDrag) {
			behaviour.onMouseDrag(ms);
			if(mouseGotReleased(ms)) {
				behaviour.onMouseUp(ms);
				this.setState(entity, CollisionState.None);
			}
		}
	}

	private boolean mouseGotReleased(MouseState ms) {
		boolean buttonWasPressed = lastMouseState.LeftButtonState == ButtonState.Pressed &&
				   ms.LeftButtonState == ButtonState.Depressed;

		buttonWasPressed = buttonWasPressed || 
			(lastMouseState.RightButtonState == ButtonState.Pressed &&
					   ms.RightButtonState == ButtonState.Depressed);
		
		buttonWasPressed = buttonWasPressed || 
		(lastMouseState.MiddleButtonState == ButtonState.Pressed &&
		ms.MiddleButtonState == ButtonState.Depressed);

		return buttonWasPressed;
	}

	private void setState(IEntity entity, CollisionState state) {
		this.collisionStates.put(entity, state);
		
	}

	private boolean mouseGotPressed(MouseState ms) {
		boolean buttonWasPressed = lastMouseState.LeftButtonState == ButtonState.Depressed &&
											   ms.LeftButtonState == ButtonState.Pressed;
		
		buttonWasPressed = buttonWasPressed || 
								(lastMouseState.RightButtonState == ButtonState.Depressed &&
										   ms.RightButtonState == ButtonState.Pressed);
		
		buttonWasPressed = buttonWasPressed || 
							(lastMouseState.MiddleButtonState == ButtonState.Depressed &&
							ms.MiddleButtonState == ButtonState.Pressed);
		
		return buttonWasPressed;
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
