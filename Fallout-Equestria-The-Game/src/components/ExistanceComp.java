package components;

import anotations.Editable;
import anotations.EditableComponent;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import entityFramework.IComponent;

@XStreamAlias("Death")
@EditableComponent
public class ExistanceComp implements IComponent {

	private static final float DEF_EXISTANCE_TIME = 5f;
	
	@Editable
	private final float existanceTime;
	private float elapsedExistance;

	public ExistanceComp() {
		this(DEF_EXISTANCE_TIME);
	}
	
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
