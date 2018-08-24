package com.gmail.ivanjermakov1.telegramspeechrecognitionbot.lang;

public enum Language {
	
	ENGLISH("en-US"),
	RUSSIAN("ru-RU");
	
	private String code;
	
	Language(String code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return code;
	}
}
