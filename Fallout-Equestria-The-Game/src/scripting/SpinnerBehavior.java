package scripting;

import utils.GameTime;
import anotations.Editable;
import components.GUIComp;

@Editable
public class SpinnerBehavior extends GUIBehaviour{
	@Editable
	private GUIComp label, plusButton, minusButton;
	
	@Editable
	private int value, min, max, interval;
	
	public SpinnerBehavior(GUIComp label, GUIComp plusBtn, GUIComp minusBtn, int value, int max, int min, int interval) {
		this.label = label;
		this.plusButton = plusBtn;
		this.minusButton = minusBtn;
		this.value = value;
		this.max = max;
		this.min = min;
		this.interval = interval;
	}
	
	@Override
	public void start() {
		onFocusGained();
	}
	
	@Override
	public void onFocusGained() {
		
	}
	
	@Override
	public void onFocusLost() {
		
	}
	
	@Override	
	public void update(GameTime time) {
		
	}
	
	public void increment () {
		if(value < max) {
			value += interval;
		}
	}
	
	public void decrement () {
		if(value > min) {
			value -= interval;
		}
	}

	@Override
	public Object clone() {
		return new SpinnerBehavior(this.label, this.plusButton, this.minusButton, this.value, this.max, this.min, this.interval);
	}
}
