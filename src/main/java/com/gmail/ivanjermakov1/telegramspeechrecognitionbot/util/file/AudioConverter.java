package com.gmail.ivanjermakov1.telegramspeechrecognitionbot.util.file;

import com.gmail.ivanjermakov1.telegramspeechrecognitionbot.SpeechRecognitionBot;

import java.io.IOException;

public class AudioConverter {
	
	public static String ogaToWav(String filePath) throws IOException, InterruptedException {
		String newFilePath = filePath.substring(0, filePath.lastIndexOf(".")) + ".wav";
		Process process = new ProcessBuilder(SpeechRecognitionBot.configurator.getProperty("opusdec.exe"), filePath, newFilePath).start();
		process.waitFor();
		return newFilePath;
	}
	
}
