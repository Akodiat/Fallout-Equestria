package GUI.controls;

import java.util.ArrayList;
import java.util.List;

import GUI.IItemFormater;
import GUI.graphics.ListBoxRenderer;

import com.google.common.collect.ImmutableList;

import math.MathHelper;
import math.Point2;
import math.Vector2;
import graphics.Color;
import graphics.TextureFont;
import utils.Mouse;
import utils.MouseButton;
import utils.Rectangle;

public class ListBox<T> extends GUISelector<T> {
	private static final ListBoxRenderer DEFAULT_RENDERER = new ListBoxRenderer();
	private final int margin = 10;
	private static final int SCROLLBAR_SIZE = 16;
	private final ScrollBar vBar;
	private final ScrollBar hBar;
	private TextureFont font;
	private int itemPadding;
	private IItemFormater<T> itemFormater;
	private Rectangle listBounds;
	private Color selectedItemColor;
	
	
	public ListBox() {
		this.vBar = new ScrollBar();
		this.hBar = new ScrollBar();
		this.hBar.setHorizontal(true);
		this.vBar.setEnabled(false);
		this.vBar.setVisible(false);
		
		this.hBar.setEnabled(false);
		this.hBar.setVisible(false);
		this.setBgColor(new Color(0,0,0,0));
		
		this.addChild(hBar);
		this.addChild(vBar);
		
		
		this.setFont(null);
		this.itemPadding = 0;
		this.itemFormater = null;
		this.listBounds = Rectangle.Empty;
		this.selectedItemColor = Color.Blue;
		
		this.setRenderer(DEFAULT_RENDERER);
	}
	
	
	@Override
	public void setBounds(Rectangle rect) {
		super.setBounds(rect);
		this.resizeScrollbars();	
	}

	private void resizeScrollbars() {	
		Rectangle b = this.getBounds();
		vBar.setBounds(b.Width - SCROLLBAR_SIZE, this.margin, SCROLLBAR_SIZE, b.Height - this.margin * 2);
		hBar.setBounds(0, b.Height - SCROLLBAR_SIZE, b.Width, SCROLLBAR_SIZE);				
	}
	
	public int getMargin() {
		return margin;
	}

	public Point2 getViewOffset() {
		return new Point2(this.hBar.getScrollValue(), this.vBar.getScrollValue());
	}
	
	public TextureFont getFont() {
		return font;
	}

	public void setFont(TextureFont font) {
		this.font = font;
	}
	
	public int getItemPadding() {
		return itemPadding;
	}

	public void setItemPadding(int itemPadding) {
		this.itemPadding = itemPadding;
	}
	
	public void setItemFormater(IItemFormater<T> itemFormater) {
		this.itemFormater = itemFormater;
	}
	public IItemFormater<T> getItemFormater() {
		return this.itemFormater;
	}
	
	public ImmutableList<String> getItemsAsStrings() {
		List<String> itemStrings = new ArrayList<>(this.itemCount());
		for (T item : this.getItems()) {
			itemStrings.add(this.getFormatedItem(item));
		}
		return ImmutableList.copyOf(itemStrings);
	}
	
	@Override
	public void onItemAdded(T item) {
		super.onItemAdded(item);
		Point2 itemDimentions = this.meassureItem(item);
		int w = MathHelper.max(itemDimentions.X, this.listBounds.Width);		
		int h = itemDimentions.Y + this.listBounds.Height;
		this.listBounds = new Rectangle(0,0, w,h);
		
		this.updateScrollBars();
	}
	@Override
	public void onItemRemoved(T item) {
		super.onItemAdded(item);
		Point2 itemDimentions = this.meassureItem(item);
		int w = this.remeassureWidth();
		int h = this.listBounds.Height - itemDimentions.Y;
		this.listBounds = new Rectangle(0,0, w,h);
		
		this.updateScrollBars();
	}
	
	private int remeassureWidth() {
		if(this.itemCount() > 0) {
			List<T> items = this.getItems();
			int max = this.meassureItem(items.get(0)).X;
			for (int i = 0; i < this.getItems().size(); i++) {
				max = MathHelper.max(max, this.meassureItem(items.get(i)).X);
			}
		}
		return 0;
	}

	private void updateScrollBars() {
		Rectangle lB = this.listBounds;
		Rectangle sB = this.getBounds();
		this.updateScrollbar(lB.Width, sB.Width, this.hBar);
		this.updateScrollbar(lB.Height, sB.Height, this.vBar);	
	}

	private void updateScrollbar(int listSize, int boundsSize, ScrollBar bar) {
		int maxScroll = listSize - boundsSize + margin * 2;
		if(maxScroll > 0) {
			bar.setEnabled(true);
			bar.setVisible(true);
			bar.setScrollMax(maxScroll + SCROLLBAR_SIZE);
			bar.setScrollValue(0);
			
		} else {
			if(bar.isEnabled()) {
				bar.setEnabled(false);
				bar.setVisible(false);
				bar.setScrollMax(0);
				bar.setScrollValue(0);
			}
		}
	}

	private Point2 meassureItem(T item) {
		String itemText = getFormatedItem(item);
		if(itemText.contains("\n")) {
			itemText = itemText.substring(0, itemText.indexOf("\n"));
		}
		
		Vector2 fontMeassure = this.font.meassureString(itemText);
		Point2 dim = new Point2((int)fontMeassure.X, (int)(fontMeassure.Y + this.itemPadding));
		return dim;
		
	}

	private String getFormatedItem(T item) {
		String itemText;
		if(this.itemFormater != null) {
			itemText = this.itemFormater.formatItem(item);
		} else {
			itemText = item.toString();
		}
		return itemText;
	}

	@Override
	public void onMouseClick(Mouse mouse, MouseButton button) {
		super.onMouseClick(mouse, button);
		if(button == MouseButton.Left) {
			if(wasItemClick(mouse)) {
			
				determineItemSelected(mouse);
			}
		}
	}

	private boolean wasItemClick(Mouse mouse) {
		Rectangle b = this.getAbsolueBounds();
		int x = b.X,y = b.Y,w = b.Width,h = b.Height;
		if(vBar.isEnabled()) {
			w -= vBar.getDimention().Width;
		}
		if(hBar.isEnabled()) {
			h -= hBar.getDimention().Height;
		}
		
		b = new Rectangle(x,y,w,h);
		
		return b.intersects(mouse.getMouseState().ViewCoords);
	}

	private void determineItemSelected(Mouse mouse) {
		Rectangle bounds = this.getAbsolueBounds();
		float relativeY = mouse.getMouseState().ViewCoords.Y - bounds.Y;		
		float itemHeight = this.font.getLineSpacing() + this.itemPadding;
		float scrollOffset = this.vBar.getScrollValue();
		
		int itemIndex = (int)((relativeY + scrollOffset) / itemHeight);
		itemIndex = MathHelper.clamp(-1, this.itemCount() - 1 , itemIndex);
		
		if(itemIndex != -1) {
			this.setSelectedIndex(itemIndex);
		}
	}

	public Color getSelectedItemColor() {
		return selectedItemColor;
	}

	public void setSelectedItemColor(Color selectedItemColor) {
		this.selectedItemColor = selectedItemColor;
	}
}
