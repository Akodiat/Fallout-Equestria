package components;

import entityFramework.IComponent;
import graphics.Color;
import graphics.Texture2D;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class RenderingComponent implements IComponent {
	
	private Texture2D 	texture;
	private Color		color;
	
	public RenderingComponent(){
		//TODO: Add default values to the default constructor.
	}
	
	public RenderingComponent(Texture2D texture, Color color){
		this.texture = texture;
		this.color = color;
	}
	
	private RenderingComponent(RenderingComponent rendComp){
		this.texture = rendComp.texture;
		this.color = rendComp.color;
	}
	
	public Object clone(){
		return new RenderingComponent(this);
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the texture
	 */
	public Texture2D getTexture() {
		return texture;
	}

	/**
	 * @param texture the texture to set
	 */
	public void setTexture(Texture2D texture) {
		this.texture = texture;
	}

}
