package GUI;

import java.util.Collection;
import java.util.Comparator;

import utils.IEventListener;


import com.google.common.collect.ImmutableList;

public interface ISelector<T> {

	public abstract ImmutableList<T> getItems();

	public abstract void addItem(T item);

	public abstract void addRange(Collection<T> items);

	public abstract void insertItem(T item, int index);

	public abstract void removeItem(T item);

	public abstract void removeItemAt(T item, int index);

	public abstract int indexOf(T item);

	public abstract int itemCount();

	public abstract int getSelecteItemIndex();

	public abstract void setSelectedIndex(int index);

	public abstract void setSelectedItem(T newItem);

	public abstract T getSelectedItem();

	public abstract void sortItems(Comparator<T> comparator);

	public abstract void addSelectedChangedListener(
			IEventListener<ItemEventArgs<T>> listener);

	public abstract void removeSelectedChangedListener(
			IEventListener<ItemEventArgs<T>> listener);

	public abstract void addItemAddedListener(
			IEventListener<ItemEventArgs<T>> listener);

	public abstract void removeItemRemovedListener(
			IEventListener<ItemEventArgs<T>> listener);

}