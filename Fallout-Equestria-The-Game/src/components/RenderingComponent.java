package components;

import math.Vector2;
import entityFramework.IComponent;
import graphics.Color;
import graphics.ShaderEffect;
import graphics.Texture2D;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class RenderingComponent implements IComponent {
	
	private Texture2D 	 texture;
	private Color		 color;
	private Vector2 	 origin;
	private ShaderEffect effect;
	
	
	public RenderingComponent(){
		this.color = Color.White;
		this.origin = Vector2.Zero;
	}
	
	public RenderingComponent(Texture2D texture, Color color, Vector2 origin, ShaderEffect effect){
		this.texture = texture;
		this.color = color;
		this.origin = origin;
		this.effect = effect;
	}
	
	private RenderingComponent(RenderingComponent rendComp){
		this.texture = rendComp.texture;
		this.color = rendComp.color;
		this.origin = rendComp.origin;
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
	/**
	 * @return the origin
	 */
	public Vector2 getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(Vector2 origin) {
		this.origin = origin;
	}

	public ShaderEffect getEffect() {
		return effect;
	}

	public void setEffect(ShaderEffect effect) {
		this.effect = effect;
	}

}
