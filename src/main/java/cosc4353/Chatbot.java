package cosc4353;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/*
    Players are asked to join the DDMT_Gameplay group if they would like to use Telegram for the game.
    The Group accepts all members but only the first 3 to type /ddmt_risk can play.

*/
public class Chatbot extends TelegramLongPollingBot {

    private String chatID = "-1001329331161"; //normal not test one2  -1001244051424 this is the ChucksTestID
    public ArrayList<Player> telegramPlayers = new ArrayList<Player>(3);
    private Integer playerCount = 0;
    private Update parseUpdate;
    private String messagetoPlayer;
    private String messageFromPlayer;
    private User chatUser;




    /*
    Listens to everything typed in the group chat and parses their choices as needed.
    users typing /ddmt_play will join the session
     */
    @Override
    public void onUpdateReceived(Update update) {

        //Player attempts to join and game is not full
        messageFromPlayer = update.getMessage().getText();
        if(messageFromPlayer.equals("/ddmt_play") && playerCount < 3){
            playerCount++;
            System.out.println("Player " + update.getMessage().getFrom().getFirstName() + " has joined the game");
            SendMessage notfull = new SendMessage()
                    .setChatId("-1001244051424")
                    .setText(update.getMessage().getFrom().getFirstName() + " has joined the game");
            try{
                execute(notfull);
            }catch(TelegramApiException e){
                e.printStackTrace();
            }
            //if(playerCount == 3)
        }

        //Player attempts to join but game is already full
        else if (messageFromPlayer.equals("/ddmt_play") && playerCount == 3){
            System.out.println("Player " + update.getMessage().getFrom().getFirstName() + " tried to join a full game");
            SendMessage full = new SendMessage()
                    .setChatId("-1001244051424")
                    .setText("Game is full. You cannot join at this time");
            try{
                execute(full);
            }catch(TelegramApiException e){
                e.printStackTrace();
            }

        }
        //catch all other commands and parse them throughout the game
        else{
            System.out.println(update.getMessage().getText() + " needs to be parsed");
            SendMessage playerchoice = new SendMessage()
                    .setChatId("-1001244051424")
                    .setText("I need to parse your message");
            try{
                execute(playerchoice);
            }catch (TelegramApiException e){
                e.printStackTrace();
            }
        }



    }

    @Override
    public String getBotUsername() {
        // Return bot username
        // If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
        return "Risk-Chatbot";
    }

    @Override
    public String getBotToken() {
        // Token to be provided in email to TA
        return "ourToken";
    }

    public void sendToGame(User user){




    }

    public void parseChat(String user_id, String request){

    }

}


