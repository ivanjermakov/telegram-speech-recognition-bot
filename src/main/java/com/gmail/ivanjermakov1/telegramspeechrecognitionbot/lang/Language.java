package com.gmail.ivanjermakov1.telegramspeechrecognitionbot.lang;

// TODO: support for more langs: https://cloud.google.com/speech-to-text/docs/languages
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
