package components;

import utils.Circle;
import entityFramework.IComponent;

public class RadiationComp implements IComponent{
	private Circle bounds;
	private float radiationLevel;
	
	public RadiationComp(RadiationComp toBeCloned) {
		this.bounds = toBeCloned.getBounds();
		this.radiationLevel = toBeCloned.getRadiationLevel();
	}
	
	public RadiationComp(Circle bounds, float radiationLevel) {
		this.bounds = bounds;
		this.radiationLevel = radiationLevel;
	}
	
	public Object clone(){
		return new RadiationComp(this);
	}
	
	public Circle getBounds() {
		return bounds;
	}
	
	public float getRadiationLevel() {
		return radiationLevel;
	}
	
	public void addRadiation(float dose) {
		this.radiationLevel += dose;
	}
	
	public String toString() {
		return "RadiationComp: \n" +
			   "Circle bounds: " + this.bounds.toString() + "\n " +
			   "Radiation level: " + this.radiationLevel;
	}
}
