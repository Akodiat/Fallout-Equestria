package entitySystems;

import math.Vector2;
import utils.Circle;

import com.google.common.collect.ImmutableSet;

import components.PhysicsComp;
import components.SpatialComp;
import components.TransformationComp;

import entityFramework.*;

//TODO Add comments. 
public class CollisionSystem extends EntitySingleProcessingSystem{

	public CollisionSystem(IEntityWorld world) {
		super(world, PhysicsComp.class, TransformationComp.class, SpatialComp.class);
	}

	private ComponentMapper<TransformationComp> transCM;
	private ComponentMapper<PhysicsComp> physCM;
	private ComponentMapper<SpatialComp> spatCM;

	@Override
	public void initialize() {
		transCM = ComponentMapper.create(this.getWorld().getDatabase(), TransformationComp.class);
		physCM = ComponentMapper.create(this.getWorld().getDatabase(), PhysicsComp.class);
		spatCM = ComponentMapper.create(this.getWorld().getDatabase(), SpatialComp.class);

	}

	@Override
	protected void processEntity(IEntity entity) {
		TransformationComp transComp = transCM.getComponent(entity);
		PhysicsComp physComp = physCM.getComponent(entity);
		SpatialComp spatComp = spatCM.getComponent(entity);

		ImmutableSet<IEntity> collidableEntities = this.getWorld().getDatabase().getEntitysContainingComponent(SpatialComp.class);
		
		for(IEntity i:collidableEntities){						

			//Checking collision with other entities
			SpatialComp otherSpatComp = i.getComponent(SpatialComp.class);
			TransformationComp otherTransComp = i.getComponent(TransformationComp.class);
			PhysicsComp otherPhysComp = i.getComponent(PhysicsComp.class);
			
			if(!i.equals(entity) && Circle.intersects(
					otherSpatComp.getBounds(),
					otherTransComp.getPosition(),
					spatComp.getBounds(),
					transComp.getPosition())){
				collide(transComp, physComp, spatComp, otherSpatComp,
						otherTransComp, otherPhysComp);
			}
		}

	}

	private void collide(TransformationComp transComp,
			PhysicsComp physComp, SpatialComp spatComp,
			SpatialComp otherSpatComp, TransformationComp otherTransComp,
			PhysicsComp otherPhysComp) {
		Vector2 Dn = Vector2.subtract((transComp.getPosition()),otherTransComp.getPosition());
		float distance = Dn.length();
		
		if(distance == 0) {
			return;
		}
		
		Dn = Vector2.norm(Dn);

		Vector2 Dt = new Vector2(Dn.Y, -Dn.X);

		float m1 = physComp.getMass();
		float m2 = otherPhysComp.getMass();
		float M = m1 + m2;
		Vector2 mT = Vector2.mul((spatComp.getBounds().getRadius() + otherSpatComp.getBounds().getRadius() - distance), Dn);

		transComp.setPosition(Vector2.add(transComp.getPosition(), Vector2.mul(m2/M, mT)));
		otherTransComp.setPosition(Vector2.subtract(otherTransComp.getPosition(), Vector2.mul(m1/M, mT)));

		Vector2 v1 = physComp.getVelocity();
		Vector2 v2 = otherPhysComp.getVelocity();

		Vector2 v1n = Vector2.mul(Vector2.dot(v1, Dn), Dn);
		Vector2 v1t = Vector2.mul(Vector2.dot(v1, Dt), Dt);

		Vector2 v2n = Vector2.mul(Vector2.dot(v2, Dt), Dn);
		Vector2 v2t = Vector2.mul(Vector2.dot(v2, Dt), Dt);

		physComp.setVelocity(Vector2.mul(((m1 - m2) / M * v1n.length() + 2 * m2 / M * v2n.length()), Vector2.add(v1t, Dn)));
		otherPhysComp.setVelocity(Vector2.mul(((m2 - m1) / M * v2n.length() + 2 * m1 / M * v1n.length()), Vector2.subtract(v2t, Dn)));
	}

}
