package components;


import utils.Rectangle;
import entityFramework.IComponent;
import graphics.Color;
import graphics.ShaderEffect;
import graphics.Texture2D;
import anotations.Editable;
import anotations.EditableComponent;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * 
 * @author Joakim Johansson
 *
 */
@XStreamAlias("Rendering")
@EditableComponent
public class RenderingComp implements IComponent {
	
	@Editable
	private Texture2D 	 texture;
	@Editable
	private Color		 color;
	@Editable
	private Rectangle 	 source;
	private ShaderEffect effect;
	
	public RenderingComp(){
		this.color   = Color.White;
		this.texture = null;
		this.effect  = null; 
		this.source  = null;
	}
	
	public RenderingComp(Texture2D texture, Color color, ShaderEffect effect, Rectangle source){
		this.texture = texture;
		this.color = color;
		this.effect = effect;
		this.source = source;
	}
	
	private RenderingComp(RenderingComp rendComp){
		this.texture = rendComp.texture;
		this.effect = rendComp.effect;
		this.color = rendComp.color;
		this.source = rendComp.source;
	}
	
	public Object clone(){
		return new RenderingComp(this);
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
	
	public String toString() {
		String s = "Rendering Comp: \n";
		s += "Texture: ";
		if(this.texture != null) {
			s += this.texture.toString();
		} else {
			s += "None";
		}
		
		s += "\nColor: " + this.color.toString() + "\n";
		s += "Shader Effect: ";
		
		if(this.effect != null) {
			s += this.effect.toString();
		} else {
			s+= "Node";
		}
		s += "\n";
		
		return s;
	}

}
