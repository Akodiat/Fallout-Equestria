package entitySystems;

import math.Vector2;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import components.AttackComponent;
import components.HealthComponent;
import components.PositionComponent;
import components.SpatialComponent;

import entityFramework.ComponentMapper;
import entityFramework.Entity;
import entityFramework.EntityProcessingSystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

/**
 * 
 * @author Gustav
 * 
 */
public class AttackResolveSystem extends EntityProcessingSystem {

	protected AttackResolveSystem(IEntityWorld world,
			Class<? extends IComponent>[] componentsClasses) {
		super(world, componentsClasses);
	}

	private ComponentMapper<AttackComponent> aCM;
	private ComponentMapper<SpatialComponent> sCM;
	private ComponentMapper<PositionComponent> pCM;
	private ComponentMapper<HealthComponent> hCM;

	@Override
	public void initialize() {
		aCM = ComponentMapper.create(this.getWorld().getDatabase(),
				AttackComponent.class);
		pCM = ComponentMapper.create(this.getWorld().getDatabase(),
				PositionComponent.class);
		sCM = ComponentMapper.create(this.getWorld().getDatabase(),
				SpatialComponent.class);
		hCM = ComponentMapper.create(this.getWorld().getDatabase(),
				HealthComponent.class);
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {
/*
		ImmutableList<IEntity> gEntities = this.getWorld().getEntityManager().getEntityGroup();
		
		for (IEntity entity : entities) {
			AttackComponent attaCom = aCM.getComponent(entity);
			PositionComponent posiCom = pCM.getComponent(entity);
			
			for (IEntity targetEntity : attaCom.getTargets()){
				SpatialComponent targetSpatiCom = sCM.getComponent(targetEntity);
				PositionComponent targetPosiCom = pCM.getComponent(targetEntity);
				
				float distanceSquared = Vector2.distanceSquared(Vector2.add(posiCom.getPosition(), attaCom.getBounds().getPosition()),
																Vector2.add(targetPosiCom.getPosition(), targetSpatiCom.getBounds().getPosition()));
				float combinedRadiusSquared = (float)Math.pow(attaCom.getBounds().getRadius() + targetSpatiCom.getBounds().getRadius(),2);				
				Boolean didItHit = distanceSquared<combinedRadiusSquared;
				
				if(didItHit){
					HealthComponent healCom = hCM.getComponent(targetEntity);
					healCom.addHealthPoints(-attaCom.getDamage());
					System.out.println("Someone got hit for " + attaCom.getDamage() + " damage!");
				}
			}
			
		}*/

	}

}
