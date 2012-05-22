package graphics;

import java.util.ArrayList;
import java.util.List;

import math.Matrix4;
import math.Vector2;

import utils.Rectangle;


public class PostProcessingManager {
	private RenderTarget2D temporaryTarget;
	private final List<ShaderEffect> postProcessingEffects;
	private final SpriteBatch spriteBatch;
	
	public PostProcessingManager(SpriteBatch spriteBatch) {
		super();
		this.postProcessingEffects = new ArrayList<>();
		this.spriteBatch = spriteBatch;
	}

	public void pushPostProcessingEffect(ShaderEffect processingEffect) {
		postProcessingEffects.add(processingEffect);
	}
	
	public void clearPostProcessingBuffer() {
		this.clearPostEffects();
	}
	
	/**Applies the buffered PostProcessingEffects to the target.
	 * 
	 * @param target
	 */
	public void applyEffectsToTarget(RenderTarget2D target) {
		if(this.temporaryTarget == null) {
			createNewTmpTarget(target);
		} else if(!target.getTexture().getBounds().equals(this.temporaryTarget.getTexture().getBounds())) {
			this.temporaryTarget.destroy();
			this.temporaryTarget = null;
			createNewTmpTarget(target);
		}
		
		renderPostEffects(target);
	}

	private void clearPostEffects() {
		this.postProcessingEffects.clear();
	}

	private void renderPostEffects(RenderTarget2D target) {
		RenderTarget2D readTarget = target;
		RenderTarget2D writeTarget = this.temporaryTarget;
		
		for (ShaderEffect postEffect : this.postProcessingEffects) {
			spriteBatch.begin(postEffect, Matrix4.Identity, writeTarget);
			
			postEffect.bindShaderProgram();			
			postEffect.apply(); 
			postEffect.unbindShaderProgram();
			
			spriteBatch.clearScreen(Color.Transparent);
			spriteBatch.draw(readTarget.getTexture(), Vector2.Zero, Color.White);			
			spriteBatch.end();
			
			if(readTarget == target) {
				readTarget = writeTarget;
				writeTarget = target;
			} else {
				readTarget = writeTarget;
				writeTarget = this.temporaryTarget;
			}
		}
		
		spriteBatch.begin(null, Matrix4.Identity, target);
	//	spriteBatch.clearScreen(Color.Transparent);
		spriteBatch.draw(readTarget.getTexture(), Vector2.Zero, Color.White);			
		spriteBatch.end();
	}

	private void createNewTmpTarget(RenderTarget2D target) {
		Rectangle bounds = target.getTexture().getBounds();
		this.temporaryTarget = new RenderTarget2D(bounds.Width, bounds.Height);
	}
}
