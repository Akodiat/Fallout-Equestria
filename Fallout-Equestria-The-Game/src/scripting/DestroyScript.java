package scripting;

import components.PhysicsComp;
import components.TransformationComp;

import utils.GameTime;

public class DestroyScript extends BehaviourScript {

	@Override
	public void start() {
	}

	@Override
	public void update(GameTime time) {
		this.entity.kill();
	}

	@Override
	public BehaviourScript createNew() {
		return new DestroyScript();
	}
	
}
