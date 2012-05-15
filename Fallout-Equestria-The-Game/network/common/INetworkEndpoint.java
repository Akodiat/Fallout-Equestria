package common;

import java.util.Iterator;
import java.util.List;

import com.esotericsoftware.kryo.Kryo.Listener;

public interface INetworkEndpoint {
	public void send(Object object);
	public <T> void send(Iterator<T> iteratorOfObjects);
		
	
	public void addNetworkListener(Listener listener);
	public void removeNetworkListener(Listener listener);
}
