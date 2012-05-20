package GUI;

import utils.EventArgs;

public class ItemEventArgs<T> extends EventArgs{
	private final T item;
	
	public ItemEventArgs(T item) {
		this.item = item;
	}

	public T getItem() {
		return item;
	}
}
