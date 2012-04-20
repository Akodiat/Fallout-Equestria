package gameMap;

import math.Vector2;
import entityFramework.IEntityArchetype;

public class ArchetypeNode {
	private final IEntityArchetype archetype;
	private final Vector2 position;
	
	public ArchetypeNode(IEntityArchetype archtyep, Vector2 position) {
		this.archetype = archtyep;
		this.position = position;
	}

	public Vector2 getPosition() {
		return position;
	}

	public IEntityArchetype getArchetype() {
		return archetype;
	}
}
