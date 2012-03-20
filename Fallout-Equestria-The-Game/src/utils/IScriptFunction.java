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
}
