package components;

import java.util.HashMap;
import java.util.Map;

import utils.Rectangle;
import anotations.Editable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import entityFramework.IComponent;
import graphics.Color;
import graphics.Texture2D;
import graphics.TextureFont;

@XStreamAlias("GUI")
@Editable
@SuppressWarnings("serial")
public class GUIComp implements IComponent {
	private Map<String, GUIComp> children;
	
	private @Editable Texture2D background;
	private @Editable Texture2D middleground;
	private @Editable Texture2D foreground;
	
	private @Editable Color bgColor;
	private @Editable Color mgColor;
	private @Editable Color fgColor;


	private @Editable String text;
	private @Editable TextureFont textFont;
	private @Editable Color textColor;
	private @Editable Rectangle position;
	
	public GUIComp() {
		this(null, null, null, Color.White, Color.White, Color.White,
				               null, null, "", null, Color.White);
	}
	
	
	public GUIComp(GUIComp toBeCloned) {
		this.background = toBeCloned.background;
		this.middleground = toBeCloned.middleground;
		this.foreground = toBeCloned.foreground;
		this.bgColor = toBeCloned.bgColor;
		this.mgColor = toBeCloned.mgColor;
		this.fgColor = toBeCloned.fgColor;
		this.position = toBeCloned.position;
		this.text = toBeCloned.text;
		this.textFont = toBeCloned.textFont;
		this.textColor = toBeCloned.textColor;
		this.children = new HashMap<>();
		for (String key : toBeCloned.children.keySet()) {
			GUIComp comp = (GUIComp)toBeCloned.children.get(key).clone();
			this.children.put(text, comp);
		}
	}
	
	public GUIComp(Texture2D background, Texture2D middleground,
			Texture2D foreground, Color bgColor, Color mgColor, Color fgColor,
			Texture2D border, Rectangle position, String text,
			TextureFont textFont, Color textColor) {
		super();
		this.background = background;
		this.middleground = middleground;
		this.foreground = foreground;
		this.bgColor = bgColor;
		this.mgColor = mgColor;
		this.fgColor = fgColor;
		this.position = position;
		this.text = text;
		this.textFont = textFont;
		this.textColor = textColor;
		this.children = new HashMap<>();
	}

	public Object clone(){
		return new GUIComp(this);
	}
	
	public Texture2D getBackground() {
		return background;
	}

	public void setBackground(Texture2D background) {
		this.background = background;
	}

	public Texture2D getMiddleground() {
		return middleground;
	}

	public void setMiddleground(Texture2D middleground) {
		this.middleground = middleground;
	}

	public Texture2D getForeground() {
		return foreground;
	}

	public void setForeground(Texture2D foreground) {
		this.foreground = foreground;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public Color getMgColor() {
		return mgColor;
	}

	public void setMgColor(Color mgColor) {
		this.mgColor = mgColor;
	}

	public Color getFgColor() {
		return fgColor;
	}

	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
	}

	public Rectangle getPosition() {
		return position;
	}

	public void setPosition(Rectangle position) {
		this.position = position;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TextureFont getTextFont() {
		return textFont;
	}

	public void setTextFont(TextureFont textFont) {
		this.textFont = textFont;
	}
	
	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
	
	public void setTextureColors(Color color) {
		this.bgColor = color;
		this.mgColor = color;
		this.fgColor = color;
	}
}
