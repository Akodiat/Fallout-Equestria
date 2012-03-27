package components;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import death.IDeathAction;
import entityFramework.IComponent;
import scripting.LineScript;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("Death")
public class DeathComp implements IComponent{
	private Set<IDeathAction> deathActions;
	
	public DeathComp() {
		this(new HashSet<IDeathAction>());
	}
	
	public DeathComp(Collection<IDeathAction> deathActions) {
		this.deathActions = new HashSet<>();
		for (IDeathAction deathAction : deathActions) {
			this.deathActions.add(deathAction);
		}
	}
	
	private DeathComp(DeathComp other) {
		this(other.deathActions);
	}
	
	public Object clone() {
		return new DeathComp(this);
	}
	
	public Set<IDeathAction> getDeathActions() {
		return deathActions;
	}

	public void addDeathAction(IDeathAction deathAction) {
		this.deathActions.add(deathAction);
	}

	public String toString() {
		return "Death Comp: " +
			   "\n Script: \n" + this.deathActions.toString();
		
	}
}
