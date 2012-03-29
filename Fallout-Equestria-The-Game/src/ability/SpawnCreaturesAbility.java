package ability;

import components.AttackComp;
import components.BasicAIComp;
import components.HealthComp;
import components.PhysicsComp;
import components.RenderingComp;
import components.TransformationComp;
import components.WeaponComp;
import content.ContentManager;

import math.MathHelper;
import math.Vector2;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class SpawnCreaturesAbility extends Ability {

	private final IEntityArchetype creatureArch;
	private final int creatureAmount;
	
	public SpawnCreaturesAbility(IEntityArchetype creatureArch, int apCost, float spawningInterval, int creatureAmount) {
		super(apCost, spawningInterval);
		this.creatureAmount = creatureAmount;
		this.creatureArch = creatureArch;
	}

	@Override
	protected void use(IEntity sourceEntity, Vector2 targetPos,
			IEntityManager manager) {
		TransformationComp transComp = sourceEntity.getComponent(TransformationComp.class);

		Vector2 attackSpeed = Vector2.subtract(targetPos, transComp.getPosition());
		attackSpeed = Vector2.norm(attackSpeed);
		for (int i = 0; i<creatureAmount;i++){
			IEntity creature = manager.createEntity(creatureArch);
			attackSpeed = Vector2.rotate(attackSpeed, (float)(MathHelper.Pi/creatureAmount));
			creature.getComponent(AttackComp.class).setSourceEntity(sourceEntity);
			//creature.getComponent(TransformationComp.class).setRotation(attackSpeed.angle()); //Kamikaze
			creature.getComponent(TransformationComp.class).setPosition(Vector2.add(transComp.getPosition(), Vector2.mul(100, attackSpeed)));
			creature.getComponent(RenderingComp.class).setTexture(ContentManager.loadTexture("trixie_filly.png"));
			BasicAIComp aiComp = new BasicAIComp(targetPos);
			//TODO: Fix magic numbers!
			WeaponComp weaponComp = new WeaponComp(new BulletAbility(ContentManager.loadArchetype("ppieBullet.archetype"), 5, 5f, 10f), null);
			HealthComp healthComp = new HealthComp(10f,1f,10f);
			
			creature.addComponent(aiComp);
			creature.addComponent(weaponComp);
			creature.addComponent(healthComp);
			
			creature.refresh();
		}
		
	}

}
