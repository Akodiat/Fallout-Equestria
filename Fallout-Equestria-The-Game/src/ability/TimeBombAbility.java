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

	private final float timeUntilExplosion;
	private final IEntityArchetype archetype;
	
	public TimeBombAbility(IEntityArchetype explostionArchetype, float timeUntilExplode, int apCost, float cooldown) {
		super(apCost, cooldown);
		this.timeUntilExplosion = timeUntilExplode;
		this.archetype = explostionArchetype;
	}

	@Override
	protected void use(IEntity sourceEntity, Vector2 targetPos,
			final IEntityManager manager) {
		final IEntity tmp = manager.createEmptyEntity();
		
		RenderingComp renderComp = createRenderingComponent();
		
		TransformationComp transComp = createTransformationComponent(targetPos,
				renderComp);
		
		ExistanceComp existComp = new ExistanceComp(this.timeUntilExplosion);
		
		DeathComp deathComp = createDeathComponent(sourceEntity, manager, transComp);
		
		tmp.addComponent(deathComp);
		tmp.addComponent(renderComp);
		tmp.addComponent(transComp);	
		tmp.addComponent(existComp);
		
		tmp.refresh();
	}

	private RenderingComp createRenderingComponent() {
		//TODO the following is a placeholder!
		RenderingComp renderComp = new RenderingComp();
		renderComp.setTexture(ContentManager.loadTexture("Circle100pxGrey.png"));
		//The placeholder ends here.
		return renderComp;
	}

	private TransformationComp createTransformationComponent(Vector2 targetPos,
			RenderingComp renderComp) {
		TransformationComp transComp = new TransformationComp();
		transComp.setPosition(targetPos);
		transComp.setOrigin(renderComp.getTexture().getBounds().getCenter());
		return transComp;
	}

	private DeathComp createDeathComponent(final IEntity sourceEntity, final IEntityManager manager,
			final TransformationComp transComp) {
		DeathComp deathComp = new DeathComp();
		deathComp.addDeathAction( new IDeathAction() {
			@Override
			public void excecute(IEntity deadEntity, IEntityManager entityManager) {
				IEntity explosionEntity = manager.createEntity(archetype);
				
				TransformationComp explosionTransComp = explosionEntity.getComponent(TransformationComp.class);
				explosionTransComp.setPosition(transComp.getPosition());
				explosionEntity.getComponent(AttackComp.class).setSourceEntity(sourceEntity);
				
				explosionEntity.refresh();
			}
		});
		return deathComp;
	}
}
