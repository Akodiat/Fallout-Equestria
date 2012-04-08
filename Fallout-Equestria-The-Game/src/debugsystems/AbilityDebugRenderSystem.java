package debugsystems;

import math.Matrix4;
import math.Vector2;
import utils.Rectangle;
import components.WeaponComp;
import content.ContentManager;

import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import entityFramework.LabelEntitySystem;
import graphics.Color;
import graphics.ShaderEffect;
import graphics.SpriteBatch;
import graphics.TextureFont;

public class AbilityDebugRenderSystem extends LabelEntitySystem{

	private final static String fontAsset = "arialb20.xml";
	private final SpriteBatch spriteBatch;
	private TextureFont font;
	
	public AbilityDebugRenderSystem(IEntityWorld world, String label, SpriteBatch batch) {
		super(world, label, WeaponComp.class);
		this.spriteBatch = batch;
	}

	@Override
	public void initialize() {
		font = ContentManager.loadFont(fontAsset);
	}

	@Override
	protected void processEntity(IEntity entity) {
		WeaponComp comp = entity.getComponent(WeaponComp.class);
		String s = "The ability in use is " + comp.getPrimaryAbility().toString();
		
		Rectangle screenBounds = this.spriteBatch.getViewport();
		
		Vector2 position = new Vector2(screenBounds.Width - font.meassureString(s).X - 100, 40);
		
		ShaderEffect effect = this.spriteBatch.getActiveEffect();
		Matrix4 view = this.spriteBatch.getView();
		this.spriteBatch.end();
		this.spriteBatch.begin();
		
		this.spriteBatch.drawString(font, s, position, Color.Red);
		
		this.spriteBatch.end();
		this.spriteBatch.begin(effect, view);
		
	}

	@Override
	protected void processMissingEntity() {
		// TODO Auto-generated method stub
		
	}

}
