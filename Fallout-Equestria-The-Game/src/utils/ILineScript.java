package utils;

/** 
 * 
 * @author Lukas Kurtyan
 *
 */
public interface ILineScript {
	
	/** Gets the current line in the script to be excecuted.
	 * 
	 * @return the current line in the script.
	 */
	public String getCurrentLine();
	
	/**Gets all the lines in the script.
	 * 
	 * @return all the lines in sequence of the script.
	 */
	public String[] getAllLines();
	
	/**
	 * Goes to the next line in the script.
	 */
	public void gotoNextLine();
	
	/**
	 * Goes to the start of the script.
	 */
	public void gotoStart();
}
