package ability;

import utils.GameTime;

public class AnimationManager {
	private Ability activeAbility;
		
	public void startAbility(Ability ability) {
		if(ability == null) {
			throw new NullPointerException("ability cannot be null");
		}
		
		if(this.activeAbility == null) {
			this.activeAbility = ability;
			this.activeAbility.start();
		} else if(!this.activeAbility.isBlocking()) {
			this.activeAbility.stop();
			this.activeAbility = ability;
			this.activeAbility.start();
		}
	}
	
	public void update(GameTime time) {
		if(this.activeAbility != null) {
			this.activeAbility.update(time);
		}
	}
	
	public void stopActiveAbility() {
		if(this.activeAbility != null) {
			this.activeAbility.stop();
			this.activeAbility = null;
		}
	}
	
}
