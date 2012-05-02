package GUI.controls;

import java.util.HashMap;
import java.util.Map;

import GUI.IScrollable;
import GUI.ResizedEventArgs;
import GUI.ScrollEventArgs;
import GUI.graphics.PanelRenderer;

import math.MathHelper;
import math.Point2;
import misc.IEventListener;
import utils.Rectangle;

public class ScrollPanel extends Panel implements IScrollable {
	private static final PanelRenderer DEFAULT_RENDERER = new PanelRenderer();
	
	private static final int SCROLLBAR_SIZE = 15;
	//Make sure the scroll bars are rendered last over everything else.
	private static final int SCROLLBAR_RENDER_ORDER = Integer.MAX_VALUE;
	
	private Rectangle innerBounds;
	private IEventListener<ResizedEventArgs> resizedListener;	
	private IEventListener<ScrollEventArgs> 	 scrollListener;
	private ScrollBar vBar;
	private ScrollBar hBar;
	
	private Map<GUIControl, Point2> initialLocations;
	
	
	public ScrollPanel() {
		this.vBar = new ScrollBar();
		this.hBar = new ScrollBar();
		this.vBar.setRenderOrder(SCROLLBAR_RENDER_ORDER);
		this.hBar.setRenderOrder(SCROLLBAR_RENDER_ORDER);
		this.hBar.setHorizontal(true);
		
		this.innerBounds = Rectangle.Empty;
		this.resizedListener = new IEventListener<ResizedEventArgs>() {
			@Override
			public void onEvent(Object sender, ResizedEventArgs e) {
				fixBoundsFromChild(e.getBounds());
			}
		};
		this.scrollListener = new IEventListener<ScrollEventArgs>() {
			@Override
			public void onEvent(Object sender, ScrollEventArgs e) {
				onScrolled();
			}
		};	
			
		this.initialLocations = new HashMap<GUIControl, Point2>();
		this.addChild(this.vBar);
		this.addChild(this.hBar);		
		

		this.hBar.addScrollListener(scrollListener);
		this.vBar.addScrollListener(scrollListener);
		
		this.setRenderer(DEFAULT_RENDERER);
	}	
	public Point2 getViewOffset() {
		return new Point2(this.hBar.getScrollValue(), this.vBar.getScrollValue());
	}		
	protected void onScrolled() {
		for (GUIControl child : this.getChildren()) {
			if(child == this.vBar || child == this.hBar)
				continue;
			
			Point2 initialPos = this.initialLocations.get(child);
			Point2 correctedPos = new Point2(initialPos.X - this.hBar.getScrollValue()
										   ,initialPos.Y - this.vBar.getScrollValue());
			child.setPosition(correctedPos);
		}
	}
		
	@Override
	protected void onResized(Rectangle bounds) {
		super.onResized(bounds);
		vBar.setBounds(bounds.Width - SCROLLBAR_SIZE, 0, SCROLLBAR_SIZE, bounds.Height);
		hBar.setBounds(0, bounds.Height - SCROLLBAR_SIZE, bounds.Width, SCROLLBAR_SIZE);		
		
		if(innerBounds.Width < bounds.Width + SCROLLBAR_SIZE||
		   innerBounds.Height < bounds.Height + SCROLLBAR_SIZE) {
			updateInnerBounds(bounds.Width + SCROLLBAR_SIZE, 
							  bounds.Height + SCROLLBAR_SIZE);
		} 
		
		updateScrollbars();
	}	
	@Override
	protected void onChildAdded(GUIControl child) {
		super.onChildAdded(child);
		this.initialLocations.put(child, child.getPosition());
		fixBoundsFromChild(child.getBounds());
		child.addResizedListener(this.resizedListener);
	}
	
	@Override 
	protected void onChildRemoved(GUIControl child) {
		super.onChildRemoved(child);
		this.initialLocations.remove(child);
		child.removeResizedListener(this.resizedListener);
		fixChildRemovedBounds();
	}
	private void fixChildRemovedBounds() {
		int width = 0, height = 0;
		for (GUIControl child : this.getChildren()) {
			width = MathHelper.max(width, child.getBounds().getRight());
			height = MathHelper.max(height, child.getBounds().getBottom());
		}
		this.updateInnerBounds(width, height);
	}
	private void fixBoundsFromChild(Rectangle childBounds) {
		if(this.innerBounds.getRight() < childBounds.getRight()) {
			updateInnerBounds(childBounds.getRight(), this.innerBounds.Height);
		} 
		if(this.innerBounds.getBottom() < childBounds.getBottom())  {
			updateInnerBounds(this.innerBounds.Width, childBounds.getBottom());
		}
	}
	private void updateInnerBounds(int width, int height) {
		   innerBounds = new Rectangle(0,0, width,
				   							height);
		   updateScrollbars();		   
	}
	private void updateScrollbars() {
		this.updateScrollbar(this.getBounds().Height, this.innerBounds.Height, vBar);
		this.updateScrollbar(this.getBounds().Width, this.innerBounds.Width, hBar);
	}
	private void updateScrollbar(int outerBoundSize, int innerBoundsSize, ScrollBar bar) {
		int maxScroll = innerBoundsSize - outerBoundSize;
		if(maxScroll > SCROLLBAR_SIZE) {
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
}
