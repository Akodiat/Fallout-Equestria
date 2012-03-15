package components;

import java.util.List;

import org.lwjgl.util.Rectangle;

import entityFramework.Entity;
import entityFramework.IComponent;

public class AttackComponent implements IComponent {

	private Rectangle bounds;
	private int damage;
	
	public AttackComponent(Rectangle bounds, int damage) {
		this.bounds = bounds;
		this.damage = damage;
	}

	/**
	 * Copyconstructor
	 * @param objectToCopy
	 */
	public AttackComponent(AttackComponent objectToCopy) {
		this(objectToCopy.bounds, objectToCopy.damage);
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

	public Object clone(){
		return new AttackComponent(this);
	}
	
}
