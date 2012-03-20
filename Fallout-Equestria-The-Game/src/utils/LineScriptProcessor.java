package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;

public class LineScriptProcessor implements ILineScriptProcessor{

	Map<String, IScriptFunction> avalibleFunctions;
	
	public LineScriptProcessor() {
		this.avalibleFunctions = new HashMap<>();
	}
	
	@Override
	public void registerScirptFunction(String scriptID, IScriptFunction function) {
		
		if(this.avalibleFunctions.containsKey(scriptID)) {
			throw new RuntimeException("The key " + scriptID + "is already pressent.");
		}
		
		this.avalibleFunctions.put(scriptID, function);
	}

	@Override
	public void unregisterScriptFunction(String scriptID) {
		this.avalibleFunctions.remove(scriptID);
	}

	@Override
	public void processLine(ILineScript script) {
		String scriptLine = script.getCurrentLine();
		script.gotoNextLine();
		
		List<String> function = this.getFuctionAsList(scriptLine);
		
		String functionID = function.remove(0);
		String[] functionArgs = new String[function.size()];
		function.toArray(functionArgs);
		
		this.avalibleFunctions.get(functionID).excecute(functionArgs);
	}
	
	private List<String> getFuctionAsList(String functionLine) {
		Iterable<String> spllitedLine = Splitter.on(',')
				.omitEmptyStrings()
				.trimResults()
				.split(functionLine);

		List<String> function  = new ArrayList<>();
		for (String string : spllitedLine) {
			function.add(string);
		}	
		
		return function;
	}

	@Override
	public void processLines(ILineScript script, int numLines) {
		for (int i = 0; i < numLines; i++) {
			this.processLine(script);
		}
	}

	@Override
	public void processEntireScript(ILineScript script) {
		String[] scriptLines = script.getAllLines();
		for (int i = 0; i < scriptLines.length; i++) {
			String scriptLine = scriptLines[i];
			
			List<String> function = this.getFuctionAsList(scriptLine);
			
			String functionID = function.remove(0);
			String[] functionArgs = new String[function.size()];
			function.toArray(functionArgs);
			
			this.avalibleFunctions.get(functionID).excecute(functionArgs);		
		}
	}

}
