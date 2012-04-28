package GUI;

import java.util.List;

import graphics.Texture2D;
import content.ContentManager;
import utils.Rectangle;

public abstract class IGUIFactory {
	protected GUIRenderingContext context;
	
	public abstract void initialize(ContentManager manager);
	
	public abstract Button createButton(Rectangle bounds, String text);
	public abstract CheckBox createCheckBox(Rectangle bounds, boolean checked);
	public abstract ToggleButton createToggleButton(Rectangle bounds, boolean checked);
	public abstract ComboBox createComboBox(Rectangle bound, List<Object> items);
	public abstract Label createLabel(Rectangle bounds, String text);
	public abstract TextField createTextfield(Rectangle bounds, String initialText);
	public abstract ImageBox createImageBox(Rectangle bounds, Texture2D texture);
	public abstract ScrollBar createScrollBar(Rectangle bounds, boolean isVertical);
	public abstract PanelList createPanelList(Rectangle bounds);
	public abstract Slider createSlider(Rectangle bounds, int maxValue);
	public abstract Panel createPanel(Rectangle bounds);
	public abstract ScrollPanel createScrollPanel(Rectangle bounds);
	
}