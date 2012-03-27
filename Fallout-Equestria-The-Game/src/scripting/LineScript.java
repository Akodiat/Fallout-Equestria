package scripting;


public class LineScript implements ILineScript{

	private int currentLine;
	private final String[] lines;
	
	public LineScript(String[] lines) {
		this.lines = lines.clone();
		this.currentLine = 0;
	}
	
	@Override
	public String getCurrentLine() {
		return lines[currentLine];
	}

	@Override
	public void gotoNextLine() {
		currentLine = (currentLine + 1) % (this.lines.length);
	}

	@Override
	public void gotoStart() {
		currentLine = 0;
	}

	@Override
	public String[] getAllLines() {
		return this.lines.clone();
	}

	public String toString() {
		String script = "";
		for (String line : this.lines) {
			script += line + "\n";
		}
		
		return "Script: " + "\n"
			+  "CurrentLin: " + this.currentLine + "\n"
			+  "Script: \n" + script;
	}
}
