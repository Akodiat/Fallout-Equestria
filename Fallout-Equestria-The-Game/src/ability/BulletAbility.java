package ability;

import org.newdawn.slick.openal.Audio;


import utils.Circle;
import math.Vector2;
import components.AttackComp;
import components.ExistanceComp;
import components.InputComp;
import components.PhysicsComp;
import components.RenderingComp;
import components.TransformationComp;
import content.ContentManager;

import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;

public class BulletAbility extends AbstractAbilityProcessor{
	
	private Audio soundEffect;
	private final float bulletSpeed = 10;
	
	
	public BulletAbility(Audio soundEffect){
		super(Abilities.Bullet, TransformationComp.class);

		this.soundEffect = soundEffect;
	}

	@Override
	public void processEntity(IEntity sourceEntity, IEntityManager manager, IEntityArchetype specialArchtype) {
		
		if(specialArchtype != null) {
			createSpecialBullet(sourceEntity, manager, specialArchtype);
		} else {
			createDefaultBullet(sourceEntity, manager);
		}
		
	}


	private void createDefaultBullet(IEntity sourceEntity,
			IEntityManager manager) {

		IEntity bullet = manager.createEmptyEntity();
		
		RenderingComp rendComp = new RenderingComp();
		rendComp.setTexture(ContentManager.loadTexture("Circle100pxGrey.png"));
		TransformationComp transComp = new TransformationComp();
		transComp.setPosition(sourceEntity.getComponent(TransformationComp.class).getPosition());
		transComp.setOrigin(new Vector2(rendComp.getTexture().getBounds().Width/2,rendComp.getTexture().getBounds().Height/2));
		Vector2 targetPos = sourceEntity.getComponent(InputComp.class).getMousePosition();
		ExistanceComp existanceComp = new ExistanceComp(1f);
		
		
		PhysicsComp physComp = new PhysicsComp();
		bullet.addComponent(physComp);
		
		Vector2 attackSpeed = Vector2.subtract(targetPos, transComp.getPosition());
		attackSpeed = Vector2.norm(attackSpeed);
		
		bullet.addComponent(transComp);
		bullet.addComponent(rendComp);
		bullet.addComponent(existanceComp);
		bullet.addComponent(new AttackComp(sourceEntity, new Circle(Vector2.Zero, 30), 10, true));
		
		bullet.getComponent(AttackComp.class).setSourceEntity(sourceEntity);
		bullet.getComponent(TransformationComp.class).setRotation(attackSpeed.angle());
		bullet.getComponent(TransformationComp.class).setPosition(Vector2.add(transComp.getPosition(), Vector2.mul(60, attackSpeed)));
		bullet.getComponent(PhysicsComp.class).setVelocity(Vector2.mul(bulletSpeed, attackSpeed));
		

		soundEffect.playAsSoundEffect(1.0f, 0.5f, false);
		
		bullet.refresh();
	}

	private void createSpecialBullet(IEntity sourceEntity,
			IEntityManager manager, IEntityArchetype specialArchtype) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Not implemented yet!");
	}

	@Override
	public void initialize(IEntityManager manager) {
		// TODO Auto-generated method stub
		
	}

}
