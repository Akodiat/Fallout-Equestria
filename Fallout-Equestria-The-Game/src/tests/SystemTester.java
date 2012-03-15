package tests;

import com.google.inject.Guice;
import com.google.inject.Injector;

import entityFramework.EntityWorld;
import entityFramework.IEntitySystem;
import entityFramework.IEntityWorld;

public class SystemTester {
	
	private IEntityWorld world;
	private boolean startedTesting;
	
	public SystemTester() {
		Injector injector = Guice.createInjector(new EntityModule());
		world = injector.getInstance(IEntityWorld.class);
	}
	
	
	public void addRenderSubSystem(IEntitySystem system) {
		world.getSystemManager().addRenderEntitySystem(system);
	}
	
	public void addLogicubSystem(IEntitySystem system) {
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
			world.update(deltha);
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