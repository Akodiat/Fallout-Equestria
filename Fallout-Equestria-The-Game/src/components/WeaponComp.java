package components;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import entityFramework.IComponent;
import entityFramework.IEntityArchetype;
import ability.AbilityInfo;
import anotations.Editable;
import anotations.Editable;

/**
 * 
 * @author Lukas Kurtyan & Joakim Johansson
 *
 */
@XStreamAlias("Weapon")
@Editable
public class WeaponComp implements IComponent{
	//TODO implement rendering and similar effects!. 
	
	@Editable
	private IEntityArchetype primaryArch;
	@Editable
	private IEntityArchetype secondaryArch;
	@Editable
	private boolean isMelee;
	
	public WeaponComp() {
		this(null, null, false);
	}
	
	public WeaponComp(IEntityArchetype primaryAbility, IEntityArchetype secondaryAbility, boolean isMelee) {
		this.setPrimaryArchetype(primaryAbility);
		this.setSecondaryArchetype(secondaryAbility);
	}
	
	private WeaponComp(WeaponComp other) {
		this.primaryArch   = other.primaryArch;
		this.secondaryArch = other.secondaryArch;
		this.isMelee 	   = other.isMelee;
	}
	
	public boolean isMelee(){
		return this.isMelee;
	}
	
	public boolean isRanged(){
		return !this.isMelee;
	}
	
	public IEntityArchetype getPrimaryArchetype() {
		return primaryArch;
	}

	public void setPrimaryArchetype(IEntityArchetype primaryAbility) {
		this.primaryArch = primaryAbility;
	}

	public IEntityArchetype getSecondaryArchetype() {
		return secondaryArch;
	}

	public void setSecondaryArchetype(IEntityArchetype secondaryAbility) {
		this.secondaryArch = secondaryAbility;
	}
	
	@Override
	public WeaponComp clone() {
		return new WeaponComp(this);
	}
	
	public String toString() {
		String s = "Weapon: \n";
		s += "Primary Ability ";

		if(this.primaryArch != null) {
			s += this.primaryArch.toString();
		} else {
			s += "None";
		}

		s += "Secondary Ability ";

		if(this.secondaryArch != null) {
			s += this.secondaryArch.toString();
		} else {
			s += "None";
		}
		
		return s;
	}
}
