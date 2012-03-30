package components;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import entityFramework.IComponent;
import ability.Abilities;

/**
 * 
 * @author Lukas Kurtyan
 *
 */
@XStreamAlias("Weapon")
public class WeaponComp implements IComponent{
	//TODO implement rendering and similar effects!. 
	
	private Abilities primaryAbility;
	private Abilities secondaryAbility;
	
	public WeaponComp() {
		this(Abilities.None, Abilities.None);
	}
	
	public WeaponComp(Abilities primaryAbility, Abilities secondaryAbility) {
		this.setPrimaryAbility(primaryAbility);
		this.setSecondaryAbility(secondaryAbility);
	}
	
	private WeaponComp(WeaponComp other) {
		this.primaryAbility   = other.primaryAbility;
		this.secondaryAbility = other.secondaryAbility;
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
	
	@Override
	public WeaponComp clone() {
		return new WeaponComp(this);
	}
	
	public String toString() {
		return "Weapon: \n" 
			+  "Primary Ability   " + this.primaryAbility.toString() + "\n"
			+  "Secondary Ability " + this.secondaryAbility.toString();
	}
}
