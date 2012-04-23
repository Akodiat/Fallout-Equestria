package ability;

import org.newdawn.slick.openal.Audio;

import math.Vector2;
import utils.Circle;
import components.HealingComp;
import components.InputComp;
import components.RenderingComp;
import components.SpecialComp;
import content.ContentManager;

import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;
import graphics.Color;

public class HealAbility extends AbstractAbilityProcessor{
	private Audio sfx;
	
	public HealAbility(Audio soundEffect) {
		super(Abilities.Healing, HealingComp.class, InputComp.class);
		
		this.sfx = soundEffect;
	}

	@Override
	public void processEntity(IEntity sourceEntity, IEntityManager manager, IEntityArchetype specialArchtype) {
		int intelligence = sourceEntity.getComponent(SpecialComp.class).getIntelligence();
		int strength = sourceEntity.getComponent(SpecialComp.class).getStrength();
		
		Vector2 mouseClickPos = sourceEntity.getComponent(InputComp.class).getMousePosition();
		
		IEntity healingCircle = manager.createEmptyEntity();
		
		RenderingComp rendComp = new RenderingComp();
		rendComp.setTexture(ContentManager.loadTexture("Circle100pxGrey.png"));
		rendComp.setColor(new Color(Color.Yellow, 0.5f));
		
		HealingComp healComp = new HealingComp(new Circle(mouseClickPos, 10*strength), 10*intelligence);
		
		healingCircle.addComponent(rendComp);
		healingCircle.addComponent(healComp);
		
		healingCircle.refresh();
		
		sfx.playAsSoundEffect(1.0f, 0.5f, false);
	}

	@Override
	public void initialize(IEntityManager manager) {
		// TODO Auto-generated method stub
		
	}

}
