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
				boolean triggerCollision = s0.isTrigger() || s1.isTrigger();
				
				if(collisionResult) {
					if(alreadyColiding) {
						if(triggerCollision) {
							overTrigger(e0,e1);		
						} else {
							overCollision(e0,e1);
						}
					} else {
						addCollisionState(e0,e1);
						if(triggerCollision) {
							enterTrigger(e0,e1);
						} else {
							enterCollision(e0,e1);
						}
					}
				} else {
					if(alreadyColiding) {
						removeCollisionState(e0,e1);
						if(triggerCollision) {
							exitTrigger(e0,e1);
						} else {
							exitCollision(e0,e1);
						}
					}
				}
			}
		}	
						
	}
	
	private void enterTrigger(IEntity e0, IEntity e1) {
		BehaviourScript s0 = this.getScript(e0);
		if(s0 != null) {
			s0.onTriggerEnter(e1);
		}
	
		BehaviourScript s1 = this.getScript(e1);
		if(s1 != null) {
			s1.onTriggerEnter(e0);
		}
	}

	private void overTrigger(IEntity e0, IEntity e1) {
		BehaviourScript s0 = this.getScript(e0);
		if(s0 != null) {
			s0.onTriggerOver(e1);
		}
	
		BehaviourScript s1 = this.getScript(e1);
		if(s1 != null) {
			s1.onTriggerOver(e0);
		}
	}
	
	private void exitTrigger(IEntity e0, IEntity e1) {
		BehaviourScript s0 = this.getScript(e0);
		if(s0 != null) {
			s0.onTriggerExit(e1);
		}
	
		BehaviourScript s1 = this.getScript(e1);
		if(s1 != null) {
			s1.onTriggerExit(e0);
		}
	}
	

	private void enterCollision(IEntity e0, IEntity e1) {
		BehaviourScript s0 = this.getScript(e0);
		if(s0 != null) {
			s0.onCollisionEnter(e1);
		}
	
		BehaviourScript s1 = this.getScript(e1);
		if(s1 != null) {
			s1.onCollisionEnter(e0);
		}
	}
	
	private void overCollision(IEntity e0, IEntity e1) {
		BehaviourScript s0 = this.getScript(e0);
		if(s0 != null) {
			s0.onCollisionOver(e1);
		}
	
		BehaviourScript s1 = this.getScript(e1);
		if(s1 != null) {
			s1.onCollisionOver(e0);
		}			
	}
	
	private void exitCollision(IEntity e0, IEntity e1) {
		BehaviourScript s0 = this.getScript(e0);
		if(s0 != null) {
			s0.onCollisionExit(e1);
		}
	
		BehaviourScript s1 = this.getScript(e1);
		if(s1 != null) {
			s1.onCollisionExit(e0);
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

	private BehaviourScript getScript(IEntity e) {
		ScriptComp scriptComp = e.getComponent(ScriptComp.class);
		if(scriptComp != null) {
			return scriptComp.getScript();
		}
		return null;
	}
	
	private boolean testCollision(Vector2 p0, Circle c0,
								  Vector2 p1, Circle c1) {
		return Circle.intersects(c0, p0, c1, p1);		
		
	}

}
