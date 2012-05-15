package common;

import components.TransformationComp;

public class EntityCreatedMessage extends NetworkMessage{

	public String entityArchetypePath;
	public int networkID;
	public TransformationComp transComp;
}
