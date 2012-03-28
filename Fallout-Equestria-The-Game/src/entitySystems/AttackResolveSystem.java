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
		super(world, TransformationComp.class, AttackComp.class);
	}

	private ComponentMapper<AttackComp> aCM;
	private ComponentMapper<SpatialComp> sCM;
	private ComponentMapper<TransformationComp> tCM;

	@Override
	public void initialize() {
		aCM = ComponentMapper.create(this.getWorld().getDatabase(),
				AttackComp.class);
		tCM = ComponentMapper.create(this.getWorld().getDatabase(),
				TransformationComp.class);
		sCM = ComponentMapper.create(this.getWorld().getDatabase(),
				SpatialComp.class);
	}

	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			AttackComp attaCom = aCM.getComponent(entity);
			TransformationComp posiCom = tCM.getComponent(entity);

			@SuppressWarnings("unchecked")
			ImmutableSet<IEntity> targets = this
					.getDatabase()
					.getEntitysContainingComponents(TransformationComp.class,
							SpatialComp.class);

			boolean hitSomething = false;
			for (IEntity targetEntity : targets) {
				if (targetEntity != attaCom.getSourceEntity()
						&& targetEntity != entity) {
					SpatialComp targetSpatiCom = sCM
							.getComponent(targetEntity);
					TransformationComp targetPosiCom = tCM
							.getComponent(targetEntity);
					Boolean itGotHit = checkForCollision(attaCom, posiCom,
							targetEntity, targetSpatiCom, targetPosiCom);

					if (itGotHit) {
						hitSomething = true;
						HealthComp healthComp = targetEntity
								.getComponent(HealthComp.class);
						if (healthComp != null) {
							healthComp.addHealthPoints(-attaCom.getDamage());
							attaCom.addTargetHit(targetEntity);

						}
					}
				}

				if (hitSomething && attaCom.isDestoryOnHit()) {
					entity.kill();
				}
			}
		}
	}

	private boolean checkForCollision(AttackComp attaCom,
			TransformationComp posiCom, IEntity targetEntity,
			SpatialComp targetSpatiCom, TransformationComp targetPosiCom) {

		return Circle.intersects(attaCom.getBounds(), posiCom.getPosition(),
				targetSpatiCom.getBounds(), targetPosiCom.getPosition())
				&& !attaCom.getTargetsHit().contains(targetEntity);
	}
}
