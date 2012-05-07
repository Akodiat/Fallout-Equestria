package ability;

import components.TransformationComp;

import math.Vector2;
import entityFramework.IEntityArchetype;
import utils.GameTime;

public class BulletAbility extends Ability {
	private IEntityArchetype bulletArchetype;
	private final int speed;
	private Vector2 fireOffset;
	
	public BulletAbility(IEntityArchetype bulletArchetype, int speed, Vector2 fireOffset, boolean blocking) {
		super(blocking);
		this.bulletArchetype = bulletArchetype;
		this.speed = speed;
		this.setFireOffset(fireOffset);
	}

	@Override
	public void start() {
		this.createBullet();
	}
	
	protected void createBullet() {
		TransformationComp trans = this.SorceEntity.getComponent(TransformationComp.class);
		Vector2 direction = trans.getMirror() ? Vector2.UnitX : new Vector2(-1, 0);
		Vector2 velocity = Vector2.mul(this.speed, direction);
		
		float posX = direction.X * fireOffset.X + trans.getPosition().X;
		float posY = fireOffset.Y + trans.getPosition().Y;
		System.out.println(posX + "|" + posY);
		
		this.CreationFactory.createMovingEntity(bulletArchetype, new Vector2(posX,posY), velocity);		
	}
	
	public void update(GameTime time) {	}
	public void stop() { }

	
	
	public Vector2 getFireOffset() {
		return fireOffset;
	}

	public void setFireOffset(Vector2 fireOffset) {
		this.fireOffset = fireOffset;
	}

}
