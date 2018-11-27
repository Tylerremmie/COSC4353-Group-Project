package cosc4353;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class Main {
	public static boolean instructionsExecuted =  false;
	

	public static void main(String[] args) throws InterruptedException {
		
		Engine.telegramOrConsole();
		
		if(Engine.playingTelegramGame == true){
		
		ApiContextInitializer.init();
		 TelegramBotsApi botsApi = new TelegramBotsApi();
		 TelegramBotsApi sendMessage = new TelegramBotsApi();
		 
		 try {
			 sendMessage.registerBot(new TelegramBot());
	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        }
		  
		 try {
	            botsApi.registerBot(new TelegramBot());
	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        }
		 TelegramBot bott = new TelegramBot();
		 bott.sendInstructions();
		}
			 Engine Driver = new Engine();
			 Driver.StartUp();
			 Driver.Turns();	 
	}
}