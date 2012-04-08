package tests;

import com.google.inject.AbstractModule;
import entityFramework.*;

public class EntityModule extends AbstractModule {

	@Override
	public void configure() {
		bind(IEntityWorld.class).to(EntityWorld.class).asEagerSingleton();
		bind(IEntitySystemManager.class).to(EntitySystemManager.class).asEagerSingleton();
		
		bind(IEntityManager.class).to(EntityManager.class).asEagerSingleton();
		bind(IEntityGroupManager.class).to(EntityGroupManager.class);
		bind(IEntityLabelManager.class).to(EntityLabelManager.class);
		
		bind(IEntityFactory.class).to(EntityFactory.class);
		bind(IEntityDatabase.class).to(EntityDatabase.class).asEagerSingleton();
	}

}
