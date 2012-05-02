package GUI.controls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import misc.IEventListener;

import GUI.Event;
import GUI.ItemEventArgs;

import com.google.common.collect.ImmutableList;

public class GUISelector<T> extends GUIControl {
	private List<T> items;
	private T selectedItem;
	private int selectedItemIndex;
	
	private Event<ItemEventArgs<T>> selectedItemChangedEvent;
	private Event<ItemEventArgs<T>> itemAddedEvent;
	private Event<ItemEventArgs<T>> itemRemovedEvent;
	
	
	public GUISelector() {
		this.items = new ArrayList<>();
		this.selectedItem = null;
		this.selectedItemIndex = -1;
		
		this.selectedItemChangedEvent = new Event<>();
		this.itemAddedEvent = new Event<>();
		this.itemRemovedEvent = new Event<>();
	}	
	public ImmutableList<T> getItems() {
		return ImmutableList.copyOf(items);
	}
	public void addItem(T item) {
		this.items.add(item);
		this.onItemAdded(item);
	}
	
	public void addRange(Collection<T> items) {
		for (T item : items) {
			this.addItem(item);
		}
	}

	public void insertItem(T item, int index) {
		this.items.add(index, item);
	}
	public void removeItem(T item) {
		boolean r = this.items.remove(item);
		if(r) {
			if(this.selectedItem == item) {
				this.selectedItem = null;
				this.selectedItemIndex = -1;
			}
			
			this.onItemRemoved(item);
		}
	}
	public void removeItemAt(T item, int index) {
		if(index > 0 || index < this.items.size()) {
			this.items.remove(index);
			this.onItemRemoved(item);
		}
	}
	
	public int indexOf(T item) {
		return this.items.indexOf(item);
	}
	public int itemCount() {
		return this.items.size();
	}
	public int getSelecteItemIndex() {
		return this.selectedItemIndex;
	}
	
	public void setSelectedIndex(int index) {
		this.selectedItemIndex = index;
		this.selectedItem = this.items.get(index);
		this.onSelectedItemChanged(selectedItem);
	}

	public void setSelectedItem(T newItem) {
		if(newItem == null) {
			this.selectedItem = null;
		} else {
			if(this.items.contains(newItem)) {
				this.selectedItem = newItem;
				this.selectedItemIndex = this.items.indexOf(newItem);
			} else {
				throw new UnsupportedOperationException("The item " + newItem + " was not pressent in the selector.");
			}
		}
		
		this.onSelectedItemChanged(newItem);
	}
	
	public T getSelectedItem() {
		return this.selectedItem;
	}
	public void sortItems(Comparator<T> comparator) {
		Collections.sort(this.items, comparator);
	}
	
	protected void onSelectedItemChanged(T item) {
		ItemEventArgs<T> args = new ItemEventArgs<>(item);
		this.selectedItemChangedEvent.invoke(this, args);
	}
	protected void onItemAdded(T item) {
		ItemEventArgs<T> args = new ItemEventArgs<>(item);
		this.itemAddedEvent.invoke(this, args);
	}
	
	protected void onItemRemoved(T item) {
		ItemEventArgs<T> args = new ItemEventArgs<>(item);
		this.itemAddedEvent.invoke(this, args);
	}
	
	
	public void addSelectedChangedListener(IEventListener<ItemEventArgs<T>> listener) {
		this.selectedItemChangedEvent.addListener(listener);
	}	
	public void removeSelectedChangedListener(IEventListener<ItemEventArgs<T>> listener) {
		this.selectedItemChangedEvent.removeListener(listener);
	}
	public void addItemAddedListener(IEventListener<ItemEventArgs<T>> listener) {
		this.itemAddedEvent.addListener(listener);
	}
	public void removeItemRemovedListener(IEventListener<ItemEventArgs<T>> listener) {
		this.itemRemovedEvent.addListener(listener);
	}

}
