package components;

import entityFramework.IComponent;
import anotations.Editable;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@Editable @XStreamAlias("Health")
public class HealthComp implements IComponent {

	private final static float DEF_MAX_HP = 100;
	private final static float DEF_REGEN_SPEED = 10;
	
	private	@Editable float maxHealth;
	private @Editable float regenSpeed;
	
	private float healthPoints;

	public HealthComp() {
		this.maxHealth = DEF_MAX_HP;
		this.healthPoints = DEF_MAX_HP;
		this.regenSpeed = DEF_REGEN_SPEED;
	}

	public HealthComp(float maxHealth, float regenSpeed, float healthPoints) {
		if (maxHealth <= 0) {
			throw new RuntimeException("Tried to set maxhealth below or equal to zero");
		}
		
		this.maxHealth = maxHealth;
		this.regenSpeed = regenSpeed;

		if (healthPoints <= this.maxHealth) {
			this.healthPoints = healthPoints;
		} else {
			this.healthPoints = this.maxHealth;
		}
	}

	/**
	 * CopyConstructor
	 * 
	 * @param other
	 */
	public HealthComp(HealthComp other) {
		this.maxHealth = other.getMaxHealth();
		this.regenSpeed = other.getRegenSpeed();
		this.healthPoints = other.getHealthPoints();
	}

	public Object clone() {
		return new HealthComp(this);
	}

	public float getHealthPoints() {
		return healthPoints;
	}

	public void setHealthPoints(float healthPoints) {
		if (healthPoints <= this.maxHealth) {
			this.healthPoints = healthPoints;
		} else {
			this.healthPoints = this.maxHealth;
		}
	}

	public void addHealthPoints(float healthPointsToAdd) {
		float temp = this.healthPoints + healthPointsToAdd;
		this.setHealthPoints(temp);
	}

	public void regenHealthPoints() {
		this.addHealthPoints(this.regenSpeed);
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		if (maxHealth <= 0) {
			throw new RuntimeException("Tried to set maxhealth below or equal to zero");
		}
		this.maxHealth = maxHealth;
	}

	public float getRegenSpeed() {
		return regenSpeed;
	}

	public void setRegenSpeed(float regenSpeed) {
		this.regenSpeed = regenSpeed;
	}
	
	public String toString() {
		return "Health Comp: \n"
			 + "Current Health: " + this.healthPoints + "\n"
			 + "Max Health: " + this.maxHealth + "\n" 
			 + "Regen Speed: " + this.regenSpeed;
	}

}
