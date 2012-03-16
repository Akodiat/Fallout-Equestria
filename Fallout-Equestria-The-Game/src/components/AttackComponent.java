package components;

import java.util.List;

import utils.Circle;

import com.google.common.collect.ImmutableList;

import entityFramework.Entity;
import entityFramework.IComponent;

public class AttackComponent implements IComponent {

	private Circle bounds;
	private int damage;
	private ImmutableList<Entity> targets;
	
	public AttackComponent(Circle bounds, int damage, ImmutableList<Entity> targets) {
		this.bounds = bounds;
		this.damage = damage;
		this.targets = targets;
	}

	public ImmutableList<Entity> getTargets() {
		return targets;
	}

	public void setTargets(ImmutableList<Entity> targets) {
		this.targets = targets;
	}

	/**
	 * Copyconstructor
	 * @param objectToCopy
	 */
	public AttackComponent(AttackComponent objectToCopy) {
		this(objectToCopy.bounds, objectToCopy.damage, objectToCopy.targets);
	}
	
	public Circle getBounds() {
		return bounds;
	}

	public void setBounds(Circle bounds) {
		this.bounds = bounds;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public Object clone(){
		return new AttackComponent(this);
	}
	
}
