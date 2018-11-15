package cosc4353;

import java.io.IOException;

public class Menu extends Thread{
	
	private String data="";
	private static int turnnumber;
	private static String currentplayer = "";
	
	public void run(){
		MenuInput in = new MenuInput();
		
		try {

			System.out.println("=======================================================================================================");
			System.out.println(currentplayer + "'s turn. Please choose an action. Turn number: " + turnnumber);
			System.out.println("=======================================================================================================");
			System.out.println("1: Place New Armies");
            System.out.println("2: Attack");
			System.out.println("3: Fortify");
			System.out.println("4: Buy Credits");
			System.out.println("5: Give Credits");
			System.out.println("6: Undo Turn");
			System.out.println("7: Redo Turn");
			System.out.println("8: End Turn");
			System.out.println("9: End Game");

			data = in.call();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getdata(){
		return data;
	}
	
	public void setTurnNumber(int turnNum) {
		turnnumber = turnNum;
	}
	
	public void setCurrentPlayer(String currentPlayer) {
		currentplayer = currentPlayer;
	}
    
}