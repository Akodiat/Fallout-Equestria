package statuses;

import entityFramework.IEntity;
import utils.ITimerListener;

public abstract class ImmobilizedStatus implements IStatus, ITimerListener {
	private IEntity target;
	
	public ImmobilizedStatus(IEntity target) {
		this.target = target;
	}
	
	public abstract void activateStatusEffect();
	
	public void deactivateStatusEffect() {
		
	}
	
	public abstract void update();
	
	@Override
	public void Start() {
		this.activateStatusEffect();
	}

	@Override
	public void Tick() {
		this.update();
	}

	@Override
	public void Complete() {
		this.deactivateStatusEffect();
	}
}
