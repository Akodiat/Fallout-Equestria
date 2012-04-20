package scripting;

import entityFramework.IEntity;

public class DestroyOnHitScript extends HitScript {

	@Override
	public void start() {
	}

	@Override
	public void onHit(IEntity cba) {
		this.entity.kill();
	}

	@Override
	public HitScript createNew() {
		return new DestroyOnHitScript();
	}
	
}
