package ability;


import components.InputComp;

import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class SpawnCreaturesAbility extends AbstractAbilityProcessor {

	public SpawnCreaturesAbility(int apCost, float spawningInterval, int creatureAmount) {
		super(Abilities.SpawnCreatures, InputComp.class);
	}

	@Override
	public void processEntity(IEntity entity, IEntityManager manager, IEntityArchetype specialArchetype) {
		
		throw new UnsupportedOperationException();
		
		/*TransformationComp sourceTransComp = entity.getComponent(TransformationComp.class);
		InputComp inpComp = entity.getComponent(InputComp.class);
		Vector2 targetPos = inpComp.getMousePosition();
		
		Vector2 attackSpeed = Vector2.subtract(targetPos, sourceTransComp.getPosition());
		attackSpeed = Vector2.norm(attackSpeed);
		for (int i = 0; i<creatureAmount;i++){
			IEntity creature = manager.createEmptyEntity();
			attackSpeed = Vector2.rotate(attackSpeed, (float)(MathHelper.Pi/creatureAmount));
			
			RenderingComp rendComp = new RenderingComp();
			rendComp.setTexture(ContentManager.loadTexture("trixie_filly.png"));
			
			BasicAIComp aiComp = new BasicAIComp(targetPos);
			
			WeaponComp weaponComp = new WeaponComp(new AbilityInfo(ability));
			HealthComp healthComp = new HealthComp(10f,1f,10f);
			
			TransformationComp transComp = new TransformationComp();
			transComp.setPosition((Vector2.add(sourceTransComp.getPosition(), Vector2.mul(100, attackSpeed))));
			
			PhysicsComp physComp = new PhysicsComp();
			
			SpatialComp spatialComp = new SpatialComp(new Circle(Vector2.Zero, 10f));
			
			AbilityComp abComp = new AbilityComp(15, 15, 1);
			
			
			creature.addComponent(aiComp);
			creature.addComponent(weaponComp);
			creature.addComponent(healthComp);
			creature.addComponent(transComp);
			creature.addComponent(rendComp);
			creature.addComponent(physComp);
			creature.addComponent(spatialComp);
			creature.addComponent(abComp);
			
			creature.refresh();
		}*/
	}

	@Override
	public void initialize(IEntityManager manager) {
		// TODO Auto-generated method stub
		
	}

}
