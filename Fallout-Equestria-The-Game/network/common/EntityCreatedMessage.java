package common;

import components.PhysicsComp;
import components.TransformationComp;

public class EntityCreatedMessage {

	public String entityArchetypePath;
	public int networkID;
	public int localClientID;
	public TransformationComp transComp;
	public PhysicsComp physComp;
	
}
