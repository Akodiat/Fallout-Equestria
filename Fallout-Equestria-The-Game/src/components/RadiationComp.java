package components;

import anotations.Editable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import utils.Circle;
import entityFramework.IComponent;

@XStreamAlias("Radiation")
@Editable
public class RadiationComp implements IComponent{
	private Circle bounds;
	@Editable
	private float radiationLevel;
	
	public RadiationComp() {
		this(Circle.Empty, 0);
	}
	
	public RadiationComp(RadiationComp toBeCloned) {
		this.bounds = toBeCloned.getBounds();
		this.radiationLevel = toBeCloned.getRadiationLevel();
	}
	
	public RadiationComp(Circle bounds, int radiationLevel) {
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
	
	public void radiate(float dose, Circle spatial) {
			this.radiationLevel += dose;
			this.bounds.setRadius(radiationLevel * spatial.getRadius());
	}
	
	public String toString() {
		return "RadiationComp: \n" +
			   "Circle bounds: " + this.bounds.toString() + "\n " +
			   "Radiation level: " + this.radiationLevel;
	}
}
