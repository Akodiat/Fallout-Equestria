package GUI.controls;

import GUI.Event;
import GUI.MouseEventArgs;
import misc.EventArgs;
import misc.IEventListener;

public class Spinner extends Panel{
	private final Label label;
	private final Button plusButton;
	private final Button minusButton;
	
	private float max;
	private float min;
	private float interval;
	
	private float value;
	
	private Event<EventArgs> spinnedEvent;
	
	public Spinner(float max, float min, float interval, float value) {
		super();
		this.label = new Label();
		this.plusButton = new Button();
		this.minusButton = new Button();
		
		this.max = max;
		this.min = min;
		this.interval = interval;
		this.value = value;
		
		this.spinnedEvent = new Event<>();
		
		this.initialize();
	}
	
	public void initialize() {
		this.plusButton.addMouseClicked(new IEventListener<MouseEventArgs>() {
			@Override
			public void onEvent(Object sender, MouseEventArgs e) {
				modify(sender);
			}
		});
		
		this.minusButton.addMouseClicked(new IEventListener<MouseEventArgs>() {
			@Override
			public void onEvent(Object sender, MouseEventArgs e) {
				modify(sender);
			}
		});
	}
	
	public void modify(Object sender) {
		if(sender == this.plusButton) {
			if(value <= max - interval) {
				this.value += interval;
			}
		} else if(sender == this.minusButton){
			if(value >= min + interval) {
				this.value -= interval;
			}
		}
		
		this.label.setText(value + "");
		this.spinnedEvent.invoke(sender, EventArgs.Empty);
	}
}
