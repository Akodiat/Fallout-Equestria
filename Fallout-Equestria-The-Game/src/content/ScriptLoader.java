package content;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import scripting.ILineScript;
import scripting.LineScript;

import com.google.common.base.Splitter;

public class ScriptLoader implements IContentLoader<ILineScript>{
	

	private String[] extractCommands(String rawScript) {
		Iterable<String> iterator = Splitter.on(';')
									.trimResults()
									.omitEmptyStrings()
									.split(rawScript);
		List<String> commands = new ArrayList<String>();
		for (String command : iterator) {
			commands.add(command);
		}
		
		String[] tmp = new String[commands.size()];
		commands.toArray(tmp);
		return tmp;
	}

	private String readRawScript(BufferedReader reader) throws IOException {
		StringBuilder builder = new StringBuilder();
		
		String line;
    	while ((line = reader.readLine()) != null) {
    		builder.append(line).append("\n");
    	}
    	
    	return builder.toString();
	}

	@Override
	public Class<ILineScript> getClassAbleToLoad() {
		return ILineScript.class;
	}

	@Override
	public ILineScript loadContent(InputStream in) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		String rawScript = readRawScript(reader);	
		String[] commands = extractCommands(rawScript);
		
		return new LineScript(commands);
	}

	@Override
	public String getFoulder() {
		return "scripts";
	}
}
