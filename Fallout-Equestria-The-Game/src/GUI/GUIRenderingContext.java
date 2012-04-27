package GUI;

import graphics.Color;
import graphics.RenderTarget2D;
import graphics.SpriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import math.Matrix4;

import utils.GameTime;
import utils.Rectangle;

public class GUIRenderingContext {

	private SpriteBatch spriteBatch;
	
	public GUIRenderingContext(SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
		this.renderers = new HashMap<>();
	}
	
	Map<Class<? extends GUIControl>, IGUIRenderer<? extends GUIControl>> renderers;	
	public void addRenderer(IGUIRenderer<? extends GUIControl> renderer) {
		this.renderers.put(renderer.getRenderedType(), renderer);
	}
	
	public  void render(GUIControl control, GameTime time) {
		RenderTarget2D target = renderChildrenRecursivly(control, time);	
		
		this.spriteBatch.begin();
		this.spriteBatch.draw(target.getTexture(), control.getBounds(), Color.White, null);
		this.spriteBatch.end();
		
		target.destroy();
	}
	
	public RenderTarget2D renderChildrenRecursivly(GUIControl control, GameTime time) {
		List<GUIControl> children = control.getChildren();
		if(children.isEmpty()) {
			RenderTarget2D target = new RenderTarget2D(control.getBounds().Width, control.getBounds().Height);
			this.spriteBatch.begin(null, Matrix4.Identity, target);
			this.renderInternal(control, time);
			this.spriteBatch.end();
			return target;
		} else {
			RenderTarget2D[] childTargets = new RenderTarget2D[children.size()];			
			for (int i = 0; i < childTargets.length; i++) {
				childTargets[i] = this.renderChildrenRecursivly(children.get(i), time);
 			}
			
			RenderTarget2D target = new RenderTarget2D(control.getBounds().Width, control.getBounds().Height); 
			this.spriteBatch.begin(null, Matrix4.Identity, target);
			this.renderInternal(control, time);
			
			for (int i = 0; i < control.getChildren().size(); i++) {
				GUIControl child = control.getChildren().get(i);
				RenderTarget2D childTarget = childTargets[i];
				this.spriteBatch.draw(childTarget.getTexture(), child.getBounds(), Color.White, null);
		
			}
			this.spriteBatch.end();
			
			for (RenderTarget2D renderTarget2D : childTargets) {
				renderTarget2D.destroy();
			}
			
			return target;
		}
	}
			
	private  <T extends GUIControl> void renderInternal(T control, GameTime time) {
		@SuppressWarnings("unchecked")
		IGUIRenderer<T> renderer = (IGUIRenderer<T>) this.renderers.get(control.getClass());		
		if(renderer == null) {
			throw new UnsupportedOperationException("The guid control type is not valid");
		}
		
		renderer.render(spriteBatch, time, control);	
	}
}
