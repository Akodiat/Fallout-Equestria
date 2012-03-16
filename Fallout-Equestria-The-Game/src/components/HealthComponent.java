package components;

import entityFramework.IComponent;

public class HealthComponent implements IComponent {
	
	private int healthPoints;

	public HealthComponent(int healthPoints) {
		this.healthPoints = healthPoints;
	}
	
	public Object clone(){
		return new HealthComponent(this.healthPoints);
	}

	public int getHealthPoints() {
		return healthPoints;
	}

	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}
	
	public void addHealthPoints(int healthPointsToAdd){
		this.healthPoints += healthPointsToAdd;
	}

}
