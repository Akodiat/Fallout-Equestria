package GUI;

import java.util.HashMap;
import java.util.Map;

import math.Vector2;


import utils.ButtonState;
import utils.Mouse;
import utils.MouseButton;
import utils.MouseState;
import utils.Rectangle;

import com.google.common.collect.ImmutableSet;

import components.BehaviourComp;
import components.GUIComp;

import entityFramework.EntityProcessingSystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class GUISystem extends EntityProcessingSystem {
	
	public GUISystem(IEntityWorld world, Mouse mouse) {
		super(world, GUIComp.class, BehaviourComp.class);
		guiStates = new HashMap<>();
		this.mouse = mouse;
	}

	private Mouse mouse;
	private IEntity focusTarget;
	private Map<IEntity, GUIState> guiStates;
	
	@Override
	public void entityAdded(IEntity entity) {
		this.guiStates.put(entity, new GUIStateNone());
	}
	
	@Override
	public void entityRemoved(IEntity entity) {
		this.guiStates.remove(entity);
	}
	
	@Override
	public void entityDestroyed(IEntity entity) {
		this.guiStates.remove(entity);
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		MouseState ms = this.mouse.getMouseState();
		for (IEntity entity : entities) {
			GUIComp component = entity.getComponent(GUIComp.class);
			GUIState state = this.guiStates.get(entity);
			
			
			boolean collision = component.getPosition().intersects(ms.ViewCoords);
			
			
			state.Update(collision, entity, ms);
		}
		
	}
	

	private void changeState(GUIState state, IEntity entity) {
		System.out.println(state.getClass().getSimpleName());
		this.guiStates.put(entity, state);
	}

	
	
	public void checkNonLeftButtonEvents(BehaviourComp comp, MouseState state) {
		if(mouse.wasButtonPressed(MouseButton.Right)) {
			comp.onMouseDown(state, MouseButton.Right);
	
		} else if(mouse.wasButtonReleased(MouseButton.Right)) {
			comp.onMouseUp(state, MouseButton.Right);
			comp.onMouseUpAsButton(state, MouseButton.Right);
		}
		
		if(mouse.wasButtonPressed(MouseButton.Middle)) {
			comp.onMouseDown(state, MouseButton.Middle);
		} else if(mouse.wasButtonReleased(MouseButton.Middle)) {
			
			comp.onMouseUp(state, MouseButton.Middle);
			comp.onMouseUpAsButton(state, MouseButton.Middle);
		}
		
	}
	
	private abstract class GUIState {
		public abstract void Update(boolean collision, IEntity entity, MouseState state);
	}
	
	private class GUIStateNone extends GUIState{

		@Override
		public void Update(boolean collision, IEntity entity, MouseState state) {
			if(collision) {
				BehaviourComp comp = entity.getComponent(BehaviourComp.class);
				comp.onMouseEnter(state);
				changeState(new GUIStateOver(), entity);
			}
		}
	}
	
	private class GUIStateOver extends GUIState {

		@Override
		public void Update(boolean collision, IEntity entity, MouseState state) {
			BehaviourComp comp = entity.getComponent(BehaviourComp.class);
			
			if(!collision) {
				comp.onMouseExit(state);
				changeState(new GUIStateNone(), entity);
			} else {
				if(mouse.wasButtonPressed(MouseButton.Left)) {
					comp.onMouseDown(state, MouseButton.Left);
					changeState(new GUIStateDragOver(), entity);
				}
				
				checkNonLeftButtonEvents(comp, state);
			}
		}
	}
	
	private class GUIStateDragOver extends GUIState {
		
		@Override
		public void Update(boolean collision, IEntity entity, MouseState state) {
			BehaviourComp comp = entity.getComponent(BehaviourComp.class);
			if(!collision) {
				comp.onMouseExit(state);
				changeState(new GUIStateDrag(), entity);
			} else {
				if(mouse.wasButtonReleased(MouseButton.Left)) {
					changeState(new GUIStateOver(), entity);
					comp.onMouseUp(state, MouseButton.Left);
					comp.onMouseUpAsButton(state, MouseButton.Left);
				}
				
				checkNonLeftButtonEvents(comp, state);
			}
		}
		
	}
	public class GUIStateDrag extends GUIState {
		@Override
		public void Update(boolean collision, IEntity entity, MouseState state) {
			BehaviourComp comp = entity.getComponent(BehaviourComp.class);
			if(collision) {
				comp.onMouseEnter(state);
				changeState(new GUIStateDragOver(), entity);
			} else {
				if(mouse.wasButtonReleased(MouseButton.Left)) {
					changeState(new GUIStateNone(), entity);
					comp.onMouseUp(state, MouseButton.Left);
				}
			}
		}
		
	}
}
