package entitySystems;


import math.Vector2;

import utils.Circle;


import com.google.common.collect.ImmutableSet;

import components.*;
import content.ContentManager;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import graphics.Color;

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
							
							createFloatingCombatText(targetPosiCom.getPosition(), attaCom.getDamage());

						}
					}
				}

				if (hitSomething && attaCom.isDestoryOnHit()) {
					entity.kill();
				}
			}
		}
	}

	private void createFloatingCombatText(Vector2 position, int damage) {
		IEntity entity = this.getEntityManager().createEmptyEntity();
		TransformationComp transComp = new TransformationComp();
		transComp.setPosition(position);
		transComp.setScale(new Vector2(2f,2f));
		
		TextRenderingComp textRenderComp = new TextRenderingComp();
		textRenderComp.setColor(Color.Red);
		textRenderComp.setFont(ContentManager.loadFont("arialb20.xml"));
		textRenderComp.setText("-" + damage);
		
		PhysicsComp physicsComp = new PhysicsComp(new Vector2(0f, -3));
		ExistanceComp existComp = new ExistanceComp(0.5f);
		
		entity.addComponent(transComp);
		entity.addComponent(textRenderComp);
		entity.addComponent(physicsComp);
		entity.addComponent(existComp);
		
		entity.refresh();
	}

	private boolean checkForCollision(AttackComp attaCom,
			TransformationComp posiCom, IEntity targetEntity,
			SpatialComp targetSpatiCom, TransformationComp targetPosiCom) {

		return Circle.intersects(attaCom.getBounds(), posiCom.getPosition(),
				targetSpatiCom.getBounds(), targetPosiCom.getPosition())
				&& !attaCom.getTargetsHit().contains(targetEntity);
	}
}
