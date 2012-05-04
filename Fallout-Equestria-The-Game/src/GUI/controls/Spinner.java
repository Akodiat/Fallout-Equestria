package GUI.controls;

import graphics.Texture2D;
import graphics.TextureFont;
import utils.Rectangle;
import GUI.MouseEventArgs;
import misc.Event;
import misc.EventArgs;
import misc.IEventListener;

public class Spinner extends Panel {
	private static final int margin = 5;

	private final Label label;
	private final Button plusButton, minusButton;

	private float max, min;
	private float interval;
	private float value;

	private static final int defaultFontSize = 20;

	private Event<EventArgs> spinnedEvent;

	public Spinner(float max, float min, float interval, float value, Texture2D plusImage, Texture2D minusImage) {
		super();
		this.label = new Label();
		this.plusButton = new Button();
		this.plusButton.setImage(plusImage);
		this.minusButton = new Button();
		this.minusButton.setImage(minusImage);

		this.addChild(label);
		this.addChild(plusButton);
		this.addChild(minusButton);

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

	public void setFont(TextureFont font) {
		this.label.setFont(font);
		updateLabel();
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

	@Override
	public void onResized(Rectangle bounds) {
		updateLabel();
		updateButtons();
		
		super.onResized(bounds);
	}
	
	public void updateLabel() {
		int textHeight;

		this.label.setText(value + "");

		if(this.label.getFont() != null) {
			textHeight = (int) this.label.getFont().getLineSpacing();
		} else {
			textHeight = defaultFontSize;
		}

		this.label.setBounds(margin, this.getBounds().Height/2 - textHeight/2, 
				             this.getBounds().Width/2, textHeight);
		
		System.out.println(this.label.getBounds());
	}

	public void updateButtons() {
		this.minusButton.setBounds(this.label.getBounds().getRight() + margin, margin,
				this.getBounds().Width/4 - 2*margin, this.getBounds().Height - 2*margin);

		this.plusButton.setBounds(this.minusButton.getBounds().getRight() + margin, margin,
				this.getBounds().Width/4 - 2*margin, this.getBounds().Height - 2*margin);
	}
	
	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getInterval() {
		return interval;
	}

	public void setInterval(float interval) {
		this.interval = interval;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

}
