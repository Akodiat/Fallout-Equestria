package entitySystems;

import components.HealthComponent;

import math.Vector2;
import entityFramework.ComponentMapper;
import entityFramework.EntitySystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import graphics.SpriteBatch;
import graphics.Texture2D;

public class HUDRenderingSystem extends EntitySystem {

	private final String playerLabel;
	private Texture2D pixel;
	private SpriteBatch batch;
	private Vector2 healthPos;
	private Vector2 actionPos;
	
	protected HUDRenderingSystem(IEntityWorld world, SpriteBatch batch, String playerLabel) {
		super(world);
		this.batch = batch;
		this.playerLabel = playerLabel;		
	}

	private ComponentMapper<HealthComponent> healthCM;
	
	@Override
	public void initialize() {
		healthCM = ComponentMapper.create(this.getWorld().getDatabase(), HealthComponent.class);
		
	}

	@Override
	public void process() {
		IEntity entity = this.getWorld().getEntityManager().getEntity("PLAYER");
	}

}
