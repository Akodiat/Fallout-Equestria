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
public class SpatialComponent implements IComponent {

	private Circle bounds;
	
	public SpatialComponent(SpatialComponent toBeCloned) {
		this.bounds = toBeCloned.getBounds();
	}
	
	public SpatialComponent(Circle bounds) {
		this.bounds = bounds;
	}
	
	public SpatialComponent(float radius) {
		this.bounds = new Circle(new Vector2(0,0),radius);
	}

	public Circle getBounds() {
		return bounds;
	}

	public void setBounds(Circle bounds) {
		this.bounds = bounds;
	}
	
	public Object clone(){
		return new SpatialComponent(this);
	}
	
	public String toString() {
		return "Spatial Component: \n" 
			+  "Circle Bounds: " + this.bounds.toString();
	}
}
