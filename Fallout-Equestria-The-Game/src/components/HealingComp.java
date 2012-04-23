package components;

import anotations.Editable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import utils.Circle;
import entityFramework.IComponent;

@XStreamAlias("Healing")
@Editable
public class HealingComp implements IComponent{
	@Editable
	private Circle bounds;
	@Editable
	private float healingPower;
	
	public HealingComp(HealingComp toBeCloned) {
		this.bounds = toBeCloned.getBounds();
		this.healingPower = toBeCloned.getHealingPower();
	}
	
	public HealingComp(Circle bounds, float healingPower) {
		this.bounds = bounds;
		this.healingPower = healingPower;
	}
	
	public Circle getBounds() {
		return this.bounds;
	}
	
	public Object clone(){
		return new HealingComp(this);
	}

	public float getHealingPower() {
		return healingPower;
	}

	public void setHealingPower(float healingPower) {
		this.healingPower = healingPower;
	}
	
	public String toString() {
		return "RadiationComp: \n" +
			   "Circle bounds: " + this.bounds.toString() + "\n " +
			   "Healing power: " + this.healingPower;
	}
}
