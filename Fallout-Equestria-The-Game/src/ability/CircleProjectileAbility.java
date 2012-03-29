package ability;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import math.MathHelper;
import math.Vector2;
import components.AnimationComp;
import components.AttackComp;
import components.PhysicsComp;
import components.TransformationComp;

import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;

public class CircleProjectileAbility extends Ability{

	private Audio soundEffect;
	private final IEntityArchetype bulletArch;
	private final float bulletSpeed;
	private final int bulletAmount;


	public CircleProjectileAbility(IEntityArchetype bulletArch, int APCost, float shootingInterval, float bulletSpeed, int bulletAmount){
		super(APCost, shootingInterval);
		this.bulletArch = bulletArch;
		this.bulletSpeed = bulletSpeed;
		this.bulletAmount = bulletAmount;

		/*try {
   this.soundEffect = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("/resources/sound/effects/pew.ogg"));
  } catch (IOException e) {
   e.printStackTrace();
  }*/
	}

	@Override
	protected void use(IEntity sourceEntity, Vector2 targetPos, IEntityManager manager) {

		TransformationComp transComp = sourceEntity.getComponent(TransformationComp.class);

		Vector2 attackSpeed = Vector2.subtract(targetPos, transComp.getPosition());
		attackSpeed = Vector2.norm(attackSpeed);
		for (int i = 0; i<bulletAmount;i++){
			IEntity bullet = manager.createEntity(bulletArch);
			attackSpeed = Vector2.rotate(attackSpeed, (float)(MathHelper.TwoPi/bulletAmount));
			bullet.getComponent(AttackComp.class).setSourceEntity(sourceEntity);
			bullet.getComponent(TransformationComp.class).setRotation(attackSpeed.angle()+(float)(MathHelper.PiOver2));
			bullet.getComponent(TransformationComp.class).setPosition(Vector2.add(transComp.getPosition(), Vector2.mul(60, attackSpeed)));
			bullet.getComponent(PhysicsComp.class).setVelocity(Vector2.mul(bulletSpeed, attackSpeed));
			bullet.refresh();
		}

		//TODO enable once again. Later
		//soundEffect.playAsSoundEffect(1.0f, 0.5f, false);


	}

}
