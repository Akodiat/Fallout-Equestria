package components;

import javax.print.attribute.standard.MediaSize.Other;

import math.Vector3;
import utils.BoundingBox;
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

	private BoundingBox bounds;
	
	@Editable
	private boolean trigger;	
	@Editable
	private boolean collideable;
	
	public SpatialComp() {
		this(new BoundingBox(Vector3.Zero, Vector3.Zero), false);
	}
	
	public SpatialComp(BoundingBox bounds, boolean isTrigger) {
		this.bounds = bounds;
		this.setTrigger(isTrigger);
		this.setCollideable(true);
	}
	
	public SpatialComp(SpatialComp toBeCloned) {
		this.bounds = toBeCloned.bounds;
		this.trigger = toBeCloned.trigger;
		this.collideable = toBeCloned.collideable;
	}
	public Object clone(){
		return new SpatialComp(this);
	}

	public BoundingBox getBounds() {
		return bounds;
	}

	public void setBounds(BoundingBox bounds) {
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

	public boolean isCollideable() {
		return collideable;
	}

	public void setCollideable(boolean collideable) {
		this.collideable = collideable;
	}
}
