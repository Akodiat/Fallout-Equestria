package entitySystems;

import utils.*;
import components.*;
import content.ContentManager;
import math.*;
import entityFramework.*;
import graphics.*;

public class HUDRenderingSystem extends LabelEntitySystem {
	private SpriteBatch batch;
	private Vector2 healthPos;
	private Vector2 apPos;
	
	private Texture2D healthBarBackGround;
	private Texture2D healthBarFill;

	public HUDRenderingSystem(IEntityWorld world, SpriteBatch batch,
			String playerLabel) {
		super(world, playerLabel, HealthComponent.class, ActionPointsComponent.class);
		this.batch = batch;
		this.healthPos = new Vector2(100, 100);
		this.apPos = new Vector2(100,140);
		
		this.healthPos = new Vector2(batch.getViewport().Width/16 -50,batch.getViewport().Height -200);
		this.apPos = new Vector2(batch.getViewport().Width/16 -50,batch.getViewport().Height -50);
	}

	private ComponentMapper<HealthComponent> healthCM;
	private ComponentMapper<ActionPointsComponent> apCM;

	@Override
	public void initialize() {
		healthCM = ComponentMapper.create(this.getWorld().getDatabase(),
				HealthComponent.class);
		apCM = ComponentMapper.create(this.getWorld().getDatabase(),
				ActionPointsComponent.class);
		
		healthBarBackGround = ContentManager.loadTexture("HealthBarBG.png");
		healthBarFill = ContentManager.loadTexture("HealthBarFill.png");
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
		/*OLD CODE:
		Rectangle rectHealth = new Rectangle((int) this.healthPos.X,
				(int) this.healthPos.Y, (int) (100.0f * ratio), 20);
		this.batch.draw(Texture2D.getPixel(), rectHealth, Color.Green, null);
		*/
		Rectangle rectHealthBG = new Rectangle((int) this.healthPos.X,
				(int) this.healthPos.Y, healthBarBackGround.Width, healthBarBackGround.Height);
		this.batch.draw(this.healthBarBackGround, rectHealthBG, Color.White, null);
		
		Rectangle rectHealthFill = new Rectangle((int) this.healthPos.X - 5,
				(int) this.healthPos.Y, healthBarBackGround.Width,healthBarBackGround.Height);
		Rectangle rectHealthToActuallyFill = new Rectangle(-2,
				25, (int)((healthBarBackGround.Width - 82)*ratio),healthBarBackGround.Height);
		this.batch.draw(this.healthBarFill, rectHealthFill, Color.White, rectHealthToActuallyFill);
	}

	@Override
	protected void processMissingEntity() {
		// TODO Auto-generated method stub
		//Question should something be done here? Yes no maby so? IDK i rly don't know. 
		//Should this method 
	}

}
