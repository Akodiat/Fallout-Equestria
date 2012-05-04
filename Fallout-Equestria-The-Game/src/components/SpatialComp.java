package components;

import utils.Circle;
import entityFramework.IComponent;
import anotations.Editable;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * This is a component giving entities the
 * ability to get hit.
 * @author Gustav
 */
@XStreamAlias("Spatial")
@Editable
public class SpatialComp implements IComponent {

	@Editable
	private Circle bounds;
	
	@Editable
	private boolean trigger;
	
	public SpatialComp() {
		this(Circle.Empty, false);
	}

	public SpatialComp(float radius) {
		this(new Circle(radius), false);
	}
	
	
	public SpatialComp(Circle bounds, boolean isTrigger) {
		this.bounds = bounds;
		this.setTrigger(isTrigger);
	}
	
	public SpatialComp(SpatialComp toBeCloned) {
		this.bounds = toBeCloned.bounds;
		this.trigger = toBeCloned.trigger;
	}
	public Object clone(){
		return new SpatialComp(this);
	}

	public Circle getBounds() {
		return bounds;
	}

	public void setBounds(Circle bounds) {
		this.bounds = bounds;
	}
	
	public String toString() {
		return "Spatial Component: \n" 
			+  "Circle Bounds: " + this.bounds.toString();
	}

	public boolean isTrigger() {
		return trigger;
	}

	public void setTrigger(boolean trigger) {
		this.trigger = trigger;
	}
}
