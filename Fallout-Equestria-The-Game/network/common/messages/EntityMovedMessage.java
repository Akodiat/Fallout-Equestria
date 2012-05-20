package common.messages;

import components.TransformationComp;

public class EntityMovedMessage extends NetworkMessage{
	public TransformationComp newTransfComp;
	public int entityID;
}
