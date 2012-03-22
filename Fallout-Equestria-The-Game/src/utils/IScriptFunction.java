package utils;

/**
 * 
 * @author Lukas Kurtyan
 *
 */
public interface IScriptFunction {

	/** The function this ScriptFunction controls.
	 * 
	 * @param params the arguments of the function.
	 */
	public void excecute(String[] params);
	
	/**The name that will be represented in the script.
	 * 
	 * @return a string name of a function.
	 */
	public String getFunctionName();
}
