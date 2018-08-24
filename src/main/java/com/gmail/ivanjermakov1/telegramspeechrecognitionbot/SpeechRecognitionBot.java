package com.gmail.ivanjermakov1.telegramspeechrecognitionbot;

import com.gmail.ivanjermakov1.telegramspeechrecognitionbot.config.Configurator;
import com.gmail.ivanjermakov1.telegramspeechrecognitionbot.exception.DownloadException;
import com.gmail.ivanjermakov1.telegramspeechrecognitionbot.exception.InvalidMessageException;
import com.gmail.ivanjermakov1.telegramspeechrecognitionbot.recognize.GoogleRecognizer;
import com.gmail.ivanjermakov1.telegramspeechrecognitionbot.util.WebDownloader;
import com.gmail.ivanjermakov1.telegramspeechrecognitionbot.util.file.AudioConverter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Voice;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public class SpeechRecognitionBot extends TelegramLongPollingBot {
	
	public static Configurator configurator;
	
	static {
		try {
			configurator = new Configurator("src/main/resources/application.properties");
			configurator.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onUpdateReceived(Update update) {
		try {
			Voice voice = update.getMessage().getVoice();
			if (voice == null) throw new InvalidMessageException();
			
			new Thread(() -> {
				try {
					sendResponse(update, analyzeVoice(voice));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getBotUsername() {
		return "";
	}
	
	@Override
	public String getBotToken() {
		return configurator.getProperty("telegram.bot.token");
	}
	
	private String downloadVoice(Voice voice) throws DownloadException, IOException {
		String fileId = voice.getFileId();
		
		String filePathUrl = "https://api.telegram.org/bot" + configurator.getProperty("telegram.bot.token") +
				"/getFile?file_id=" + fileId;
		JSONObject json = new JSONObject(IOUtils.toString(new URL(filePathUrl), Charset.forName("UTF-8")));
		
		String filePath = ((JSONObject) json.get("result")).get("file_path").toString();
		
		String downloadPath = "https://api.telegram.org/file/bot" + configurator.getProperty("telegram.bot.token") +
				"/" + filePath;
		
		WebDownloader.download(downloadPath, filePath);
		
		return filePath;
	}
	
	private String analyzeVoice(Voice voice) throws IOException, InterruptedException, DownloadException {
		String filePath = downloadVoice(voice);
		
		String newFilePath = AudioConverter.ogaToWav(filePath);
		
		String result = new GoogleRecognizer().recognize(newFilePath);
		
		//cleanup
		FileUtils.forceDelete(new File(filePath));
		FileUtils.forceDelete(new File(newFilePath));
		
		return result;
	}
	
	private void sendResponse(Update update, String result) throws TelegramApiException {
		SendMessage message = new SendMessage().setChatId(update.getMessage().getChatId());
		
		message.setText("Recognized: \n" + result);
		
		execute(message);
	}
	
}