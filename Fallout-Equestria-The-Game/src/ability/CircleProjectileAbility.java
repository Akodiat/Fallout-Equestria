package ability;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import utils.Circle;
import utils.Rectangle;

import math.MathHelper;
import math.Vector2;
import components.AnimationComp;
import components.AttackComp;
import components.PhysicsComp;
import components.RenderingComp;
import components.SpatialComp;
import components.SpecialComp;
import components.TransformationComp;

import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;
import entitySystems.RenderingSystem;
import graphics.Color;
import graphics.Texture2D;

public class CircleProjectileAbility extends AbstractAbilityProcessor{

	private static final float bulletSpeed  = 5.0f;
	private static final int numBullets = 10;
	private static final int standardDMG = 1;
	private static final int radius = 20;
	private static final Texture2D texture = Texture2D.getPixel();

	public CircleProjectileAbility() {
		super(Abilities.CircleBullet, TransformationComp.class, SpatialComp.class, SpecialComp.class);
	}
	
	@Override
	public void initialize(IEntityManager manager) {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void processEntity(IEntity entity, IEntityManager manager) {
		TransformationComp transComp = entity.getComponent(TransformationComp.class);
		SpatialComp spatialComp = entity.getComponent(SpatialComp.class);
		SpecialComp specialComp = entity.getComponent(SpecialComp.class);
		
		
		for (int i = 0; i< numBullets;i++){
			TransformationComp ntComp = new TransformationComp();
			AttackComp aComp = new AttackComp(entity, new Circle(Vector2.Zero, radius),
											  this.standardDMG * specialComp.getStrength(), 
											  true);
			Vector2 dir = new Vector2((float)Math.cos(MathHelper.TwoPi / i),
									  (float)Math.cos(MathHelper.TwoPi / i));
			
			PhysicsComp physComp = new PhysicsComp(Vector2.mul(bulletSpeed, dir));
			RenderingComp nrComp = new RenderingComp(texture, Color.White, null, new Rectangle(-radius,-radius,radius,radius));
			
			IEntity nEntity = manager.createEmptyEntity();
			
			nEntity.addComponent(ntComp);
			nEntity.addComponent(physComp);
			nEntity.addComponent(aComp);
			nEntity.addComponent(nrComp);	
			
			nEntity.refresh();
		}

		
	}
}
