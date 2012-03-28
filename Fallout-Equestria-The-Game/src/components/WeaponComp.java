package components;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import entityFramework.IComponent;
import ability.Ability;

/**
 * 
 * @author Lukas Kurtyan
 *
 */
@XStreamAlias("Weapon")
public class WeaponComp implements IComponent{
	//TODO implement rendering and similar effects!. 
	
	private Ability primaryAbility;
	private Ability secondaryAbility;
	
	public WeaponComp() {
		this(null, null);
	}
	
	public WeaponComp(Ability primaryAbility, Ability secondaryAbility) {
		this.setPrimaryAbility(primaryAbility);
		this.setSecondaryAbility(secondaryAbility);
	}
	
	private WeaponComp(WeaponComp other) {
		this.primaryAbility   = other.primaryAbility;
		this.secondaryAbility = other.secondaryAbility;
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
