package misc;

import ability.Abilities;

public class Weapon {
	private Abilities primaryAbility;
	private Abilities secondaryAbility;
	
	public Weapon(Abilities primaryAbility, Abilities secondaryAbility) {
		this.setPrimaryAbility(primaryAbility);
		this.setSecondaryAbility(secondaryAbility);
	}

	public Abilities getPrimaryAbility() {
		return primaryAbility;
	}

	public void setPrimaryAbility(Abilities primaryAbility) {
		this.primaryAbility = primaryAbility;
	}

	public Abilities getSecondaryAbility() {
		return secondaryAbility;
	}

	public void setSecondaryAbility(Abilities secondaryAbility) {
		this.secondaryAbility = secondaryAbility;
	}
	
	public String toString() {
		return "Weapon: \n" 
			+  "Primary Ability   " + this.primaryAbility.toString() + "\n"
			+  "Secondary Ability " + this.secondaryAbility.toString();
	}
}
