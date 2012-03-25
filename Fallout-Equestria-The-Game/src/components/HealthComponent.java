package components;

import entityFramework.IComponent;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Health")
public class HealthComponent implements IComponent {

	private int maxHealth;
	private float regenSpeed;
	private float healthPoints;

	public HealthComponent() {
		this.maxHealth = 100;
		this.regenSpeed = 10;
		this.healthPoints = 100;
	}

	public HealthComponent(int maxHealth, float regenSpeed, float healthPoints) {
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
	public HealthComponent(HealthComponent other) {
		this.maxHealth = other.getMaxHealth();
		this.regenSpeed = other.getRegenSpeed();
		this.healthPoints = other.getHealthPoints();
	}

	public Object clone() {
		return new HealthComponent(this);
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

	public int getMaxHealth() {
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
