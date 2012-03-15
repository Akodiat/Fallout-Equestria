package components;

import org.lwjgl.util.Color;

import entityFramework.IComponent;
import graphics.Texture2D;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class RenderingComponent implements IComponent {
	
	private Texture2D 	texture;
	private Color		color;
	
	public Object clone(){
		RenderingComponent comp = new RenderingComponent();
		comp.setColor(color);
		comp.setTexture(texture);
		return comp;
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
