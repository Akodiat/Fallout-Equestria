package entitySystems;

import java.util.ArrayList;
import java.util.List;

import math.Vector2;
import scripting.BehaviourScript;
import utils.Circle;

import com.google.common.collect.ImmutableSet;

import components.PhysicsComp;
import components.ScriptComp;
import components.SpatialComp;
import components.TransformationComp;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import java.util.*;

public class ScriptCollisionSystem extends  EntityProcessingSystem{

	public ScriptCollisionSystem(IEntityWorld world) {
		super(world, TransformationComp.class, SpatialComp.class);
		// TODO Auto-generated constructor stub
		this.entityCollisionStates = new HashMap<>();		
	}
	
	private Map<IEntity, Set<IEntity>> entityCollisionStates;

	
	@Override
	protected void entityAdded(IEntity entity) {
		this.entityCollisionStates.put(entity, new HashSet<IEntity>());		
	}
	
	@Override
	protected void entityRemoved(IEntity entity) {
		for (IEntity e : entityCollisionStates.get(entity)) {
			exitCollision(e, entity);	
		}		
		this.entityCollisionStates.remove(entity);	
	}

	private ComponentMapper<TransformationComp> tCM;
	private ComponentMapper<SpatialComp> sCM;

	@Override
	public void initialize() {
		tCM = ComponentMapper.create(this.getDatabase(), TransformationComp.class);
		sCM = ComponentMapper.create(this.getDatabase(), SpatialComp.class);
		
	}

	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		List<IEntity> indexedEntities = new ArrayList<>(entities);
		for (int i = 0; i < indexedEntities.size() - 1; i++) {
			IEntity e0 = indexedEntities.get(i);
			TransformationComp t0 = tCM.getComponent(e0);
			SpatialComp s0 = sCM.getComponent(e0);
			
			for (int j = i + 1; j < indexedEntities.size(); j++) {				
				IEntity e1 = indexedEntities.get(j);
				TransformationComp t1= tCM.getComponent(e1);
				SpatialComp s1 = sCM.getComponent(e1);
						
				boolean collisionResult = testCollision(t0.getPosition(), s0.getBounds(),
														t1.getPosition(), s1.getBounds());
				boolean alreadyColiding = getCollisionStatus(e0,e1);
				
				if(collisionResult) {
					if(alreadyColiding) {
						overCollision(e0,e1);
					} else {
						addCollisionState(e0,e1);
						enterCollision(e0,e1);
					}
				} else {
					if(alreadyColiding) {
						removeCollisionState(e0,e1);
						exitCollision(e0,e1);
					}
				}
			}
		}	
						
	}
	
	private void addCollisionState(IEntity e0, IEntity e1) {
		this.entityCollisionStates.get(e0).add(e1);
		this.entityCollisionStates.get(e1).add(e0);
	}

	private void removeCollisionState(IEntity e0, IEntity e1) {
		this.entityCollisionStates.get(e0).remove(e1);
		this.entityCollisionStates.get(e1).remove(e0);
	}

	private boolean getCollisionStatus(IEntity e0, IEntity e1) {
		Set<IEntity> e0Cols = this.entityCollisionStates.get(e0);
		if(e0Cols.contains(e1)) {
			return true;
		} else {
			return false;
		}
				
	}

	private void enterCollision(IEntity e0, IEntity e1) {
		ScriptComp scriptComp0 = e0.getComponent(ScriptComp.class);
		if(scriptComp0 != null) {
			BehaviourScript script = scriptComp0.getScript();
			script.onCollisionEnter(e1);
		}
		
		ScriptComp scriptComp1 = e1.getComponent(ScriptComp.class);
		if(scriptComp1 != null) {
			BehaviourScript script = scriptComp1.getScript();
			script.onCollisionEnter(e0);
		}	
	}
	
	private void overCollision(IEntity e0, IEntity e1) {
		ScriptComp scriptComp0 = e0.getComponent(ScriptComp.class);
		if(scriptComp0 != null) {
			BehaviourScript script = scriptComp0.getScript();
			script.onCollisionOver(e1);
		}
		
		ScriptComp scriptComp1 = e1.getComponent(ScriptComp.class);
		if(scriptComp1 != null) {
			BehaviourScript script = scriptComp1.getScript();
			script.onCollisionOver(e0);
		}			
	}
	
	private void exitCollision(IEntity e0, IEntity e1) {
		ScriptComp scriptComp0 = e0.getComponent(ScriptComp.class);
		if(scriptComp0 != null) {
			BehaviourScript script = scriptComp0.getScript();
			script.onCollisionExit(e1);
		}
		
		ScriptComp scriptComp1 = e1.getComponent(ScriptComp.class);
		if(scriptComp1 != null) {
			BehaviourScript script = scriptComp1.getScript();
			script.onCollisionExit(e0);
		}
	}

	private boolean testCollision(Vector2 p0, Circle c0,
								  Vector2 p1, Circle c1) {
		return Circle.intersects(c0, p0, c1, p1);		
		
	}

}
