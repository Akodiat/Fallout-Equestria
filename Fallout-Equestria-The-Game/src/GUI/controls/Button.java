package GUI.controls;

import GUI.graphics.ButtonRenderer;
import utils.input.Mouse;
import utils.input.MouseButton;

public class Button extends ButtonBase {
	private static ButtonRenderer DEFAULT_RENDERER = new ButtonRenderer();
	
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
	}
	

	@Override
	protected void onMouseUp(Mouse mouse, MouseButton button){
		super.onMouseUp(mouse, button);
		this.setPressed(false);
	}
}
