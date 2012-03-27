package entitySystems;

import java.util.ArrayList;
import java.util.List;

import utils.Circle;

import com.google.common.collect.ImmutableSet;

import components.*;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

/**
 * 
 * @author Gustav
 */
public class AttackResolveSystem extends EntityProcessingSystem {

	public AttackResolveSystem(IEntityWorld world) {
		super(world, TransformationComp.class, AttackComponent.class);
	}

	private ComponentMapper<AttackComponent> aCM;
	private ComponentMapper<SpatialComponent> sCM;
	private ComponentMapper<TransformationComp> tCM;

	@Override
	public void initialize() {
		aCM = ComponentMapper.create(this.getWorld().getDatabase(),
				AttackComponent.class);
		tCM = ComponentMapper.create(this.getWorld().getDatabase(),
				TransformationComp.class);
		sCM = ComponentMapper.create(this.getWorld().getDatabase(),
				SpatialComponent.class);
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			AttackComponent attaCom = aCM.getComponent(entity);
			TransformationComp posiCom = tCM.getComponent(entity);

			List<IEntity> gEntities = new ArrayList<IEntity>();
			for (int i = 0; i < attaCom.getTargetGroups().size(); i++) {
				gEntities.addAll(this.getWorld().getEntityManager()
						.getEntityGroup(attaCom.getTargetGroups().get(i)));
			}

			boolean hitSomething = false;

			for (IEntity targetEntity : gEntities) {

				//TODO K�lla f�r nullpointers!
				SpatialComponent targetSpatiCom = sCM
						.getComponent(targetEntity);
				TransformationComp targetPosiCom = tCM
						.getComponent(targetEntity);
				//K�lla f�r nullpointers!!
				
				Boolean itGotHit = Circle.intersects(attaCom.getBounds(),
						posiCom.getPosition(), targetSpatiCom.getBounds(),
						targetPosiCom.getPosition());

				if (itGotHit) {
					hitSomething = true;
					HealthComponent healthComp = targetEntity.getComponent(HealthComponent.class);
					if(healthComp != null) {
						healthComp.addHealthPoints(-attaCom.getDamage());
					}
				}
			}

			if (hitSomething) {
				entity.kill();
			}
		}
	}
}
