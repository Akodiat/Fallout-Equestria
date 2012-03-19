package entitySystems;

import java.util.ArrayList;
import java.util.List;

import utils.Circle;

import com.google.common.collect.ImmutableSet;

import components.AttackComponent;
import components.PositionComponent;
import components.SpatialComponent;
import components.StatusChangeComponent;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

/**
 * 
 * @author Gustav
 */
public class AttackResolveSystem extends EntityProcessingSystem {

	@SuppressWarnings("unchecked")
	public AttackResolveSystem(IEntityWorld world) {
		super(world, PositionComponent.class, AttackComponent.class);
	}

	private ComponentMapper<AttackComponent> aCM;
	private ComponentMapper<SpatialComponent> sCM;
	private ComponentMapper<PositionComponent> pCM;

	@Override
	public void initialize() {
		aCM = ComponentMapper.create(this.getWorld().getDatabase(),
				AttackComponent.class);
		pCM = ComponentMapper.create(this.getWorld().getDatabase(),
				PositionComponent.class);
		sCM = ComponentMapper.create(this.getWorld().getDatabase(),
				SpatialComponent.class);
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			AttackComponent attaCom = aCM.getComponent(entity);
			PositionComponent posiCom = pCM.getComponent(entity);

			List<IEntity> gEntities = new ArrayList<IEntity>();
			for (int i = 0; i < attaCom.getTargetGroups().size(); i++) {
				gEntities.addAll(this.getWorld().getEntityManager()
						.getEntityGroup(attaCom.getTargetGroups().get(i)));
			}

			for (IEntity targetEntity : gEntities) {
				SpatialComponent targetSpatiCom = sCM
						.getComponent(targetEntity);
				PositionComponent targetPosiCom = pCM
						.getComponent(targetEntity);

				Boolean itGotHit = Circle.intersects(attaCom.getBounds(),
						posiCom.getPosition(), targetSpatiCom.getBounds(),
						targetPosiCom.getPosition());

				if (itGotHit) {
					targetEntity.addComponent(new StatusChangeComponent(attaCom.getDamage(),""));
				}
			}

		}

	}
}
