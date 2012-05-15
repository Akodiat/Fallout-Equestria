package common;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class HealthChangedMessage extends NetworkMessage{
	public int entityUniqueID;
	public int deltaHealth;
}
