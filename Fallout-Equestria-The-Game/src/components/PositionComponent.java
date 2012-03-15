package components;

import math.Vector2;
import entityFramework.IComponent;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class PositionComponent implements IComponent{
	private Vector2 position;
	
	public Object clone(){
		PositionComponent comp = new PositionComponent();
		comp.setPosition(position);
		return comp;
	}

	/**
	 * @return the position
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public void setPosition(int x, int y) {
		this.position = new Vector2(x,y);
	}

}