package ability;

import utils.Circle;
import components.AbilityPointsComp;
import components.AttackComp;
import components.BasicAIComp;
import components.HealthComp;
import components.PhysicsComp;
import components.RenderingComp;
import components.SpatialComp;
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
		TransformationComp sourceTransComp = sourceEntity.getComponent(TransformationComp.class);

		Vector2 attackSpeed = Vector2.subtract(targetPos, sourceTransComp.getPosition());
		attackSpeed = Vector2.norm(attackSpeed);
		for (int i = 0; i<creatureAmount;i++){
			IEntity creature = manager.createEmptyEntity();
			attackSpeed = Vector2.rotate(attackSpeed, (float)(MathHelper.Pi/creatureAmount));
			
			RenderingComp rendComp = new RenderingComp();
			rendComp.setTexture(ContentManager.loadTexture("trixie_filly.png"));
			
			BasicAIComp aiComp = new BasicAIComp(targetPos);
			
			WeaponComp weaponComp = new WeaponComp(new BulletAbility(ContentManager.loadArchetype("ppieBullet.archetype"), 5, 5f, 10f), null);
			HealthComp healthComp = new HealthComp(10f,1f,10f);
			
			TransformationComp transComp = new TransformationComp();
			transComp.setPosition((Vector2.add(sourceTransComp.getPosition(), Vector2.mul(100, attackSpeed))));
			
			PhysicsComp physComp = new PhysicsComp();
			
			SpatialComp spatialComp = new SpatialComp(new Circle(Vector2.Zero, 10f));
			
			AbilityPointsComp abComp = new AbilityPointsComp(15, 15, 1);
			
			creature.addComponent(aiComp);
			creature.addComponent(weaponComp);
			creature.addComponent(healthComp);
			creature.addComponent(transComp);
			creature.addComponent(rendComp);
			creature.addComponent(physComp);
			creature.addComponent(spatialComp);
			creature.addComponent(abComp);
			
			creature.refresh();
		}
		
	}

}
