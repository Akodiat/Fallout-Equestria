package components;


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
	private ShaderEffect effect;
	
	
	public RenderingComponent(){
		this.color   = Color.White;
		this.texture = null;
		this.effect  = null; 
	}
	
	public RenderingComponent(Texture2D texture, Color color, ShaderEffect effect){
		this.texture = texture;
		this.color = color;
		this.effect = effect;
	}
	
	private RenderingComponent(RenderingComponent rendComp){
		this.texture = rendComp.texture;
		this.effect = rendComp.effect;
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

	public ShaderEffect getEffect() {
		return effect;
	}

	public void setEffect(ShaderEffect effect) {
		this.effect = effect;
	}
	
	public String toString() {
		return "Rendering Comp: \n" 
			+  "Texture: " + this.texture.toString() + "\n"
			+  "Color: " + this.color.toString() + "\n" 
			+  "Shader Effect: " + this.effect.toString();
		
	}

}
