package scripting;


public class ConsoleScriptFunction implements IScriptFunction{

	@Override
	public void excecute(String[] params) {
		if(params.length != 1) {
			throw new ScriptException();
		}
		
		System.out.println(params[0]);	
		
	}

	@Override
	public String getFunctionName() {
		return "cout";
	}

}
