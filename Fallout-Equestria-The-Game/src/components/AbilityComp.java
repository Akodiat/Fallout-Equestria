package components;

import utils.time.GameTime;
import ability.Ability;
import anotations.Editable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import entityFramework.IComponent;


@Editable @XStreamAlias("Ability")
public class AbilityComp implements IComponent {
	private static final float DEF_MAX_AP = 100;
	private static final float DEF_REGEN_SPEED = 10;
	
	private @Editable float maxAbilityPoints;
	private @Editable float regenerationSpeed;
		
	private float abilityPoints;
	private Ability activeAbility;

	public AbilityComp() {
		this.maxAbilityPoints = DEF_MAX_AP;
		this.abilityPoints = DEF_MAX_AP;
		this.regenerationSpeed = DEF_REGEN_SPEED;
		this.activeAbility = null;
	}

	/**
	 * Copyconstructor
	 * @param other
	 */
	public AbilityComp(AbilityComp other) {
		this.maxAbilityPoints = other.maxAbilityPoints;
		this.abilityPoints = other.abilityPoints;
		this.regenerationSpeed = other.regenerationSpeed;
		this.activeAbility = null;
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
	
	public void stopAbility(Ability ability) {
		if(this.activeAbility == ability) {
			this.activeAbility.stop();
			this.activeAbility = null;
		}
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
			   "\nRegen speed: " + this.regenerationSpeed;
	}
}

