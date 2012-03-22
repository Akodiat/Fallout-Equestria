package entitySystems;

import java.io.IOException;

import com.google.common.collect.ImmutableSet;
import components.HealthComponent;
import components.RenderingComponent;
import components.TransformationComp;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import graphics.Color;
import graphics.SpriteBatch;
import graphics.Texture2D;
import utils.ContentManager;
import utils.Rectangle;

public class HealthBarRenderSystem extends EntityProcessingSystem {

	private SpriteBatch spriteBatch;

	public HealthBarRenderSystem(IEntityWorld world, SpriteBatch graphics) {
		super(world, HealthComponent.class, RenderingComponent.class,
				TransformationComp.class);
		this.spriteBatch = graphics;
	}

	private ComponentMapper<HealthComponent> hCM;
	private ComponentMapper<RenderingComponent> rCM;
	private ComponentMapper<TransformationComp> tCM;

	@Override
	public void initialize() {
		hCM = ComponentMapper.create(this.getWorld().getDatabase(),
				HealthComponent.class);
		rCM = ComponentMapper.create(this.getWorld().getDatabase(),
				RenderingComponent.class);
		tCM = ComponentMapper.create(this.getWorld().getDatabase(),
				TransformationComp.class);
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			HealthComponent healthC = this.hCM.getComponent(entity);
			RenderingComponent renderC = this.rCM.getComponent(entity);
			TransformationComp positionC = this.tCM.getComponent(entity);

			Rectangle healthBar = new Rectangle(
					(int) (-healthC.getHealthPoints() / 2
							+ positionC.getPosition().X + (renderC.getTexture().Width * positionC
							.getScale().X) / 2), (int) (-10 + positionC
							.getPosition().Y), (int) healthC.getHealthPoints(),
					10);
			Rectangle border = new Rectangle(healthBar.X - 1, healthBar.Y - 1,
					healthBar.Width + 2, healthBar.Height + 2);
			this.spriteBatch.draw(Texture2D.getPixel(), border, Color.Black,
					null);

			float healthPercentage = healthC.getHealthPoints()
					/ healthC.getMaxHealth();

			try {
				Texture2D texture = ContentManager
						.loadTexture("HealthBarUnit.png");
				if (healthPercentage >= 0.5) {
					this.spriteBatch
							.draw(texture, healthBar, new Color(1 -2*(healthPercentage -0.5f), 1,0,1), null);
				}else {
					this.spriteBatch.draw(texture, healthBar, new Color(1, 2*healthPercentage,0,1), null);
				}
			} catch (IOException e) {
				throw new Error("Fuck you. (HealthBarUnit.png is missing)");
			}

		}
	}
}
