package common;

import components.PhysicsComp;
import components.TransformationComp;

public class EntityMovedMessage extends NetworkMessage{
	public TransformationComp newTransfComp;
	public PhysicsComp newPhysComp;
	public int entityID;
}
