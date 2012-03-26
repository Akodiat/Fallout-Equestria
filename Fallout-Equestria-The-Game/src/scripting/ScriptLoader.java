package scripting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Splitter;

public class ScriptLoader {
	
	public ILineScript loadScript(InputStream stream) throws IOException {
	
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		String rawScript = readRawScript(reader);
		
		String[] commands = extractCommands(rawScript);
		
		return new LineScript(commands);
	}

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
}
