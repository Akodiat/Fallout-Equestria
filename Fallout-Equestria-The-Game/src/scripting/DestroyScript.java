package scripting;

import components.PhysicsComp;
import components.TransformationComp;

import utils.GameTime;

public class DestroyScript extends Behavior {

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
