package uinttests;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tests.EntityModule;

import ability.Abilities;
import ability.AbilityBuilder;
import ability.AbilitySystem;

import com.google.inject.Guice;
import com.google.inject.Injector;

import components.AbilityComp;
import components.InputComp;
import components.TransformationComp;

import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class GroupedAbilityEntitySystemTest {

	static IEntityWorld world;

	@BeforeClass
	public static void setup() {

		Injector injector = Guice.createInjector(new EntityModule());
		world = injector.getInstance(IEntityWorld.class);
		world.getSystemManager().addLogicEntitySystem(new AbilitySystem(world, AbilityBuilder.build()));

	}
	
	@Before
	public void setUp1() {
		IEntity player = world.getEntityManager().createEmptyEntity();
		player.setLabel("Player");
		player.addComponent(new InputComp());
		player.addComponent(new TransformationComp());
		player.addComponent(new AbilityComp());
		player.refresh();
	}
	
	@After
	public void tearDown() {
		world.getDatabase().clear();
	}
	
	@Test
	public void test() {
		IEntity player = world.getEntityManager().getEntity("Player");
		player.getComponent(AbilityComp.class).setAbility(Abilities.Teleport);
		world.update(1);
	}

}
