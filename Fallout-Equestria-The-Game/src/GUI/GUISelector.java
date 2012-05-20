package GUI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import utils.Event;
import utils.IEventListener;



import com.google.common.collect.ImmutableList;

public class GUISelector<T> extends GUIControl implements ISelector<T> {
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
	/* (non-Javadoc)
	 * @see GUI.controls.ISelector#getItems()
	 */
	@Override
	public ImmutableList<T> getItems() {
		return ImmutableList.copyOf(items);
	}
	/* (non-Javadoc)
	 * @see GUI.controls.ISelector#addItem(T)
	 */
	@Override
	public void addItem(T item) {
		this.items.add(item);
		this.onItemAdded(item);
	}
	
	/* (non-Javadoc)
	 * @see GUI.controls.ISelector#addRange(java.util.Collection)
	 */
	@Override
	public void addRange(Collection<T> items) {
		for (T item : items) {
			this.addItem(item);
		}
	}

	/* (non-Javadoc)
	 * @see GUI.controls.ISelector#insertItem(T, int)
	 */
	@Override
	public void insertItem(T item, int index) {
		this.items.add(index, item);
	}
	/* (non-Javadoc)
	 * @see GUI.controls.ISelector#removeItem(T)
	 */
	@Override
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
	/* (non-Javadoc)
	 * @see GUI.controls.ISelector#removeItemAt(T, int)
	 */
	@Override
	public void removeItemAt(T item, int index) {
		if(index > 0 || index < this.items.size()) {
			this.items.remove(index);
			this.onItemRemoved(item);
		}
	}
	
	/* (non-Javadoc)
	 * @see GUI.controls.ISelector#indexOf(T)
	 */
	@Override
	public int indexOf(T item) {
		return this.items.indexOf(item);
	}
	/* (non-Javadoc)
	 * @see GUI.controls.ISelector#itemCount()
	 */
	@Override
	public int itemCount() {
		return this.items.size();
	}
	/* (non-Javadoc)
	 * @see GUI.controls.ISelector#getSelecteItemIndex()
	 */
	@Override
	public int getSelecteItemIndex() {
		return this.selectedItemIndex;
	}
	
	/* (non-Javadoc)
	 * @see GUI.controls.ISelector#setSelectedIndex(int)
	 */
	@Override
	public void setSelectedIndex(int index) {
		this.selectedItemIndex = index;
		this.selectedItem = this.items.get(index);
		this.onSelectedItemChanged(selectedItem);
	}

	/* (non-Javadoc)
	 * @see GUI.controls.ISelector#setSelectedItem(T)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see GUI.controls.ISelector#getSelectedItem()
	 */
	@Override
	public T getSelectedItem() {
		return this.selectedItem;
	}
	/* (non-Javadoc)
	 * @see GUI.controls.ISelector#sortItems(java.util.Comparator)
	 */
	@Override
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
	
	
	/* (non-Javadoc)
	 * @see GUI.controls.ISelector#addSelectedChangedListener(misc.IEventListener)
	 */
	@Override
	public void addSelectedChangedListener(IEventListener<ItemEventArgs<T>> listener) {
		this.selectedItemChangedEvent.addListener(listener);
	}	
	/* (non-Javadoc)
	 * @see GUI.controls.ISelector#removeSelectedChangedListener(misc.IEventListener)
	 */
	@Override
	public void removeSelectedChangedListener(IEventListener<ItemEventArgs<T>> listener) {
		this.selectedItemChangedEvent.removeListener(listener);
	}
	/* (non-Javadoc)
	 * @see GUI.controls.ISelector#addItemAddedListener(misc.IEventListener)
	 */
	@Override
	public void addItemAddedListener(IEventListener<ItemEventArgs<T>> listener) {
		this.itemAddedEvent.addListener(listener);
	}
	/* (non-Javadoc)
	 * @see GUI.controls.ISelector#removeItemRemovedListener(misc.IEventListener)
	 */
	@Override
	public void removeItemRemovedListener(IEventListener<ItemEventArgs<T>> listener) {
		this.itemRemovedEvent.addListener(listener);
	}

}
