package common.messages;

public class AnimationChangedMessage extends NetworkMessage{
	public int changedEntityNetworkID;
	public String newAnimation;
}
