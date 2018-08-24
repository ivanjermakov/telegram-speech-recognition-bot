package com.gmail.ivanjermakov1.telegramspeechrecognitionbot.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
	
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String now() {
		return format.format(new Date());
	}
	
	public static String now(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}
	
}
