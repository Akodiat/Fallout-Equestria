package GUI;


import graphics.TextureFont;

public class Label extends GUIControl {
	private static final LabelRenderer DEFATULT_RENDERER = new LabelRenderer();
	
	
	private String text;
	private TextureFont font;
	
	public Label() {
		super();
		this.setRenderer(DEFATULT_RENDERER);
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
	
}
