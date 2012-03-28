package components;

import entityFramework.IComponent;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("ActionPoints")
public class AbilityPointsComp implements IComponent {

	private static final float DEF_MAX_AP = 100;
	private static final float DEF_REGEN_SPEED = 10;
	
	private float maxAbilityPoints;
	private float abilityPoints;
	private float regenerationSpeed;

	public AbilityPointsComp() {
		this.maxAbilityPoints = DEF_MAX_AP;
		this.abilityPoints = DEF_MAX_AP;
		this.regenerationSpeed = DEF_REGEN_SPEED;
	}

	/**
	 * Copyconstructor
	 * @param other
	 */
	public AbilityPointsComp(AbilityPointsComp other) {
		this.maxAbilityPoints = other.getMaxAbilityPoints();
		this.abilityPoints = other.getAbilityPoints();
		this.regenerationSpeed = other.getRegenerationSpeed();
	}

	public AbilityPointsComp(int maxAbilityPoints, float abilityPoints,
			float regenerationSpeed) {
		this.maxAbilityPoints = maxAbilityPoints;
		this.abilityPoints = abilityPoints;
		this.regenerationSpeed = regenerationSpeed;
	}
	
	public AbilityPointsComp clone(){
		return new AbilityPointsComp(this);
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
