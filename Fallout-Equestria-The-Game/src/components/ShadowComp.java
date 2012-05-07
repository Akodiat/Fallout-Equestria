package components;

import entityFramework.IComponent;

public class ShadowComp implements IComponent{
	public Object clone() {
		return new ShadowComp();
	}
}
