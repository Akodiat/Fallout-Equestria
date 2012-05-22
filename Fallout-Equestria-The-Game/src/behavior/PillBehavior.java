package behavior;

import java.util.Set;

import animation.AnimationPlayer;
import anotations.Editable;

import components.AnimationComp;
import components.TimerComp;
import components.TransformationComp;

import entityFramework.IEntity;

import math.Vector2;
import misc.EntityGroups;
import utils.IEventListener;
import utils.TimerEventArgs;
import utils.time.GameTime;
import utils.time.Timer;

public class PillBehavior extends Behavior {
	private static final String IDLE_STATE = "idle";
	private static final String SIGHT_STATE = "sight";
	private static final float sightRange = 300f;
	private static final float drugDuration = 3.0f;
	
	private @Editable String cactuarAnimSetName = "cactuar.animset";
	
	
	private AnimationPlayer animationPlayer;
	
	
	@Override
	public void start() {
		this.StateMachine.registerState(IDLE_STATE, new IdleState());
		this.StateMachine.registerState(SIGHT_STATE, new PlayerInSightState());
		this.StateMachine.changeState(IDLE_STATE);
		
		this.animationPlayer = this.ContentManager.loadAnimationSet(cactuarAnimSetName);
	}

	@Override
	public Object clone() {
		return new PillBehavior();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCollisionEnter(IEntity entity) {
		if(!entity.isInGroup(EntityGroups.Players.toString()))
			return;
		
		//Change everything to catuars here. Also kill pill.
		Set<IEntity> entities = this.EntityManager.getAllEntitysContainingComponents(AnimationComp.class);
		for (IEntity entity2 : entities) {
			final AnimationComp animComp = entity2.getComponent(AnimationComp.class);
			final AnimationPlayer oldPlayer = animComp.getAnimationPlayer();			
			
			animComp.setAnimationPlayer(this.animationPlayer.clone());
			
			TimerComp comp = entity2.getComponent(TimerComp.class);
			if(comp == null) 
			{
				comp = new TimerComp();
				entity2.addComponent(comp);
				entity2.refresh();
			}
			Timer timer = new Timer(1,drugDuration);
			timer.addCompleteListener(new IEventListener<TimerEventArgs>() {
				
				@Override
				public void onEvent(Object sender, TimerEventArgs e) {
					animComp.setAnimationPlayer(oldPlayer);
				}
			});
			timer.start();
			comp.addTimer(timer);
		}
		
		this.Entity.kill();
	}
	
	private class IdleState extends BehaviourState {
		
		@Override
		protected void enter() {
			AnimationComp animComp = getComponent(AnimationComp.class);
			animComp.changeAnimation("idle", false);
		}
		
		@Override
		public void update(GameTime time) {
			Set<IEntity> entities = EntityManager.getEntityGroup(EntityGroups.Players.toString());
			for (IEntity entity : entities) {
				if(withinRange(entity)) {
					StateMachine.changeState(SIGHT_STATE);
				}				
			}
		}

	
		
	}
	
	private boolean withinRange(IEntity entity) {
		TransformationComp other = entity.getComponent(TransformationComp.class);
		TransformationComp ours = PillBehavior.this.Entity.getComponent(TransformationComp.class);
		
		return Vector2.distance(other.getPosition(), ours.getPosition()) < PillBehavior.sightRange;
	}
	
	private class PlayerInSightState extends BehaviourState {
		@Override
		protected void enter() {
			AnimationComp animComp = getComponent(AnimationComp.class);
			animComp.changeAnimation("walk", false);
		}
		
		@Override
		public void update(GameTime time) {
			Set<IEntity> entities = EntityManager.getEntityGroup(EntityGroups.Players.toString());
			boolean result = false;
			for (IEntity entity : entities) {	
				if(withinRange(entity)) {
					result = true;
				}				
			}
			
			if(!result) {
				StateMachine.changeState(IDLE_STATE);
			}
		}
	}

}
