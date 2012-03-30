package components;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import death.DeathActions;
import entityFramework.IComponent;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("Death")
public class DeathComp implements IComponent{
	private Set<DeathActions> deathActions;
	public DeathComp() {
		this(new HashSet<DeathActions>());
	}
	
	public DeathComp(Collection<DeathActions> deathActions) {
		this.deathActions = new HashSet<>();
		for (DeathActions deathAction : deathActions) {
			this.deathActions.add(deathAction);
		}
	}
	
	private DeathComp(DeathComp other) {
		this(other.deathActions);
	}
	
	public Object clone() {
		return new DeathComp(this);
	}
	
	public Set<DeathActions> getDeathActions() {
		return deathActions;
	}

	public void addDeathAction(DeathActions deathAction) {
		this.deathActions.add(deathAction);
	}

	public String toString() {
		return "Death Comp: " +
			   "\n DeathActions: \n" + this.deathActions;
		
	}
}
