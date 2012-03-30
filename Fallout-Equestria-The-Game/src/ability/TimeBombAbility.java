package ability;

import components.AttackComp;
import components.DeathComp;
import components.ExistanceComp;
import components.RenderingComp;
import components.TransformationComp;
import content.ContentManager;
import death.IDeathAction;

import math.Vector2;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;

public class TimeBombAbility  extends Ability{

	private final IEntityArchetype explostionArchetype;
	private final IEntityArchetype countDownArchetype;
	
	public TimeBombAbility(IEntityArchetype explostionArchetype, IEntityArchetype countDownArche, int apCost, float cooldown) {
		super(apCost, cooldown);
		this.explostionArchetype = explostionArchetype;
		this.countDownArchetype = countDownArche;
	}

	@Override
	protected void use(IEntity sourceEntity, Vector2 targetPos,
			final IEntityManager manager) {
		final IEntity countDownEntity = manager.createEntity(this.countDownArchetype);
		TransformationComp transComp = countDownEntity.getComponent(TransformationComp.class);
		transComp.setPosition(targetPos);
		IEntity cloud = manager.createEntity(ContentManager.loadArchetype("Cloud.archetype"));
		cloud.getComponent(TransformationComp.class).setPosition(transComp.getPosition());
		cloud.getComponent(TransformationComp.class).setScale(2f, 2f);
		cloud.refresh();
		
		DeathComp deathComp = createDeathComponent(sourceEntity, manager, transComp);	
		countDownEntity.addComponent(deathComp);			
		countDownEntity.refresh();
	}

	private DeathComp createDeathComponent(final IEntity sourceEntity, final IEntityManager manager,
			final TransformationComp transComp) {
		DeathComp deathComp = new DeathComp();
		deathComp.addDeathAction( new IDeathAction() {
			@Override
			public void excecute(IEntity deadEntity, IEntityManager entityManager) {
				IEntity explosionEntity = manager.createEntity(explostionArchetype);
				
				TransformationComp explosionTransComp = explosionEntity.getComponent(TransformationComp.class);
				explosionTransComp.setPosition(transComp.getPosition());
				explosionEntity.getComponent(AttackComp.class).setSourceEntity(sourceEntity);
				
				explosionEntity.refresh();
			}
		});
		return deathComp;
	}
}
