package debugsystems;

import math.Vector2;

import com.google.common.collect.ImmutableSet;

import components.AttackComp;
import components.TransformationComp;
import content.ContentManager;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import graphics.Color;
import graphics.SpriteBatch;
import graphics.Texture2D;

public class DebugAttackRenderSystem extends EntityProcessingSystem {

	private Texture2D circleTexture;
	private SpriteBatch graphics;

	public DebugAttackRenderSystem(IEntityWorld world, SpriteBatch graphics) {
		super(world, AttackComp.class, TransformationComp.class);
		this.graphics = graphics;
	}

	private ComponentMapper<AttackComp> aCM;
	private ComponentMapper<TransformationComp> tCM;

	@Override
	public void initialize() {
		aCM = ComponentMapper.create(this.getWorld().getDatabase(),
				AttackComp.class);
		tCM = ComponentMapper.create(this.getWorld().getDatabase(),
				TransformationComp.class);

		this.circleTexture = ContentManager.loadTexture("Circle100pxGrey.png");
	}

	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			AttackComp attaCom = aCM.getComponent(entity);
			TransformationComp transCom = tCM.getComponent(entity);

			// Rectangle source = new
			// Rectangle((int)attaCom.getBounds().getPosition().X,(int)attaCom.getBounds().getPosition().Y,(int)attaCom.getBounds().getRadius()*2,(int)attaCom.getBounds().getRadius()*2);
			float scale = 2*attaCom.getBounds().getRadius() / 100;
			graphics.draw(circleTexture, transCom.getPosition(), new Color(Color.Red, 0.5f),
					null, new Vector2(attaCom.getBounds().getRadius()), scale, 0, false);

		}
	}

}
