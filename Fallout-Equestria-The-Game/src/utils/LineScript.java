package utils;

import java.util.Collection;

public class LineScript implements ILineScript{

	private int currentLine;
	private final String[] lines;
	
	public LineScript(Collection<String> lines) {
		this((String[])lines.toArray());
	}
	
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

}
