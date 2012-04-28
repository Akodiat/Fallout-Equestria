package GUI;

public class ScrollBar extends GUIControl {
	
	private Button upButton;
	private Button downButton;
	private Button scrollButton;
	
	
	private boolean vertical;
	
	public boolean isHorizontal() {
		return !this.vertical;
	}	
	public boolean isVertical() {
		return this.vertical;
	}
	public Button getUpButton() {
		return upButton;
	}
	public void setUpButton(Button upButton) {
		this.upButton = upButton;
	}
	public Button getDownButton() {
		return downButton;
	}
	public void setDownButton(Button downButton) {
		this.downButton = downButton;
	}
	public Button getScrollButton() {
		return scrollButton;
	}
	public void setScrollButton(Button scrollButton) {
		this.scrollButton = scrollButton;
	}
}
