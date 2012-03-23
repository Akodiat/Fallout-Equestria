package scripting;


/**
 * 
 * @author Lukas Kurtyan 
 *
 */
public interface ILineScriptProcessor {

	/**Registers a function this ScriptProcessor can process. 
	 * 
	 * @param function the function(s) that can be executed by the script processor.
	 */
	public void registerScriptFunction( IScriptFunction function);
	
	/**Removes the specified identifier from being able to execute from the script.
	 * 
	 * @param scriptID the identifier that will be specified inside the scripts.
	 */
	public void unregisterScriptFunction(String scriptID);
	
	/**Processes the current line of the script.
	 * 
	 * @param script the script processing.
	 */
	public void processLine(ILineScript script);
	
	
	/**Processes lines from the script. 
	 * 
	 * @param script the script processing.
	 * @param numLines the number of lines to be processed.
	 */
	public void processLines(ILineScript script, int numLines);
	
	/**Process an entire LineScript.
	 * 
	 * @param script the lineScript processing.
	 */
	public void processEntireScript(ILineScript script);
}
