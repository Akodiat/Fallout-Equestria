package components;

import anotations.Editable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import entityFramework.IComponent;

@XStreamAlias("Status")
@Editable
public class StatusComp implements IComponent {
	//TODO Implement!!!???
	
	public Object clone() {
		return new StatusComp();
	}
	
	public String toString() {
		return "Status Comp: \n"
			+  "You're alive and healthy!";
	}
}
