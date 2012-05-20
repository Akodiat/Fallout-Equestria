package tests;

import utils.time.Clock;
import utils.time.GameTime;

import com.google.inject.Guice;
import com.google.inject.Injector;

import entityFramework.IEntitySystem;
import entityFramework.IEntityWorld;

public class SystemTester {
	
	private IEntityWorld world;
	private boolean startedTesting;
	private Clock clock;
	
	public SystemTester() {
		Injector injector = Guice.createInjector(new EntityModule());
		world = injector.getInstance(IEntityWorld.class);
		this.clock = new Clock();
	}
	
	public void addRenderSubSystem(IEntitySystem system) {
		world.getSystemManager().addRenderEntitySystem(system);
	}
	
	public void addLogicSubSystem(IEntitySystem system) {
		world.getSystemManager().addLogicEntitySystem(system);
	}
	
	public IEntityWorld getWorld() {
		return this.world;
	}
	
	public void startTesting() {
		this.world.getSystemManager().initialize();
		this.startedTesting = true;
	}
	
	public void updateWorld(float deltha) {
		if(this.startedTesting) {
			this.clock.update();
			GameTime time = this.clock.getGameTime();
			world.update(time);
		}
	}
	
	public void renderWorld() {
		if(this.startedTesting) {
			world.render();
		}
	}
	
	public void stopTesting() {
		this.world = null;
	}
}