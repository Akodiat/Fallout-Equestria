package components;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import entityFramework.IComponent;
import ability.AbilityInfo;

/**
 * 
 * @author Lukas Kurtyan
 *
 */
@XStreamAlias("Weapon")
@EditableComponent
public class WeaponComp implements IComponent{
	//TODO implement rendering and similar effects!. 
	
	private AbilityInfo primaryAbility;
	private AbilityInfo secondaryAbility;
	
	public WeaponComp() {
		this(AbilityInfo.None, AbilityInfo.None);
	}
	
	public WeaponComp(AbilityInfo primaryAbility, AbilityInfo secondaryAbility) {
		this.setPrimaryAbility(primaryAbility);
		this.setSecondaryAbility(secondaryAbility);
	}
	
	private WeaponComp(WeaponComp other) {
		this.primaryAbility   = other.primaryAbility;
		this.secondaryAbility = other.secondaryAbility;
	}
	
	public AbilityInfo getPrimaryAbility() {
		return primaryAbility;
	}

	public void setPrimaryAbility(AbilityInfo primaryAbility) {
		this.primaryAbility = primaryAbility;
	}

	public AbilityInfo getSecondaryAbility() {
		return secondaryAbility;
	}

	public void setSecondaryAbility(AbilityInfo secondaryAbility) {
		this.secondaryAbility = secondaryAbility;
	}
	
	@Override
	public WeaponComp clone() {
		return new WeaponComp(this);
	}
	
	public String toString() {
		String s = "Weapon: \n";
		s += "Primary Ability ";

		if(this.primaryAbility != null) {
			s += this.primaryAbility.toString();
		} else {
			s += "None";
		}

		s += "Secondary Ability ";

		if(this.secondaryAbility != null) {
			s += this.secondaryAbility.toString();
		} else {
			s += "None";
		}
		
		return s;
	}
}
