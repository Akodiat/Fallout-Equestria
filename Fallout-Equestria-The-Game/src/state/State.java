package state;

public abstract class State {
	private StateMachine<? extends State> machine;
	
	public State() {
		this.machine = null;
	}
	protected void setMachine(StateMachine<? extends State> machine) {
		this.machine = machine;
	}
	protected StateMachine<? extends State> getMachine() {
		return this.machine;
	}

	protected abstract void enter();
	protected abstract void exit();
}
