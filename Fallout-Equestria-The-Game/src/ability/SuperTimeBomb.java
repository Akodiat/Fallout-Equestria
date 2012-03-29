package ability;

import utils.Rectangle;
import math.Vector2;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;

public class SuperTimeBomb extends Ability{

	private TimeBombAbility timeBombAbility;
	private final int numBombs;
	private final Rectangle abilityBounds;
	
	public SuperTimeBomb(IEntityArchetype explostionArchetype,IEntityArchetype countDownArch, Rectangle abilityBounds, int numBombs, int apCost, float cooldown) {
		super(apCost, cooldown);
		this.timeBombAbility = new TimeBombAbility(explostionArchetype, countDownArch, 0, 0);
		this.numBombs = numBombs;
		this.abilityBounds = abilityBounds;
	}

	@Override
	protected void use(IEntity sourceEntity, Vector2 targetPos,
			IEntityManager manager) {
		
		timeBombAbility.use(sourceEntity, targetPos, manager);
		
		for (int i = 0; i < this.numBombs - 1; i++) {
			float randX, randY;
			randX = abilityBounds.X + (float)Math.random() * abilityBounds.Width;
			randY = abilityBounds.Y + (float)Math.random() * abilityBounds.Height;
			timeBombAbility.use(sourceEntity, new Vector2(randX, randY), manager);
		}
	}

}
