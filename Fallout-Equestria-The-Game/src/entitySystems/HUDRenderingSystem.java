package entitySystems;

import utils.*;
import components.*;
import math.*;
import entityFramework.*;
import graphics.*;

public class HUDRenderingSystem extends LabelEntitySystem {
	private SpriteBatch batch;
	private Vector2 healthPos;
	private Vector2 apPos;

	public HUDRenderingSystem(IEntityWorld world, SpriteBatch batch,
			String playerLabel) {
		super(world, playerLabel, HealthComponent.class, ActionPointsComponent.class);
		this.batch = batch;
		this.healthPos = new Vector2(100, 100);
		this.apPos = new Vector2(100,140);
		
		this.healthPos = new Vector2(batch.getViewport().Width/2 -50,batch.getViewport().Height -100);
		this.apPos = new Vector2(batch.getViewport().Width/2 -50,batch.getViewport().Height -50);
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
	protected void processEntity(IEntity entity) {
		ShaderEffect effect = this.batch.getActiveEffect();
		Matrix4 view = this.batch.getView();

		this.batch.end();
		this.batch.begin(effect, Matrix4.Identity);

		drawHealthBar(entity);
		drawActionPointsBar(entity);
		
		this.batch.end();
		this.batch.begin(effect, view);	
	}

	private void drawActionPointsBar(IEntity entity) {
		ActionPointsComponent actionComp = this.apCM.getComponent(entity);
		
		float ratio = actionComp.getAbilityPoints() / actionComp.getMaxAbilityPoints();
		
		Rectangle rectAp = new Rectangle((int) this.apPos.X,
				(int) this.apPos.Y, (int) (100.0f * ratio), 20);

		this.batch.draw(Texture2D.getPixel(), rectAp, Color.Gold, null);
		
	}

	private void drawHealthBar(IEntity entity) {
		HealthComponent comp = this.healthCM.getComponent(entity);
		float ratio = comp.getHealthPoints() / comp.getMaxHealth();
		Rectangle rectHealth = new Rectangle((int) this.healthPos.X,
				(int) this.healthPos.Y, (int) (100.0f * ratio), 20);
		this.batch.draw(Texture2D.getPixel(), rectHealth, Color.Green, null);
	}

	@Override
	protected void processMissingEntity() {
		// TODO Auto-generated method stub
		//Question should something be done here? Yes no maby so? IDK i rly don't know. 
		//Should this method 
	}

}
