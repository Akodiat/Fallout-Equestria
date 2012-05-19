package GUI.controls;

import utils.Mouse;
import utils.MouseButton;
import GUI.graphics.ButtonRenderer;

public class ToggleButton extends ButtonBase {
	private static ButtonRenderer DEFAULT_RENDERER = new ButtonRenderer();
	
	private boolean toggled;
	
	public ToggleButton() {
		this.toggled = false;
		this.setRenderer(DEFAULT_RENDERER);
	}	
	
	public boolean isToggled() {
		return toggled;
	}
	public void setToggled(boolean toggled) {
		this.toggled = toggled;
		this.setMouseHover(toggled);
		this.setPressed(toggled);
	}
	
	@Override
	protected void onMouseEnter(Mouse mouse) {
		super.onMouseEnter(mouse);
		if(!this.isToggled())
			this.setMouseHover(true);
	}
	
	@Override
	protected void onMouseExit(Mouse mouse) {
		super.onMouseExit(mouse);
		if(!this.isToggled())
			this.setMouseHover(false);
	}
	
	@Override
	protected void onMouseDown(Mouse mouse, MouseButton button) {
		super.onMouseDown(mouse, button);
		this.setPressed(true);
	}
	
	@Override
	protected void onMouseUp(Mouse mouse, MouseButton button) {
		this.setPressed(false);
		super.onMouseUp(mouse, button);
	}
	

	@Override
	protected void onClicked() {
		super.onClicked();
		this.toggled = !this.toggled;
		if(this.toggled) {
			this.setPressed(true);
		} else {
			this.setPressed(false);
		}
	}
}
