package common.messages;

import components.TransformationComp;

public class EntityCreatedMessage extends NetworkMessage{
	public String entityArchetypePath;
	public TransformationComp transComp;
}
