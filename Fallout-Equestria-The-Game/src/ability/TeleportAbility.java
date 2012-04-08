package ability;


import components.InputComp;
import components.TransformationComp;

import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;

public class TeleportAbility extends AbstractAbilityProcessor {

	
	public TeleportAbility() {
		super(Abilities.Teleport, TransformationComp.class, InputComp.class);
	}

	@Override
	public void initialize(IEntityManager manager) {
	}

	@Override
	public void processEntity(IEntity entity, IEntityManager manager, IEntityArchetype archetype) {
			TransformationComp transComp = entity.getComponent(TransformationComp.class);
			InputComp inputComp = entity.getComponent(InputComp.class);
			spawnCloud(entity);
			transComp.setPosition(inputComp.getMousePosition());
			spawnCloud(entity);
	}
	
	protected void spawnCloud(IEntity entity){
		System.out.println("SPAWNING CLOUD LIEK BAWS");
	}

}
