package cosc4353;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
    	if (update.hasMessage() && update.getMessage().hasText() && Engine.needUserInput == true) {
            Engine.userInputString = update.getMessage().getText();
            Engine.gotUserInput=true;
    	}
        if (update.hasMessage() && update.getMessage().hasText() && Engine.needUserInput == false) {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            if (update.getMessage().getText().equals("/ddmt_risk")) {
            	Main.instructionsExecuted =true;
                SendMessage message = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText("Each of the 3 players must press start in order to begin the game");
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                rowInline.add(new InlineKeyboardButton().setText("start").setCallbackData("start"));
                rowsInline.add(rowInline);
                markupInline.setKeyboard(rowsInline);
                message.setReplyMarkup(markupInline);
                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
            }
        }
        else if (update.hasCallbackQuery()) {
            // Set variables
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();
            if (call_data.equals("start")) {
            	Engine.numPlayersTelegram++;
            	Engine.sentMessage = (Engine.numPlayersTelegram + " players have joined the game");
		        TelegramBotsApi sendUserMessage = new TelegramBotsApi();
		        try {
					 sendUserMessage.registerBot(new TelegramBot());
			        } catch (TelegramApiException e) {
			            e.printStackTrace();
			        }
		   	 TelegramBot sendUserMessage1 = new TelegramBot();
		   		 sendUserMessage1.sendUserMessage();
            	if(Engine.numPlayersTelegram == 3){
            		String answer = "gamestarted";
            		EditMessageText new_message = new EditMessageText()
                        .setChatId(chat_id)
                        .setMessageId(toIntExact(message_id))
                        .setText(answer);
            		try {
            			execute(new_message);
            			Engine.gotUserInput = true;
            			notifyAll();
            		} catch (TelegramApiException e) {
            			e.printStackTrace();
            		  }
            	}
            }
        }
    }
   
    public void sendInstructions() throws InterruptedException{
    		SendMessage message = new SendMessage() // Create a message object object
    		.setChatId("-1001329331161")
    		.setText("Hello Welcome to Risk! To start the game type /ddmt_risk in a group chat window. You need exactly 3 players to start.");
    		try {
    			execute(message); // Sending our message object to user
  
    		}catch (TelegramApiException e) {
    			e.printStackTrace();   
    		}
    }
    
    public void sendUserMessage(){
		SendMessage message = new SendMessage() // Create a message object object
		.setChatId("-1001329331161")
		.setText(Engine.sentMessage);
		try {
			execute(message); // Sending our message object to user
		}catch (TelegramApiException e) {
			e.printStackTrace();   
		}
    }
    	
    public String getBotUsername() {
    	  // Return bot username
        // If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
        return "MyAmazingBot";
    }

    public String getBotToken() {
        // Return bot token from BotFather
        return "659396904:AAHJ2kEU34amNR3CHNOMvumLNR8zh4nG7UA";  
    }
}
