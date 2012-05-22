package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StateMachine<T extends State> {
	protected T activeState; 

	private Map<String, T> stateMap;
	
	public StateMachine() {
		this.activeState = null;
		this.stateMap = new HashMap<>();
	}
	
	public void registerState(String stateKey, T state) {
		this.stateMap.put(stateKey, state);
	}
	
	public void changeState(String stateKey) {
		if(this.activeState != null) {
			this.activeState.exit();
		}
		
		T state = stateMap.get(stateKey);
		
		if(state == null) {
			throw new NullPointerException("The state " + stateKey + "does not exist");
		}
		
		this.activeState = state;
		this.activeState.enter();
	}
	
	public String getActiveState() {
		Set<String> keys = this.stateMap.keySet();

		for (String string : keys) {
			if(this.stateMap.get(string).equals(activeState))
				return string;
		}
		throw new RuntimeException("ActiveState not found");
	}
}
