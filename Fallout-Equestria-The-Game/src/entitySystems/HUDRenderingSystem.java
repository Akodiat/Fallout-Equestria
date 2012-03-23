package entitySystems;

import utils.Rectangle;
import components.ActionPointsComponent;
import components.HealthComponent;

import math.Matrix4;
import math.Vector2;
import entityFramework.ComponentMapper;
import entityFramework.EntitySystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import graphics.Color;
import graphics.ShaderEffect;
import graphics.SpriteBatch;
import graphics.Texture2D;

public class HUDRenderingSystem extends EntitySystem {

	private final String playerLabel;
	private SpriteBatch batch;
	private Vector2 healthPos;
	private Vector2 apPos;

	public HUDRenderingSystem(IEntityWorld world, SpriteBatch batch,
			String playerLabel) {
		super(world);
		this.batch = batch;
		this.playerLabel = playerLabel;
		this.healthPos = new Vector2(100, 100);
		this.apPos = new Vector2(100,140);
	}

	private ComponentMapper<HealthComponent> healthCM;
	private ComponentMapper<ActionPointsComponent> apCM;
	

	@Override
	public void initialize() {
		healthCM = ComponentMapper.create(this.getWorld().getDatabase(),
				HealthComponent.class);
		apCM = ComponentMapper.create(this.getWorld().getDatabase(),
				ActionPointsComponent.class);
		

	}

	@Override
	public void process() {
		IEntity entity = this.getWorld().getEntityManager()
				.getEntity(this.playerLabel);
		
		// TODO remove this ugly if statement and handle player death in some
		// other manor.
		if (entity != null) {
			HealthComponent comp = this.healthCM.getComponent(entity);
			float ratio = comp.getHealthPoints() / comp.getMaxHealth();

			this.batch.end();
			ShaderEffect effect = this.batch.getActiveEffect();
			Matrix4 view = this.batch.getView();

			this.batch.begin(effect, Matrix4.Identity);

			Rectangle rectHealth = new Rectangle((int) this.healthPos.X,
					(int) this.healthPos.Y, (int) (100.0f * ratio), 20);
			this.batch.draw(Texture2D.getPixel(), rectHealth, Color.White, null);

			ActionPointsComponent actionComp = this.apCM.getComponent(entity);
			ratio = actionComp.getAbilityPoints() / actionComp.getMaxAbilityPoints();
			
			Rectangle rectAp = new Rectangle((int) this.apPos.X,
					(int) this.apPos.Y, (int) (100.0f * ratio), 20);
			this.batch.draw(Texture2D.getPixel(), rectAp, Color.Gold, null);
			
			
			
			this.batch.end();
			this.batch.begin(effect, view);
			
			
			

		}

	}

}
