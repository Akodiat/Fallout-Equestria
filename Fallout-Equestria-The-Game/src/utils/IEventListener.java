package utils;


public interface IEventListener<T extends EventArgs> {
	public void onEvent(Object sender, T e);
}
