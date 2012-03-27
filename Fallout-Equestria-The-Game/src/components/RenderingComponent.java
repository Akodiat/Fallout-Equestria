package components;


import utils.Rectangle;
import entityFramework.IComponent;
import graphics.Color;
import graphics.ShaderEffect;
import graphics.Texture2D;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * 
 * @author Joakim Johansson
 *
 */
@XStreamAlias("Rendering")
public class RenderingComponent implements IComponent {
	
	private Texture2D 	 texture;
	private Color		 color;
	private ShaderEffect effect;
	private Rectangle 	 source;
	private Boolean 	 mirrored;

	public RenderingComponent(){
		this.color   = Color.White;
		this.texture = null;
		this.effect  = null; 
		this.source = null;
		this.mirrored = false;
	}
	
	public RenderingComponent(Texture2D texture, Color color, ShaderEffect effect, Rectangle source, Boolean mirrored){
		this.texture = texture;
		this.color = color;
		this.effect = effect;
		this.source = source;
		this.mirrored = mirrored;
	}
	
	private RenderingComponent(RenderingComponent rendComp){
		this.texture = rendComp.texture;
		this.effect = rendComp.effect;
		this.color = rendComp.color;
		this.source = rendComp.source;
		this.mirrored = rendComp.mirrored;
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

	public Rectangle getSource() {
		return source;
	}

	public void setSource(Rectangle source) {
		this.source = source;
	}

	public void setEffect(ShaderEffect effect) {
		this.effect = effect;
	}
	
	public Boolean getMirrored() {
		return mirrored;
	}

	public void setMirrored(Boolean mirrored) {
		this.mirrored = mirrored;
	}
	
	public String toString() {
		return "Rendering Comp: \n" 
			+  "Texture: " + this.texture.toString() + "\n"
			+  "Color: " + this.color.toString() + "\n" 
			+  "Shader Effect: " + this.effect.toString();
		
	}

}
