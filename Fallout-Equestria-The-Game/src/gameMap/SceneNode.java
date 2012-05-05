package gameMap;

import math.Vector2;



public class SceneNode {
	private final String nodeID;
	private final Vector2 position;
	
	public SceneNode(String nodeID, Vector2 position) {
		this.nodeID = nodeID;
		this.position = position;
	}

	public Vector2 getPosition() {
		return position;
	}

	public String getNodeID() {
		return nodeID;
	}
}
