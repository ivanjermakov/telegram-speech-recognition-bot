package com.gmail.ivanjermakov1.telegramspeechrecognitionbot;

import com.gmail.ivanjermakov1.telegramspeechrecognitionbot.config.Configurator;
import com.gmail.ivanjermakov1.telegramspeechrecognitionbot.exception.InvalidMessageException;
import com.gmail.ivanjermakov1.telegramspeechrecognitionbot.util.WebDownloader;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Voice;

import javax.xml.bind.PropertyException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public class Bot extends TelegramLongPollingBot {
	
	private static Configurator configurator;
	
	static {
		try {
			configurator = new Configurator("src/main/resources/application.properties");
			configurator.load();
		} catch (IOException | PropertyException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onUpdateReceived(Update update) {
		try {
			Voice voice = update.getMessage().getVoice();
			if (voice == null) throw new InvalidMessageException();
			
			String fileId = voice.getFileId();
			
			System.out.println(fileId);
			
			String filePathUrl = "https://api.telegram.org/bot" + configurator.getProperty("telegram.bot.token") +
					"/getFile?file_id=" + fileId;
			JSONObject json = new JSONObject(IOUtils.toString(new URL(filePathUrl), Charset.forName("UTF-8")));
			
			String filePath = ((JSONObject) json.get("result")).get("file_path").toString();
			
			String downloadPath = "https://api.telegram.org/file/bot" + configurator.getProperty("telegram.bot.token") +
					"/" + filePath;
			
			WebDownloader.download(downloadPath, filePath);
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
	
}

//https://api.telegram.org/bot<token>/getFile?file_id=<file_id>
//https://api.telegram.org/file/bot<token>/<file_path>