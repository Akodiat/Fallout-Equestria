package GUI;

import utils.EventArgs;
import utils.Rectangle;

public class ResizedEventArgs extends EventArgs{
	private final Rectangle bounds;
	
	public ResizedEventArgs(Rectangle bounds) {
		this.bounds = bounds;
	}

	public Rectangle getBounds() {
		return bounds;
	}
}
