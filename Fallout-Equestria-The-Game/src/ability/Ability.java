package ability;

import components.ActionPointsComponent;
import math.Vector2;
import utils.ITimerListener;
import utils.Timer;
import entityFramework.IEntity;
import entityFramework.IEntityManager;

public abstract class Ability{

	protected Timer timer;
	protected int apCost; //The cost of the ability 
	protected boolean busy;
	
	public Ability(int apCost, float cooldown){
		this.apCost = apCost;
		this.timer = new Timer(cooldown, 1);
		this.timer.addEventListener(new TimerImplementation());
	}
	
	protected boolean canUse(IEntity sourceEntity, Vector2 targetPos, IEntityManager manager, ActionPointsComponent apComp){
		return !busy && apComp.getAbilityPoints() >= apCost;
	}
	
	public final void useAbility(IEntity sourceEntity, Vector2 targetPos, IEntityManager manager) {
		ActionPointsComponent apComp = sourceEntity.getComponent(ActionPointsComponent.class);
		if(apComp != null && canUse(sourceEntity, targetPos, manager, apComp)) {
			timer.Start();
			apComp.addAbilityPoints(-apCost);
			use(sourceEntity, targetPos, manager);
		}
	}
	
	protected abstract void use(IEntity sourceEntity, Vector2 targetPos, IEntityManager manager);	
	
	private class TimerImplementation implements ITimerListener {	
		
		public void Start() {
			busy=true;	
		}
		
		public void Tick() {
		}

		public void Complete() {
			busy=false;
		}
	}
}
