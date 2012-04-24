package scripting;

import components.PhysicsComp;
import components.TransformationComp;

import utils.GameTime;

public class DestroyScript extends Behaviour {

	@Override
	public void start() {
	}

	@Override
	public void update(GameTime time) {
		this.entity.kill();
	}

	@Override
	public Object clone() {
		return new DestroyScript();
	}
	
}
