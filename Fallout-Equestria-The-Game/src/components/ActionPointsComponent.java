package components;

import entityFramework.IComponent;

public class ActionPointsComponent implements IComponent {

	private int maxAbilityPoints;
	private float abilityPoints;
	private float regenerationSpeed;

	public ActionPointsComponent() {
		this.maxAbilityPoints = 100;
		this.abilityPoints = 100;
		this.regenerationSpeed = 20;
	}

	/**
	 * Copyconstructor
	 * @param other
	 */
	public ActionPointsComponent(ActionPointsComponent other) {
		this.maxAbilityPoints = other.maxAbilityPoints;
		this.abilityPoints = other.abilityPoints;
		this.regenerationSpeed = other.regenerationSpeed;
	}

	public ActionPointsComponent(int maxAbilityPoints, float abilityPoints,
			float regenerationSpeed) {
		this.maxAbilityPoints = maxAbilityPoints;
		this.abilityPoints = abilityPoints;
		this.regenerationSpeed = regenerationSpeed;
	}
	
	public ActionPointsComponent clone(){
		return new ActionPointsComponent(this);
	}

	public int getMaxAbilityPoints() {
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

	public float getRegenerationSpeed() {
		return regenerationSpeed;
	}

	public void setRegenerationSpeed(float regenerationSpeed) {
		this.regenerationSpeed = regenerationSpeed;
	}

}
