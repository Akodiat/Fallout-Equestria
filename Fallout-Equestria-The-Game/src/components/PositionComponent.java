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
	
	public PositionComponent(){
		this.position = new Vector2(0,0); //TODO: Change to center of screen?
	}
	
	public PositionComponent(Vector2 position){
		this.position = position;
	}
	
	private PositionComponent(PositionComponent posComp){
		this.position = posComp.position;
	}
	public Object clone(){
		return new PositionComponent(this);
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
