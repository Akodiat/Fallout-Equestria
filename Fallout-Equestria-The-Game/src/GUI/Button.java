package GUI;

import content.ContentManager;
import sounds.SoundManager;
import utils.input.Mouse;
import utils.input.MouseButton;

public class Button extends ButtonBase {
	private static ButtonRenderer DEFAULT_RENDERER = new ButtonRenderer();
	private ContentManager cm = new ContentManager("resources");
	private SoundManager sm = new SoundManager(cm, 1.0f, 1.0f, 2.0f);
	
	public Button() {
		this.setMouseHover(false);
		this.setPressed(false);
		this.setRenderer(DEFAULT_RENDERER);
	}
	
	@Override
	protected void onMouseEnter(Mouse mouse) {
		super.onMouseEnter(mouse);
		this.setMouseHover(true);
	}
	
	@Override
	protected void onMouseExit(Mouse mouse) {
		super.onMouseExit(mouse);
		this.setMouseHover(false);
	}
	
	@Override
	protected void onMouseDown(Mouse mouse, MouseButton button) {
		super.onMouseDown(mouse, button);
		this.setPressed(true);
		sm.playSoundEffect("effects/buttonsound.ogg");
	}
	

	@Override
	protected void onMouseUp(Mouse mouse, MouseButton button){
		super.onMouseUp(mouse, button);
		this.setPressed(false);
	}
}
