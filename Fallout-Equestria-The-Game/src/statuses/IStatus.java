package statuses;

public interface IStatus {
	
	/**
	 * Method to activate the effect of a status.
	 */
	public void activateStatusEffect();
	
	/**
	 * Method to deactivate the effect of a status.
	 */
	public void deactivateStatusEffect();
	
	/**
	 * Method to update the status's effect each tick.
	 */
	public void update();
}
