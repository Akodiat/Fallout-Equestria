package components;

import entityFramework.IComponent;

public class StatusComp implements IComponent {
	//TODO Implement!!!???
	
	public Object clone() {
		return new StatusComp();
	}
	
	public String toString() {
		return "Status Comp: \n"
			+  "Your alive and healthy!";
	}
}
