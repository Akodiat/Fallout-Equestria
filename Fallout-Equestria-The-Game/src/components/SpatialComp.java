package components;

import math.Vector2;
import utils.Circle;
import entityFramework.IComponent;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * This is a component giving entities the
 * ability to get hit.
 * @author Gustav
 */
@XStreamAlias("Spatial")
@EditableComponent
public class SpatialComp implements IComponent {

	private Circle bounds;
	
	public SpatialComp(SpatialComp toBeCloned) {
		this.bounds = toBeCloned.getBounds();
	}
	
	public SpatialComp(Circle bounds) {
		this.bounds = bounds;
	}
	
	public SpatialComp(float radius) {
		this.bounds = new Circle(new Vector2(0,0),radius);
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
}
