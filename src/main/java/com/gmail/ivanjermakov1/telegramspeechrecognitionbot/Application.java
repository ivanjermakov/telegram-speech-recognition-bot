package com.gmail.ivanjermakov1.telegramspeechrecognitionbot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Application {
	
	public static void main(String[] args) throws TelegramApiRequestException {
		
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		
		Bot bot = new Bot();
		
		telegramBotsApi.registerBot(bot);
	}
	
}
