package entitySystems;

import com.google.common.collect.ImmutableSet;
import components.HealthComponent;
import components.RenderingComponent;
import components.TransformationComp;
import content.ContentManager;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import graphics.Color;
import graphics.SpriteBatch;
import graphics.Texture2D;
import utils.Rectangle;

public class HealthBarRenderSystem extends EntityProcessingSystem {

	private SpriteBatch spriteBatch;
	private Texture2D healthBarTexture;

	public HealthBarRenderSystem(IEntityWorld world, SpriteBatch graphics) {
		super(world, HealthComponent.class, RenderingComponent.class,
				TransformationComp.class);
		this.spriteBatch = graphics;
	}

	private ComponentMapper<HealthComponent> hCM;
	private ComponentMapper<TransformationComp> tCM;

	@Override
	public void initialize() {
		hCM = ComponentMapper.create(this.getWorld().getDatabase(),
				HealthComponent.class);
		tCM = ComponentMapper.create(this.getWorld().getDatabase(),
				TransformationComp.class);

		healthBarTexture = ContentManager.loadTexture("HealthBarUnit.png");
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			HealthComponent healthC = this.hCM.getComponent(entity);
			TransformationComp positionC = this.tCM.getComponent(entity);

			Rectangle healthBar = new Rectangle(
					(int) (-healthC.getHealthPoints() / 2 + positionC
							.getPosition().X),
					(int) (-10 - positionC.getOrigin().Y
							* positionC.getScale().Y + positionC.getPosition().Y),
					(int) healthC.getHealthPoints(), 10);
			Rectangle border = new Rectangle(healthBar.X - 1, healthBar.Y - 1,
					healthBar.Width + 2, healthBar.Height + 2);
			this.spriteBatch.draw(Texture2D.getPixel(), border, Color.Black,
					null);

			float healthPercentage = healthC.getHealthPoints()
					/ healthC.getMaxHealth();
			Color color;
			if (healthPercentage >= 0.5) {
				color = new Color(1 - 2 * (healthPercentage - 0.5f), 1, 0, 1);
			} else {
				color = new Color(1, 2 * healthPercentage, 0, 1);
			}

			this.spriteBatch
					.draw(this.healthBarTexture, healthBar, color, null);
		}
	}
}
