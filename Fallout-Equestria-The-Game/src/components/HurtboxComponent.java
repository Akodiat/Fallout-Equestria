package components;

import org.lwjgl.util.Rectangle;

import entityFramework.IComponent;

/**
 * This is a component giving entities the
 * ability to get hit.
 * @author Gustav
 *
 */
public class HurtboxComponent implements IComponent {

	private Rectangle bounds;
	
	public HurtboxComponent(HurtboxComponent toBeCloned) {
		super();
		this.bounds = toBeCloned.bounds;
	}
	
	public HurtboxComponent(Rectangle bounds) {
		super();
		this.bounds = bounds;
	}


	public Rectangle getBounds() {
		return bounds;
	}


	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}



	
	
	public Object clone(){
		return new HurtboxComponent(this);
	}
}
