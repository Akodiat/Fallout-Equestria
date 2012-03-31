package ability;

import org.newdawn.slick.openal.Audio;
import math.Vector2;
import components.AttackComp;
import components.InputComp;
import components.PhysicsComp;
import components.RenderingComp;
import components.TransformationComp;
import content.ContentManager;

import entityFramework.IEntity;
import entityFramework.IEntityManager;

public class BulletAbility extends AbstractAbilityProcessor{
	
	private Audio soundEffect;
	private final float bulletSpeed = 10;
	
	
	public BulletAbility(){
		super(Abilities.Bullet, TransformationComp.class);

		this.soundEffect = ContentManager.loadSound("effects/pew.ogg");
	}

	@Override
	public void processEntity(IEntity sourceEntity, IEntityManager manager) {
		IEntity bullet = manager.createEmptyEntity();
		
		RenderingComp rendComp = new RenderingComp();
		rendComp.setTexture(ContentManager.loadTexture("Circle100pxGrey.png"));
		TransformationComp transComp = new TransformationComp();
		transComp.setPosition(sourceEntity.getComponent(TransformationComp.class).getPosition());
		transComp.setOrigin(new Vector2(rendComp.getTexture().getBounds().Width/2,rendComp.getTexture().getBounds().Height/2));
		Vector2 targetPos = sourceEntity.getComponent(InputComp.class).getMousePosition();
		
		
		PhysicsComp physComp = new PhysicsComp();
		bullet.addComponent(physComp);
		
		Vector2 attackSpeed = Vector2.subtract(targetPos, transComp.getPosition());
		attackSpeed = Vector2.norm(attackSpeed);
		
		bullet.getComponent(AttackComp.class).setSourceEntity(sourceEntity);
		bullet.getComponent(TransformationComp.class).setRotation(attackSpeed.angle());
		bullet.getComponent(TransformationComp.class).setPosition(Vector2.add(transComp.getPosition(), Vector2.mul(60, attackSpeed)));
		bullet.getComponent(PhysicsComp.class).setVelocity(Vector2.mul(bulletSpeed, attackSpeed));
		

		soundEffect.playAsSoundEffect(1.0f, 0.5f, false);
		
		bullet.refresh();
	}


	@Override
	public void initialize(IEntityManager manager) {
		// TODO Auto-generated method stub
		
	}

}
