package components;

import math.Vector2;
import entityFramework.IComponent;

public class positionComponent implements IComponent{
	private Vector2 position;
	
	public Object clone(){
		return null;
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
