package GUI.graphics;

import graphics.Color;
import graphics.RenderTarget2D;
import graphics.ShaderEffect;
import graphics.SpriteBatch;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import GUI.controls.GUIControl;
import math.Matrix4;
import utils.GameTime;

public class GUIRenderingContext {

	private SpriteBatch spriteBatch;
	private ShaderEffect disabledEffect;
	private LookAndFeel lookAndFeel;
	
	public GUIRenderingContext(SpriteBatch spriteBatch, LookAndFeel lookAndFeel, ShaderEffect disabledEffect) {
		this.spriteBatch = spriteBatch;
		this.lookAndFeel = lookAndFeel;
		this.disabledEffect   = disabledEffect;
		
	}
	
	public  void render(GUIControl control, GameTime time) {
		RenderTarget2D target = renderChildrenRecursivly(control, time);	

		
		this.spriteBatch.begin();
		this.spriteBatch.draw(target.getTexture(), control.getBounds(), Color.White, null);
		this.spriteBatch.end();
		
	}
	
	public RenderTarget2D renderChildrenRecursivly(GUIControl control, GameTime time) {
		if(!control.isVisible())
			return null;
		
		List<GUIControl> children = control.getChildren();
		if(children.isEmpty()) {
			return renderControl(control, time);
		} else {
			//Sorts the children depending on their draworder.
			Collections.sort(children, new DrawOrderComparator());			
			RenderTarget2D[] childTargets = new RenderTarget2D[children.size()];			
			for (int i = 0; i < childTargets.length; i++) {
				childTargets[i] = this.renderChildrenRecursivly(children.get(i), time);
 			}
			
			RenderTarget2D target = renderChildrenToTarget(control, time,
															childTargets);
	
			return target;
		}
	}

	private RenderTarget2D renderChildrenToTarget(GUIControl control,
			GameTime time, RenderTarget2D[] childTargets) {
		RenderTarget2D target = control.getRenderTarget();

		this.spriteBatch.begin(null, Matrix4.Identity, target);
		this.spriteBatch.clearScreen(Color.Transparent);
		this.renderInternal(control, time, target);
		
		for (int i = 0; i < control.getChildren().size(); i++) {
			GUIControl child = control.getChildren().get(i);
			if(child.isVisible()) {
				RenderTarget2D childTarget = childTargets[i];
				this.spriteBatch.draw(childTarget.getTexture(), child.getBounds(), Color.White, null);
			}
		}

		this.spriteBatch.end();
		return target;
	}

	private RenderTarget2D renderControl(GUIControl control, GameTime time) {
		RenderTarget2D target = control.getRenderTarget();
		if(!control.isEnabled()) {
			this.spriteBatch.begin(disabledEffect, Matrix4.Identity, target);
			this.spriteBatch.clearScreen(Color.Transparent);
			this.renderInternal(control, time, target);
			this.spriteBatch.end();
		} else {
			this.spriteBatch.begin(null, Matrix4.Identity, target);
			this.spriteBatch.clearScreen(Color.Transparent);
			this.renderInternal(control, time, target);
			this.spriteBatch.end();
		}
		return target;
	}			
	
	private  <T extends GUIControl> void renderInternal(T control, GameTime time, RenderTarget2D target) {	
		@SuppressWarnings("unchecked")
		IGUIRenderer<T> renderer = (IGUIRenderer<T>)control.getRenderer();
		renderer.render(spriteBatch, time, control, lookAndFeel, target);	
	}


	private class DrawOrderComparator implements Comparator<GUIControl> {
		@Override
		public int compare(GUIControl control0, GUIControl control1) {
			return Integer.compare(control0.getRenderOrder(), control1.getRenderOrder());
		}
	}


}
