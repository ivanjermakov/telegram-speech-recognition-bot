package com.gmail.ivanjermakov1.telegramspeechrecognitionbot.recognize;

import com.gmail.ivanjermakov1.telegramspeechrecognitionbot.exception.RecognizeException;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class SphinxRecognizer implements Recognizer {
	
	@Override
	public String recognize(String fileName) throws IOException, RecognizeException {
		Configuration configuration = new Configuration();
		
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
		
		StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
		
		InputStream stream = new FileInputStream(new File(fileName));
		
		recognizer.startRecognition(stream);
		StringBuilder result = new StringBuilder();
		SpeechResult speechResult;
		while (true) {
			speechResult = recognizer.getResult();
			if (speechResult == null) break;
			result.append(speechResult.getHypothesis());
			System.out.format("Hypothesis: %s\n", speechResult.getHypothesis());
		}
		recognizer.stopRecognition();
		
		if (result.length() == 0) throw new RecognizeException();
		return result.toString();
	}
	
}
