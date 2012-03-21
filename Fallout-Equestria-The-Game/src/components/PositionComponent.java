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
	private float rotation;
	
	public PositionComponent(){
		this.position = new Vector2(0,0); //TODO: Change to center of screen?
		this.rotation = 0;
	}
	
	public PositionComponent(Vector2 position){
		this.position = position;	
		this.rotation = 0;
	}
	public PositionComponent(Vector2 position, float rotation){
		this.position = position;
		this.rotation = rotation;
	}
	
	private PositionComponent(PositionComponent posComp){
		this.position = posComp.getPosition();
		this.rotation = posComp.getRotation();
	}
	public Object clone(){
		return new PositionComponent(this);
	}

	/**
	 * @return the position
	 */
	public Vector2 getPosition() {
		return new Vector2(position.X,position.Y);
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

	/**
	 * @return the rotation
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * @param rotation the rotation to set
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

}
