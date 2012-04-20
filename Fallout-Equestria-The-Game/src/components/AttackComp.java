package components;

import java.util.HashSet;
import java.util.Set;

import math.Vector2;

import scripting.DestroyOnHitScript;
import scripting.HitScript;
import utils.Circle;

import anotations.Editable;

import com.google.common.collect.ImmutableSet;

import entityFramework.IComponent;
import entityFramework.IEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("Attack")
@Editable
public class AttackComp implements IComponent {

	@Editable
	private Circle		 bounds;
	private IEntity 	 sourceEntity;
	private Set<IEntity> targetsHit;
	private HitScript 	 attackScript;

	public AttackComp() {
		this(null, new Circle(new Vector2(0.0f, 0.0f), 0), new DestroyOnHitScript());
		this.attackScript.start();
	}
	
	public AttackComp(IEntity sourceEntity, Circle bounds) {
		this(sourceEntity, bounds, new DestroyOnHitScript());
	}
	
	public AttackComp(IEntity sourceEntity, Circle bounds, HitScript attackScript) {
		this.bounds		  = bounds;
		this.sourceEntity = sourceEntity;
		this.targetsHit   = new HashSet<>();
		this.attackScript = attackScript;
		attackScript.start();
	}

	public HitScript getAttackScript() {
		return attackScript;
	}

	/**
	 * Copyconstructor
	 * @param objectToCopy
	 */
	private AttackComp(AttackComp other) {
		this.bounds		  = other.bounds;
		this.sourceEntity = other.sourceEntity;
		this.targetsHit   = new HashSet<>();
		this.attackScript = other.attackScript;
	}

	public Object clone() {
		return new AttackComp(this);
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
	
	@Override
	public String toString() {
		String s = "AttackComp: \n"
			  +	   "\nCircle Bounds: " + this.bounds.toString()
			  +	   "\nSorceEntity: ";
		if(this.sourceEntity != null) {
			s += this.sourceEntity.getUniqueID() + "";
		} else {
			s += "None";
		}
		
		return s;
	}
}
