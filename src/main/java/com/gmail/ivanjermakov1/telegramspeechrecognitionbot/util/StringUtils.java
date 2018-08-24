package com.gmail.ivanjermakov1.telegramspeechrecognitionbot.util;

import java.util.stream.IntStream;

public class StringUtils {
	
	public enum TrimmingEnd {
		START,
		END
	}
	
	public static String trim(String string, int to, TrimmingEnd end) {
		if (string.length() <= to) return string;
		StringBuilder result = new StringBuilder();
		switch (end) {
			case END:
				IntStream.range(0, to)
						.forEach(i -> result.append(string.charAt(i)));
				result.append("...");
				break;
			case START:
				result.append("...");
				IntStream.range(string.length() - to, string.length())
						.forEach(i -> result.append(string.charAt(i)));
				break;
		}
		
		return result.toString();
	}
	
	/**
	 * @return line of spaces of length toFill - string.length()
	 */
	public static String fill(String string, int toFill) {
		int left = toFill - string.length();
		
		if (left < 0) throw new UnsupportedOperationException("Line is already longer");
		
		StringBuilder result = new StringBuilder();
		IntStream.range(0, left)
				.forEach(i -> result.append(" "));
		
		return result.toString();
	}
	
	public static String rounded(double value, int decimalPlaces) {
		return String.format("%." + decimalPlaces + "f", value);
	}
	
}

