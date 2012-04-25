package GUI;

import java.util.HashSet;
import java.util.Set;

import misc.EventArgs;
import misc.IEventListener;

public class Event<T extends EventArgs> {

	private Set<IEventListener<T>> listeners;
	
	public Event() {
		this.listeners = new HashSet<>();
	}
	
	public void addListener(IEventListener<T> listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(IEventListener<T> listener) {
		this.listeners.remove(listener);
	}
	
	public void invoke(Object sender, T args) {
		for (IEventListener<T> listener : this.listeners) {
			listener.onEvent(sender, args);
		}
	}
	
}
