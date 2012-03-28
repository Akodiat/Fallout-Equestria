package misc;

import ability.Ability;

public class Weapon {
	private Ability primaryAbility;
	private Ability secondaryAbility;
	
	public Weapon(Ability primaryAbility, Ability secondaryAbility) {
		this.setPrimaryAbility(primaryAbility);
		this.setSecondaryAbility(secondaryAbility);
	}

	public Ability getPrimaryAbility() {
		return primaryAbility;
	}

	public void setPrimaryAbility(Ability primaryAbility) {
		this.primaryAbility = primaryAbility;
	}

	public Ability getSecondaryAbility() {
		return secondaryAbility;
	}

	public void setSecondaryAbility(Ability secondaryAbility) {
		this.secondaryAbility = secondaryAbility;
	}
	
	public String toString() {
		return "Weapon: \n" 
			+  "Primary Ability   " + this.primaryAbility.toString() + "\n"
			+  "Secondary Ability " + this.secondaryAbility.toString();
	}
}
