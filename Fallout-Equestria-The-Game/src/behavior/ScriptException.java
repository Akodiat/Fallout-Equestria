package behavior;

public class ScriptException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ScriptException() {
		super("There was an error in the script!");
	}
	
	public ScriptException(String message) {
		super(message);
	}
}
