package components;

import org.lwjgl.util.Rectangle;

import utils.Circle;

import entityFramework.IComponent;

/**
 * This is a component giving entities the
 * ability to get hit.
 * @author Gustav
 *
 */
public class SpatialComponent implements IComponent {

	private Circle bounds;
	
	public SpatialComponent(SpatialComponent toBeCloned) {
		super();
		this.bounds = toBeCloned.bounds;
	}
	
	public SpatialComponent(Circle bounds) {
		super();
		this.bounds = bounds;
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
}
