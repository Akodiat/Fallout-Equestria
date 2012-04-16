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
	
}
