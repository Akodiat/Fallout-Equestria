package ability;

import math.Vector2;
import utils.ITimerListener;
import utils.Timer;
import entityFramework.IEntity;
import entityFramework.IEntityManager;

public abstract class Ability implements ITimerListener{

	protected Timer timer;
	protected int apCost; //The cost of the ability 
	protected boolean busy;
	
	public Ability(int apCost, float cooldown){
		this.apCost = apCost;
		this.timer = new Timer(cooldown, 1);
		this.timer.addEventListener(this);
	}
	public boolean canUse(IEntity sourceEntity, IEntityManager manager){
		return !busy;
	}
	public abstract void useAbility(IEntity sourceEntity, Vector2 targetPos, IEntityManager manager);
	public abstract void initialize();
	
	@Override
	public void Start() {
		busy=true;	
	}
	@Override
	public void Tick() {
	}
	@Override
	public void Complete() {
		busy=false;
	}
}
