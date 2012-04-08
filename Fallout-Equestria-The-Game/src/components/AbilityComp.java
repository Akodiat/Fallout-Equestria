package components;

import ability.AbilityInfo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import entityFramework.IComponent;


@XStreamAlias("Ability")
@EditableComponent
public class AbilityComp implements IComponent {

	private static final float DEF_MAX_AP = 100;
	private static final float DEF_REGEN_SPEED = 10;
	
	private float maxAbilityPoints;
	private float abilityPoints;
	private float regenerationSpeed;
	private AbilityInfo ability;

	public AbilityComp() {
		this.maxAbilityPoints = DEF_MAX_AP;
		this.abilityPoints = DEF_MAX_AP;
		this.regenerationSpeed = DEF_REGEN_SPEED;
		this.ability = AbilityInfo.None;
	}

	/**
	 * Copyconstructor
	 * @param other
	 */
	public AbilityComp(AbilityComp other) {
		this.maxAbilityPoints = other.maxAbilityPoints;
		this.abilityPoints = other.abilityPoints;
		this.regenerationSpeed = other.regenerationSpeed;
		this.ability = other.ability;
	}

	public AbilityComp(int maxAbilityPoints, float abilityPoints,
			float regenerationSpeed) {
		this.maxAbilityPoints = maxAbilityPoints;
		this.abilityPoints = abilityPoints;
		this.regenerationSpeed = regenerationSpeed;
	}
	
	public AbilityComp clone(){
		return new AbilityComp(this);
	}
	
	public AbilityInfo getAbility() {
		return ability;
	}

	public void setAbility(AbilityInfo ability) {
		this.ability = ability;
	}

	public float getMaxAbilityPoints() {
		return maxAbilityPoints;
	}

	public void setMaxAbilityPoints(int maxAbilityPoints) {
		this.maxAbilityPoints = maxAbilityPoints;
	}

	public float getAbilityPoints() {
		return abilityPoints;
	}

	public void setAbilityPoints(float abilityPoints) {
		this.abilityPoints = abilityPoints;
	}
	
	public void addAbilityPoints(float abilityPoints) {
		float temp = this.abilityPoints + abilityPoints;
		if (temp <= this.maxAbilityPoints) {
			this.abilityPoints = temp;
		}
	}
	
	public void removeAbilityPoints(float abilityPoints) {
		this.addAbilityPoints(-abilityPoints);
	}
	
	public void regenAbilityPoints() {
		this.addAbilityPoints(this.regenerationSpeed);
	}

	public float getRegenerationSpeed() {
		return regenerationSpeed;
	}

	public void setRegenerationSpeed(float regenerationSpeed) {
		this.regenerationSpeed = regenerationSpeed;
	}
	
	public String toString() {
		return "ActionComp: \n" + 
			   "Current ability points: " + this.abilityPoints +
			   "\nMax abtility points: " + this.maxAbilityPoints +
			   "\nRegen speed: " + this.regenerationSpeed +
			   "\nActive ability: " + this.ability;
	}
}

