package GUI.controls;

import java.util.Collection;
import java.util.Comparator;

import com.google.common.collect.ImmutableList;

import graphics.Color;
import graphics.TextureFont;
import GUI.IItemFormater;
import GUI.ItemEventArgs;
import misc.EventArgs;
import misc.IEventListener;
import utils.Mouse;
import utils.Rectangle;


public class ComboBox<T> extends Panel implements ISelector<T>  {
	private static final int buttonWidth = 20;
	
	private Textfield showField;
	private Button selectButton;
	private ListBox<T> selectorBox;
	private Rectangle topRect;
	private boolean selectorBoxVisible;
	
	public ComboBox() {
		this.setRenderOrder(Integer.MAX_VALUE);
		
		this.showField = new Textfield();
		this.selectButton = new Button();
		this.selectorBox = new ListBox<>();
		
		this.selectorBox.enabled = false;
		this.selectorBox.visible = false;
		this.selectorBox.setBgColor(new Color(200,200,200,255));
		this.selectorBox.setFgColor(Color.Black);
		
		
		this.addChild(showField);
		this.addChild(selectButton);
		this.addChild(selectorBox);
		
		this.initializeComponents();
	}
	
	public void setFont(TextureFont font) {
		this.showField.setFont(font);
		this.selectorBox.setFont(font);
		this.updateSelectorBox();
	}
	
	private void initializeComponents() {
		this.selectButton.addClicked(new IEventListener<EventArgs>() {
			@Override
			public void onEvent(Object sender, EventArgs e) {
				showSelectorBox();
			}
		});
		
		this.selectorBox.addSelectedChangedListener(new IEventListener<ItemEventArgs<T>>() {
			@Override
			public void onEvent(Object sender, ItemEventArgs<T> e) {
				onSelectedChanged(e.getItem());
			}
		});
		
		this.selectorBox.addItemAddedListener(new IEventListener<ItemEventArgs<T>>() {
			@Override
			public void onEvent(Object sender, ItemEventArgs<T> e) {
				updateSelectorBox();
			}
		});
	}

	protected void onSelectedChanged(T newSelectedItem) {
		this.showField.setText("");
		if(newSelectedItem != null) {
			IItemFormater<T> formater = this.getItemFormater();
			if(formater != null) {
				this.showField.setText(formater.formatItem(newSelectedItem));
			} else {
				this.showField.setText(newSelectedItem.toString());	
			}		
		}
		
		this.hideSelectorBox();
	}

	protected void showSelectorBox() {
		this.selectorBoxVisible = true;
		this.selectorBox.setVisible(true);
		this.selectorBox.setEnabled(true);
	}

	private void hideSelectorBox() {
		this.selectorBoxVisible = false;
		this.selectorBox.setVisible(false);
		this.selectorBox.setEnabled(false);
	}
	
	@Override
	public void onMouseExit(Mouse mouse) {
		if(this.selectorBoxVisible) {
			hideSelectorBox();
		}
	}

	@Override
	public void onResized(Rectangle bounds) {
		super.onResized(bounds);
		this.topRect = bounds;
		this.selectButton.setBounds(bounds.Width - buttonWidth, 0, buttonWidth, bounds.Height);
		this.showField.setBounds(0,0, bounds.Width - buttonWidth, bounds.Height);
		this.updateSelectorBox();
		setActualBounds(bounds);
	}
	

	private void updateSelectorBox() {
		TextureFont font = selectorBox.getFont();
		if(font != null) {
			int height = (int) (font.getLineSpacing() * 5);
			selectorBox.setBounds(0,topRect.Height,topRect.Width, height);
			this.setActualBounds(topRect);
			
		}
	}

	private void setActualBounds(Rectangle bounds) {
		this.bounds = new Rectangle(bounds.X, bounds.Y, bounds.Width, 
														bounds.Height + selectorBox.getBounds().Height);
		
	}

	@Override
	public ImmutableList<T> getItems() {
		return this.selectorBox.getItems();
	}

	@Override
	public void addItem(T item) {
		this.selectorBox.addItem(item);
	}

	@Override
	public void addRange(Collection<T> items) {
		this.selectorBox.addRange(items);
	}

	@Override
	public void insertItem(T item, int index) {
		this.selectorBox.insertItem(item, index);
	}

	@Override
	public void removeItem(T item) {
		this.selectorBox.removeItem(item);
	}

	@Override
	public void removeItemAt(T item, int index) {
		this.selectorBox.removeItemAt(item, index);
	}
	
	public void setItemFormater(IItemFormater<T> itemFormater) {
		this.selectorBox.setItemFormater(itemFormater);
	}
	public IItemFormater<T> getItemFormater() {
		return this.selectorBox.getItemFormater();
	}

	@Override
	public int indexOf(T item) {
		return this.selectorBox.indexOf(item);
	}

	@Override
	public int itemCount() {
		return this.selectorBox.itemCount();
	}

	@Override
	public int getSelecteItemIndex() {
		return this.selectorBox.getSelecteItemIndex();
	}

	@Override
	public void setSelectedIndex(int index) {
		this.selectorBox.setSelectedIndex(index);
		
	}

	@Override
	public void setSelectedItem(T newItem) {
		this.selectorBox.setSelectedItem(newItem);
	}

	@Override
	public T getSelectedItem() {
		return this.selectorBox.getSelectedItem();
	}

	@Override
	public void sortItems(Comparator<T> comparator) {
		this.selectorBox.sortItems(comparator);
	}

	@Override
	public void addSelectedChangedListener(
			IEventListener<ItemEventArgs<T>> listener) {
		this.selectorBox.addSelectedChangedListener(listener);
		
	}

	@Override
	public void removeSelectedChangedListener(
			IEventListener<ItemEventArgs<T>> listener) {
		this.selectorBox.removeSelectedChangedListener(listener);
		
	}

	@Override
	public void addItemAddedListener(IEventListener<ItemEventArgs<T>> listener) {
		this.selectorBox.addItemAddedListener(listener);
	}

	@Override
	public void removeItemRemovedListener(
			IEventListener<ItemEventArgs<T>> listener) {
		this.selectorBox.removeItemRemovedListener(listener);
	}
	
}
