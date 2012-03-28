package components;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import ability.Ability;

import entityFramework.IComponent;

@XStreamAlias("Ability")
public class AbilityComp implements IComponent{
	private Map<Class<? extends Ability>, Ability> abilities;
	Ability currentAbility;
	
	public AbilityComp(){
		this(new HashMap<Class<? extends Ability>, Ability>(), null);
	}
	public AbilityComp(Map<Class<? extends Ability>, Ability> abilities, Ability currentAbility){
		this.abilities = abilities;
		this.currentAbility = currentAbility;
	}
	private AbilityComp(AbilityComp other){
		this.abilities = other.abilities;
		this.currentAbility = other.currentAbility;
	}
	public Object clone(){
		return new AbilityComp(this);
	}
	/**
	 * @return the currentAbility
	 */
	public Ability getCurrentAbility() {
		return currentAbility;
	}
	/**
	 * @param currentAbility the currentAbility to set
	 */
	public void setCurrentAbility(Class<? extends Ability> abilityClass) {
		this.currentAbility = this.abilities.get(abilityClass);
	}
	public void addAbility(Ability ability){
		abilities.put(ability.getClass(), ability);
	}

}
