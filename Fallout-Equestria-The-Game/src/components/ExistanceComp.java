package components;

import entityFramework.IComponent;

public class ExistanceComp implements IComponent {

	private final float existanceTime;
	private float elapsedExistance;

	public ExistanceComp(float existanceTime) {
		this.existanceTime = existanceTime;
	}
	
	private ExistanceComp(ExistanceComp existanceComp) {
		this(existanceComp.existanceTime);
	}
	
	public boolean isExistanceOver() {
		return this.elapsedExistance >= this.existanceTime;
	}

	public void incrementElapsedExistance(float delta) {
		this.elapsedExistance += delta;
	}
	
	public Object clone() {
		return new ExistanceComp(this);
	}
}
