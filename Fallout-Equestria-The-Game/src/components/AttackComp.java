package components;

import java.util.HashSet;
import java.util.Set;

import math.Vector2;

import utils.Circle;

import com.google.common.collect.ImmutableSet;

import entityFramework.IComponent;
import entityFramework.IEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("Attack")
@EditableComponent
public class AttackComp implements IComponent {

	private Circle		 bounds;
	private int 		 damage;
	private IEntity 	 sourceEntity;
	private boolean 	 destoryOnHit;
	private Set<IEntity> targetsHit;

	public AttackComp() {
		this(null, new Circle(new Vector2(0.0f, 0.0f), 0), 1, true);
	}
	
	public AttackComp(IEntity sourceEntity, Circle bounds, int damage, boolean destroyOnHit) {
		this.bounds		  = bounds;
		this.damage		  = damage;
		this.sourceEntity = sourceEntity;
		this.destoryOnHit = destroyOnHit;
		this.targetsHit   = new HashSet<>();
	}

	/**
	 * Copyconstructor
	 * @param objectToCopy
	 */
	private AttackComp(AttackComp other) {
		this.bounds		  = other.bounds;
		this.damage 	  = other.damage;
		this.sourceEntity = other.sourceEntity;
		this.destoryOnHit = other.destoryOnHit;
		this.targetsHit   = new HashSet<>();
	}

	public Object clone() {
		return new AttackComp(this);
	}

	public boolean isDestoryOnHit() {
		return destoryOnHit;
	}

	public void setDestoryOnHit(boolean destoryOnHit) {
		this.destoryOnHit = destoryOnHit;
	}

	public void setSourceEntity(IEntity entity) {
		this.sourceEntity = entity;
	}
	
	public IEntity getSourceEntity() {
		return this.sourceEntity;
	}
	
	public void addTargetHit(IEntity entity) {
		this.targetsHit.add(entity);
	}
	
	public Set<IEntity> getTargetsHit() {
		return ImmutableSet.copyOf(this.targetsHit);
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
	
	@Override
	public String toString() {
		String s = "AttackComp: \n"
			  +	   "Damage: " + this.damage
			  +	   "\nCircle Bounds: " + this.bounds.toString()
			  +	   "\nDestroyed on hit: " + this.destoryOnHit
			  +	   "\nSorceEntity: ";
		if(this.sourceEntity != null) {
			s += this.sourceEntity.getUniqueID() + "";
		} else {
			s += "None";
		}
		
		return s;
	}
}
