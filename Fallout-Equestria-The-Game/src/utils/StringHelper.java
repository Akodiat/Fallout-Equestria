package utils;

import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Splitter;

public class StringHelper {
	
	public static String[] split(String toSplit, char seperator) {
		List<String> strings = new LinkedList<>();
		for (String string : Splitter.on(seperator).trimResults().omitEmptyStrings().split(toSplit)) {
			strings.add(string);
		}

		return strings.toArray(new String[strings.size()]);
	}
	
	public static String insert(String text, int position, String input) {
		String result = text.substring(0, position);
		result += input + text.substring(position);
		
		return result;
	}
	
	public static String backspace(String text, int position) {
		String result = text.substring(0, position-1);
		result += text.substring(position);
		
		return result;
	}
}
