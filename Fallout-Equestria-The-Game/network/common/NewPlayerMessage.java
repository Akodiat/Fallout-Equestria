package common;

import components.SpecialComp;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class NewPlayerMessage extends NetworkMessage{
	public SpecialComp specialComp;
	public PlayerCharacteristics playerCharacteristics;
	public int senderID;
}
