package entitySystems;

import math.Vector2;

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

	private ComponentMapper<AttackComponent> ACM;
	private ComponentMapper<SpatialComponent> SCM;
	private ComponentMapper<PositionComponent> PCM;
	private ComponentMapper<HealthComponent> HCM;

	@Override
	public void initialize() {
		ACM = ComponentMapper.create(this.getWorld().getDatabase(),
				AttackComponent.class);
		PCM = ComponentMapper.create(this.getWorld().getDatabase(),
				PositionComponent.class);
		SCM = ComponentMapper.create(this.getWorld().getDatabase(),
				SpatialComponent.class);
		HCM = ComponentMapper.create(this.getWorld().getDatabase(),
				HealthComponent.class);
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {

		for (IEntity entity : entities) {
			AttackComponent attaCom = ACM.getComponent(entity);
			PositionComponent posiCom = PCM.getComponent(entity);
			
			for (IEntity targetEntity : attaCom.getTargets()){
				SpatialComponent targetSpatiCom = SCM.getComponent(targetEntity);
				PositionComponent targetPosiCom = PCM.getComponent(targetEntity);
				
				float distanceSquared = Vector2.distanceSquared(Vector2.add(posiCom.getPosition(), attaCom.getBounds().getPosition()),
																Vector2.add(targetPosiCom.getPosition(), targetSpatiCom.getBounds().getPosition()));
				float combinedRadiusSquared = (float)Math.pow(attaCom.getBounds().getRadius() + targetSpatiCom.getBounds().getRadius(),2);				
				Boolean didItHit = distanceSquared<combinedRadiusSquared;
				
				if(didItHit){
					HealthComponent healCom = HCM.getComponent(targetEntity);
					healCom.addHealthPoints(-attaCom.getDamage());
					System.out.println("Someone got hit for " + attaCom.getDamage() + " damage!");
				}
			}
			
		}

	}

}
