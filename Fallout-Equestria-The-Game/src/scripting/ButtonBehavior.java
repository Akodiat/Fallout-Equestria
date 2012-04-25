package scripting;

import java.util.HashSet;
import java.util.Set;

import components.GUIComp;
import components.RenderingComp;
import components.TextRenderingComp;

import utils.ButtonState;
import utils.MouseButton;
import utils.MouseState;

import content.ContentManager;

import misc.IEventListener;

import graphics.Color;
import graphics.Texture2D;
import graphics.TextureFont;
import GUI.ButtonEventArgs;
import anotations.Editable;

@Editable
public class ButtonBehavior extends Behavior {

	private static final String DEF_BACKGROUND_TEXTURE = "GUI/ButtonBackground.png";
	private static final String DEF_NORMAL_TEXTURE 	   = "GUI/NormalButton.png";
	private static final String DEF_OVER_TEXTURE       = "GUI/OverButton.png";
	private static final String DEF_DOWN_TEXTUE  	   = "GUI/DownButton.png";	
	private static final String DEF_FONT		   	   = "GUI/StandardButton.font";

	private boolean pressed = false;
	
	private @Editable Texture2D overTexture;
	private @Editable Texture2D downTexture;
			
	private Set<IEventListener<ButtonEventArgs>> listeners;
	
	private GUIComp guiComp;
	
	/**Creates a new default button-behavior.
	 */
	public ButtonBehavior() {
		this(null,null);
	}
	
	/**Creates a specialized button with customized images
	 * 
	 * @param normalTexture the image shown when the button is not 
	 * @param overTexture
	 * @param downTexture
	 */
	public ButtonBehavior(Texture2D overTexture, Texture2D downTexture) {
		this.overTexture = overTexture;
		this.downTexture = downTexture;
		listeners = new HashSet<>();
	}
	
	public ButtonBehavior(ButtonBehavior other) {
		this.overTexture   = other.overTexture;
		this.downTexture   = other.downTexture;
		listeners = new HashSet<>();
	}
	
	@Override
	public void start() {
		
		if(this.overTexture == null) {
			this.overTexture   = ContentManager.loadTexture(DEF_OVER_TEXTURE);
		}
		if(this.downTexture == null) {
			this.downTexture   = ContentManager.loadTexture(DEF_DOWN_TEXTUE);
		}
		
		this.guiComp = this.entity.getComponent(GUIComp.class);
		if(guiComp == null) {
			throw new NullPointerException();
		}
	}

	@Override
	public Object clone() {
		return new ButtonBehavior(this);
	}
	
	
	@Override
	public void onMouseDown(MouseState state, MouseButton button) {
		if(button == MouseButton.Left) {
			this.pressed = true;
			this.setActiveTexture(downTexture);
		}

		System.out.println("button down " + button);
	}
	@Override
	public void onMouseEnter(MouseState state){
		if(!this.pressed) {
			this.setActiveTexture(this.overTexture);
		} else {
			this.setActiveTexture(this.downTexture);
		}
	}

	@Override
	public void onMouseExit(MouseState state){
			this.setActiveTexture(null);
	}	
	@Override
	public void onMouseUpAsButton(MouseState state, MouseButton button){
		if(button == MouseButton.Left) {
			this.onButtonPress();
			this.setActiveTexture(overTexture);
		}
	}
	
	@Override
	public void onMouseUp(MouseState state, MouseButton button){
		if(button == MouseButton.Left) {
			pressed = false;
			this.setActiveTexture(null);
		}
	}
	

	private void setActiveTexture(Texture2D texture) {
		this.guiComp.setMiddleground(texture);
	}
	
	public void addEventListener(IEventListener<ButtonEventArgs> listener) {
		this.listeners.add(listener);
	}
	
	public void removeEventListener(IEventListener<ButtonEventArgs> listener) {
		this.listeners.remove(listener);
	}
	
	private void onButtonPress() {
		ButtonEventArgs args = 
				new ButtonEventArgs(this.guiComp.getText());	
		for (IEventListener<ButtonEventArgs> listener : this.listeners) {
			listener.onEvent(this.entity, args);
		}
	}
}
