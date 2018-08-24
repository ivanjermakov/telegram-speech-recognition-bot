package com.gmail.ivanjermakov1.telegramspeechrecognitionbot.recognize;

import com.gmail.ivanjermakov1.telegramspeechrecognitionbot.SpeechRecognitionBot;
import com.gmail.ivanjermakov1.telegramspeechrecognitionbot.log.Logger;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GoogleRecognizer implements Recognizer {
	
	@Override
	public String recognize(String fileName) throws IOException {
		CredentialsProvider credentialsProvider = FixedCredentialsProvider
				.create(ServiceAccountCredentials.fromStream(new FileInputStream("src/main/resources/service-account.json")));
		
		SpeechSettings settings = SpeechSettings.newBuilder().setCredentialsProvider(credentialsProvider).build();
		
		// Instantiates a client
		SpeechClient speech = SpeechClient.create(settings);
		
		// Reads the audio file into memory
		Path path = Paths.get(fileName);
		byte[] data = Files.readAllBytes(path);
		ByteString audioBytes = ByteString.copyFrom(data);
		
		// Builds the sync recognize request
		RecognitionConfig config = RecognitionConfig.newBuilder()
				.setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
				.setSampleRateHertz(16000)
				.setLanguageCode(SpeechRecognitionBot.language.toString())
				.build();
		RecognitionAudio audio = RecognitionAudio.newBuilder()
				.setContent(audioBytes)
				.build();
		
		// Performs speech recognition on the audio file
		RecognizeResponse response = speech.recognize(config, audio);
		List<SpeechRecognitionResult> results = response.getResultsList();
		
		StringBuilder resultText = new StringBuilder();
		for (SpeechRecognitionResult result : results) {
			List<SpeechRecognitionAlternative> alternatives = result.getAlternativesList();
			for (SpeechRecognitionAlternative alternative : alternatives) {
				resultText.append(alternative.getTranscript());
			}
		}
		speech.close();
		
		Logger.info("successful voice recognition from file " + fileName + ". result: " + resultText.toString());
		return resultText.toString();
	}
	
}