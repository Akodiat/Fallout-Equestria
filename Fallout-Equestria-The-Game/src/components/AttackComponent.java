package components;

import java.util.List;

import org.lwjgl.util.Rectangle;

import entityFramework.Entity;
import entityFramework.IComponent;

public class AttackComponent implements IComponent {

	private Rectangle bounds;
	private int damage;
	private List<Entity> targets;
	
	public AttackComponent(Rectangle bounds, int damage, List<Entity> targets) {
		this.bounds = bounds;
		this.damage = damage;
		this.targets = targets;
	}

	/**
	 * Copyconstructor
	 * @param objectToCopy
	 */
	public AttackComponent(AttackComponent objectToCopy) {
		this(objectToCopy.bounds, objectToCopy.damage, objectToCopy.targets);
	}
	
	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public List<Entity> getTargets() {
		return targets;
	}

	public void setTargets(List<Entity> targets) {
		this.targets = targets;
	}

	public Object clone(){
		return new AttackComponent(this);
	}
	
}
