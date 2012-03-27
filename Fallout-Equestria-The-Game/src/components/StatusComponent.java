package components;

import entityFramework.IComponent;

public class StatusComponent implements IComponent {
	
	public Object clone() {
		return new StatusComponent();
	}
}
