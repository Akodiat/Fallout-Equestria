package components;

import entityFramework.IComponent;

public class StatusChangeComponent implements IComponent {

	private float damageToTake;
	private String statusEffectChange;
	
	public StatusChangeComponent() {
		this.damageToTake = 1;
		this.statusEffectChange = "";
	}
	
	public StatusChangeComponent(float damageToTake, String statusEffectChange) {
		this.damageToTake = damageToTake;
		this.statusEffectChange = statusEffectChange;
	}
	
	/**
	 * Copyconstructor
	 * @param other
	 */
	public StatusChangeComponent(StatusChangeComponent other) {
		this.damageToTake = other.getDamageToTake();
		this.statusEffectChange = other.getStatusEffectChange();
	}
	
	public StatusChangeComponent clone(){
		return new StatusChangeComponent(this);
	}
	
	public float getDamageToTake() {
		return damageToTake;
	}
	
	public void setDamageToTake(float damageToTake) {
		this.damageToTake = damageToTake;
	}
	
	public String getStatusEffectChange() {
		return statusEffectChange;
	}
	
	public void setStatusEffectChange(String statusEffectChange) {
		this.statusEffectChange = statusEffectChange;
	}
	
	public String toString() {
		return "Status Changed Comp: \n"
			+  "Damage to take: " + this.damageToTake 
			+  "Status Changed Effect:" + this.statusEffectChange;
	}
}
