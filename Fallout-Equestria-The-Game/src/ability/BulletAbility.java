package ability;

import animation.Bones;
import components.AnimationComp;
import components.SpatialComp;
import components.TransformationComp;

import math.Vector2;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import utils.time.GameTime;

public class BulletAbility extends Ability {
	private IEntityArchetype bulletArchetype;
	private final int speed;
	private Vector2 fireOffset;
	
	public BulletAbility(IEntityArchetype bulletArchetype, int speed, boolean blocking) {
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
		SpatialComp spatialComp = this.SorceEntity.getComponent(SpatialComp.class);
		Vector2 direction = trans.getMirror() ? Vector2.UnitX : new Vector2(-1, 0);
		Vector2 velocity = Vector2.mul(this.speed, direction);
		
		IEntity bullet = this.CreationFactory.createMovingEntity(bulletArchetype, Vector2.Zero,trans.getHeight(), velocity);
		
		float posX = direction.X *spatialComp.getBounds().getWidth() +  trans.getPosition().X;
		float posY = trans.getPosition().Y + bullet.getComponent(SpatialComp.class).getBounds().getHeight() / 2;
		
		bullet.getComponent(TransformationComp.class).setPosition(posX,posY);
	
		
		try {
			bullet.getComponent(AnimationComp.class).setTint(
					this.SorceEntity.getComponent(AnimationComp.class).getAnimationPlayer().getBoneColor(Bones.BODY.getValue()));
		} catch(Exception e) {
			System.out.println("Failed to set a correct color!");
		}
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
