package GUI.controls;

import graphics.TextureFont;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import GUI.graphics.TextAreaRenderer;

import math.Vector2;
import utils.Rectangle;

public class TextArea extends GUITextBase{
	private static final TextAreaRenderer DEFAULT_RENDERER = new TextAreaRenderer();
	private static final int SCROLLBAR_SIZE = 15;
	private int margin = 20;
		
	private ScrollBar vBar;
	private List<String> lines;
	
	public TextArea() {
		this.vBar = new ScrollBar();
		this.addChild(vBar);
		this.lines = new ArrayList<>();
	}
	
	@Override
	public void onResized(Rectangle bounds) {
		super.onResized(bounds);
		updateControl();
		
		vBar.setBounds(bounds.Width - SCROLLBAR_SIZE, 0,SCROLLBAR_SIZE, bounds.Height);
		this.setRenderer(DEFAULT_RENDERER);
	}
	
	public ImmutableList<String> getLines() {
		return ImmutableList.copyOf(this.lines);
	}
	
		
	@Override
	public void setFont(TextureFont font) {
		super.setFont(font);
		this.updateControl();
	}
	
	public void setMargin(int margin) {
		this.margin = margin;
	}
	
	@Override
	public void onTextChanged() {
		super.onTextChanged();
		updateControl();
	}

	private void updateControl() {
		updateLines();
		updateScrollBar();
	}

	private void updateScrollBar() {
		if(this.getFont() == null)
			return;
		
		int maxScroll = (int) (((this.lines.size() * this.getFont().getLineSpacing()) - this.getBounds().Height + this.getMargin()));
		this.vBar.setScrollMax(maxScroll);
	}

	private void updateLines() {
		this.lines.clear();	
		int maxWidth = this.getBounds().Width - SCROLLBAR_SIZE - margin * 2;
		TextureFont font = this.getFont();
		StringBuilder text = new StringBuilder(this.getText());
		String line = "";
		while(text.length() > 0) {
			line = "";
			while(text.length() > 0) {
				char c = text.charAt(0);
				
				if(c == '\n') {
					text.deleteCharAt(0);
					this.lines.add(line);
					break;
				} else {	
					String newString = line + c;
					
					Vector2 meassure = font.meassureString(newString);
					if(meassure.X > maxWidth) {
						this.lines.add(line);
						break;
					}			
	
					text.deleteCharAt(0);
					line = newString;
				}
			}
		}	
		this.lines.add(line);
	}

	public int getMargin() {
		return this.margin;
	}
	
	public void setScrollOffset(int value) {
		this.vBar.setScrollValue(value);
	}
	
	public int getScrollOffset() {
		return vBar.getScrollValue();
	}
	
	public int getMaxScrollOffset() {
		return vBar.getScrollMax();
	}

	public int getScrollBarSize() {
		return SCROLLBAR_SIZE;
	}

}
