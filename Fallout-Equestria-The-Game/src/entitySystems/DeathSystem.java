package entitySystems;

import components.DeathComp;

import entityFramework.EntitySystem;
import entityFramework.IEntityWorld;

public class DeathSystem extends EntitySystem{

	public DeathSystem(IEntityWorld world) {
		super(world, DeathComp.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}

}
