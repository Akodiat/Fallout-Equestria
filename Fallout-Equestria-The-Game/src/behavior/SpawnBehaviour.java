package behavior;

import java.util.Random;

import components.TransformationComp;

import math.Vector2;
import misc.EntityGroups;

import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import anotations.Editable;

public class SpawnBehaviour extends Behavior{
	private static Vector2 SpawnArea = new Vector2(1000, 300);
	private static float height = 1200f;
	
	private @Editable String archtypeID;
	private @Editable int numEntities;
	private IEntityArchetype archetype;
	private Random random;
	
	
	public SpawnBehaviour(String archetypeID, int numEntities) {
		this.archtypeID = archetypeID;
		this.numEntities = numEntities;
	}
	
	public SpawnBehaviour(SpawnBehaviour spawnBehaviour) {
		this.archtypeID = spawnBehaviour.archtypeID;
		this.numEntities = spawnBehaviour.numEntities;
	}

	@Override
	public void start() {
		archetype = this.ContentManager.loadArchetype(archtypeID);
		this.random = new Random();
	}

	@Override
	public Object clone() {
		return new SpawnBehaviour(this);
	}

	@Override
	public void onTriggerEnter(IEntity entity) {
		if(!entity.isInGroup(EntityGroups.Players.toString()))
			return;
		
		
		for (int i = 0; i < this.numEntities; i++) {
			Vector2 randomPos = createRandomPos();
			IEntity spawnedEntity = this.EntityManager.createEntity(this.archetype);
			spawnedEntity.getComponent(TransformationComp.class).setPosition(randomPos);
			spawnedEntity.getComponent(TransformationComp.class).setHeight(height);
			
		}
	}

	private Vector2 createRandomPos() {
		float dx = random.nextFloat() * SpawnArea.X - SpawnArea.X / 2;
		float dy = random.nextFloat() * SpawnArea.Y - SpawnArea.Y / 2;
		
		TransformationComp trans = this.Entity.getComponent(TransformationComp.class);
		
		return new Vector2(trans.getPosition().X + dx, trans.getPosition().Y + dy);
	}
}
