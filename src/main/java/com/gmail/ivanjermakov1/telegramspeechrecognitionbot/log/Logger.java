package com.gmail.ivanjermakov1.telegramspeechrecognitionbot.log;

import com.gmail.ivanjermakov1.telegramspeechrecognitionbot.util.DateFormat;
import com.gmail.ivanjermakov1.telegramspeechrecognitionbot.util.ReflectionUtils;
import com.gmail.ivanjermakov1.telegramspeechrecognitionbot.util.StringUtils;

import static com.gmail.ivanjermakov1.telegramspeechrecognitionbot.util.StringUtils.TrimmingEnd.START;

public class Logger {
	
	public static void info(String log) {
		StringBuilder builder = new StringBuilder();
		builder.append(DateFormat.now())
				.append(" ")
				.append(StringUtils.trim(ReflectionUtils.getInvokerClass("info").getName(), 55, START))
				.append(StringUtils.fill(builder.toString(), 80))
				.append(" [INFO] ")
				.append(": ")
				.append(log);
		
		System.out.println(builder.toString());
	}
	
	public static void warn(String log) {
		StringBuilder builder = new StringBuilder();
		builder.append(DateFormat.now())
				.append(" ")
				.append(StringUtils.trim(ReflectionUtils.getInvokerClass("warn").getName(), 55, START))
				.append(StringUtils.fill(builder.toString(), 80))
				.append(" [WARN] ")
				.append(": ")
				.append(log);
		
		System.out.println(builder.toString());
	}
	
}

