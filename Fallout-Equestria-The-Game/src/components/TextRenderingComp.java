package components;

import anotations.Editable;
import anotations.EditableComponent;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import entityFramework.IComponent;
import graphics.Color;
import graphics.TextureFont;

/**
 * 
 * @author Lukas Kurtyan
 *
 */
@XStreamAlias("Text")
@EditableComponent
public class TextRenderingComp implements IComponent {

	@Editable
	private String text;
	@Editable
	private TextureFont font;
	@Editable
	private Color color;
	
	public TextRenderingComp() {
		this("", null, Color.White); 
	}
	
	public TextRenderingComp(String text, TextureFont font, Color color) {
		this.text = text;
		this.font = font;
		this.setColor(color);
	}
	
	public TextRenderingComp(TextRenderingComp textRenderingComp) {
		this.text = textRenderingComp.text;
		this.font = textRenderingComp.font;
	}

	public Object clone() {
		return new TextRenderingComp(this);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TextureFont getFont() {
		return font;
	}

	public void setFont(TextureFont font) {
		this.font = font;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String toString() {
		return "TextRenderComponent Comp \n" 
			+  this.text;
	}

}
