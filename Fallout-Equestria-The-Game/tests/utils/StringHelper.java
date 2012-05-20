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
	
	public static String[] splitButDontTrim(String toSplit, char seperator) {
		List<String> strings = new LinkedList<>();
		for (String string : Splitter.on(seperator).omitEmptyStrings().split(toSplit)) {
			strings.add(string);
		}

		return strings.toArray(new String[strings.size()]);
	}
	
	
	public static String reverse(String toReverse) {
		StringBuilder builder = new StringBuilder();
		for (int i = toReverse.length() - 1; i >= 0; i--) {
			builder.append(toReverse.charAt(i));
		}
		return builder.toString();
	}
}
