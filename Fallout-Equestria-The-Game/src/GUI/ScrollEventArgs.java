package GUI;

import misc.EventArgs;

public class ScrollEventArgs extends EventArgs {
	private final int newValue;
	
	public ScrollEventArgs(int newValue) {
		this.newValue = newValue;
	}

	public int getNewValue() {
		return newValue;
	}
}
