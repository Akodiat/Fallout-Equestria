package ability;

import utils.Circle;
import utils.Rectangle;

import math.MathHelper;
import math.Vector2;
import components.*;

import entityFramework.IEntity;
import entityFramework.IEntityManager;
import graphics.Color;
import graphics.Texture2D;

public class CircleProjectileAbility extends AbstractAbilityProcessor{

	private static final float bulletSpeed  = 5.0f;
	private static final int numBullets = 40;
	private static final int standardDMG = 1;
	private static final int radius = 10;
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
											  standardDMG * specialComp.getStrength(), 
											  true);
			float angle = (MathHelper.TwoPi * i) / numBullets;
			Vector2 dir = new Vector2((float)Math.cos(angle),
									  (float)Math.sin(angle));
			
			PhysicsComp physComp = new PhysicsComp(Vector2.mul(bulletSpeed, dir));
			RenderingComp nrComp = new RenderingComp(texture, Color.Red, null, new Rectangle(0,0,radius*2,radius*2));
			ExistanceComp neComp = new ExistanceComp(1);
			
			Vector2 nPos = Vector2.add(transComp.getPosition(), Vector2.mul(spatialComp.getBounds().getRadius() + radius, dir));
			ntComp.setPosition(nPos);
			ntComp.setOrigin(new Vector2(radius));
			
			IEntity nEntity = manager.createEmptyEntity();
			
			nEntity.addComponent(ntComp);
			nEntity.addComponent(physComp);
			nEntity.addComponent(aComp);
			nEntity.addComponent(nrComp);	
			nEntity.addComponent(neComp);
			
			nEntity.refresh();
		}

		
	}
}
