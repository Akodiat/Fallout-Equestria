package entitySystems;

import java.util.ArrayList;
import java.util.List;

import math.Vector2;

import utils.Circle;

import com.google.common.collect.ImmutableSet;

import components.AttackComponent;
import components.SpatialComponent;
import components.StatusChangeComponent;
import components.TransformationComp;

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

			Boolean hitSomething = false;

			for (IEntity targetEntity : gEntities) {
				SpatialComponent targetSpatiCom = sCM
						.getComponent(targetEntity);
				TransformationComp targetPosiCom = tCM
						.getComponent(targetEntity);

				Boolean itGotHit = Circle.intersects(attaCom.getBounds(),
						Vector2.add(posiCom.getPosition(), posiCom.getOrigin()), targetSpatiCom.getBounds(),
						Vector2.add(targetPosiCom.getPosition(), targetPosiCom.getOrigin()));

				if (itGotHit) {
					hitSomething = true;
					targetEntity.addComponent(new StatusChangeComponent(attaCom
							.getDamage(), ""));
					targetEntity.refresh();
					/*
					//DEBUG: PRINTING OUT EVERYTHING ABOUT TARGET AND ATTACK
					// ATTACK
					System.out.println(attaCom.getBounds().getRadius()
							+ "ATTAR");
					System.out.println(attaCom.getBounds().getPosition().X
							+ "ATTAOFFX");

					System.out.println(attaCom.getBounds().getPosition().Y
							+ "ATTAOFFY");
					System.out.println(posiCom.getPosition().X + "ATTAPOSX");
					System.out.println(posiCom.getPosition().Y + "ATTAPOSY");

					// TARGET
					System.out.println(targetSpatiCom.getBounds().getRadius()
							+ "TARGR");
					System.out
							.println(targetSpatiCom.getBounds().getPosition().X
									+ "TARGOFFX");

					System.out
							.println(targetSpatiCom.getBounds().getPosition().Y
									+ "TARGOFFY");
					System.out.println(targetPosiCom.getPosition().X
							+ "TARGPOSX");
					System.out.println(targetPosiCom.getPosition().Y
							+ "TARGPOSY");
							*/
				}
			}

			if (hitSomething) {
				entity.kill();
			}

		}

	}
}
