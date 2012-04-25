package scripting;

import components.GUIComp;

public class SpinnerBehavior extends Behavior{
	private GUIComp spinner;
	private GUIComp textField;
	private GUIComp plusButton;
	private GUIComp minusButton;
	
	@Override
	public void start() {
		this.spinner = entity.getComponent(GUIComp.class);
		
		this.textField = new GUIComp();
		this.plusButton = new GUIComp();
		this.minusButton = new GUIComp();
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
