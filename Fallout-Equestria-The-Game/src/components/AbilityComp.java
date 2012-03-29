package components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import ability.Ability;

import entityFramework.IComponent;

@XStreamAlias("Ability")
public class AbilityComp implements IComponent{
	private Map<Class<? extends Ability>, Ability> abilities;
	private Ability currentAbility;
	
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
	
	public ArrayList<Ability> getListOfAbilities(){
		return new ArrayList<Ability>(abilities.values());
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
	
	public void removeAbility(Ability ability){
		abilities.remove(ability.getClass());
	}
	
	public String toString() {
		String s = "AbilityComp: \n";
		if(this.currentAbility != null) {
			s += "CurrentAbility:" + this.currentAbility.toString() + "\n";
		}
		
		for (Ability ability : this.abilities.values()) {
			s += ability.toString() + "\n";
		}
		
		return s;
	}
	
}
