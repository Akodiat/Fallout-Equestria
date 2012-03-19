package components;

import entityFramework.IComponent;

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
		this.maxHealth = maxHealth;
		this.regenSpeed = regenSpeed;
		this.healthPoints = healthPoints;
	}
/**
 * CopyConstructor
 * @param other
 */
	public HealthComponent(HealthComponent other) {
		this.maxHealth = other.maxHealth;
		this.regenSpeed = other.regenSpeed;
		this.healthPoints = other.healthPoints;
	}

	public Object clone() {
		return new HealthComponent(this.maxHealth, this.regenSpeed,
				this.healthPoints);
	}

	public float getHealthPoints() {
		return healthPoints;
	}

	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}

	public void addHealthPoints(int healthPointsToAdd) {
		this.healthPoints += healthPointsToAdd;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public float getRegenSpeed() {
		return regenSpeed;
	}

	public void setRegenSpeed(float regenSpeed) {
		this.regenSpeed = regenSpeed;
	}

}
