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

public class ScriptCollisionSystem extends  EntityProcessingSystem{

	
	
	public ScriptCollisionSystem(IEntityWorld world) {
		super(world, TransformationComp.class, SpatialComp.class);
		// TODO Auto-generated constructor stub
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
				if(collisionResult) {
					notifyScript(e0, e1);					
				}
				
			}
		}	
	}

	private void notifyScript(IEntity e0, IEntity e1) {
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

	private boolean testCollision(Vector2 p0, Circle c0,
								  Vector2 p1, Circle c1) {
		return Circle.intersects(c0, p0, c1, p1);		
		
	}

}
