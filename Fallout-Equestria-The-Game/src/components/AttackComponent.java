package components;

import java.util.List;

import math.Vector2;

import utils.Circle;

import com.google.common.collect.ImmutableList;

import entityFramework.Entity;
import entityFramework.IComponent;

public class AttackComponent implements IComponent {

	private Circle bounds;
	private int damage;
	private ImmutableList<String> targetGroups;
	
	public AttackComponent(Circle bounds, int damage, ImmutableList<String> targetGroups) {
		this.bounds = bounds;
		this.damage = damage;
		this.targetGroups = targetGroups;
	}
	
	/**
	 * Copyconstructor
	 * @param objectToCopy
	 */
	public AttackComponent(AttackComponent objectToCopy) {
		this(objectToCopy.bounds, objectToCopy.damage, objectToCopy.targetGroups);
	}
	
	public AttackComponent(){
		this(new Circle(new Vector2(0.0f,0.0f), 1), 1, ImmutableList.builder().build(String));
	}

	public ImmutableList<String> getTargetGroups() {
		return targetGroups;
	}

	public void setTargetGroups(ImmutableList<String> targetGroups) {
		this.targetGroups = targetGroups;
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
