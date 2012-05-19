package GUI.graphics;

import math.Vector2;
import GUI.controls.AnimationBox;
import graphics.Color;
import graphics.RenderTarget2D;
import graphics.SpriteBatch;
import utils.GameTime;

public class AnimationBoxRenderer implements IGUIRenderer<AnimationBox>{
	
	@Override
	public Class<AnimationBox> getRenderedType() {
		return AnimationBox.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, AnimationBox control, LookAndFeel lookAndFeel, RenderTarget2D target) {
		control.getAnimationPlayer().update(time);
		control.getAnimationPlayer().draw(batch, control.getDimention().getCenter(), false, 0, Color.White, new Vector2(3,3));
	}
}
