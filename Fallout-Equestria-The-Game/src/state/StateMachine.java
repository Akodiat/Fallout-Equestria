package state;

import java.util.HashMap;
import java.util.Map;

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
}
