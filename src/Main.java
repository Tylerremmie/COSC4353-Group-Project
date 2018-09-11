import java.util.*;

public class Main {

	public static void main(String[] args) {
		//Risk Boardgame
		System.out.println("RISK Board Game");
		
		//Intro sequence/////////////////////////////////////////////
		/**
		 * Checks to see if the input is a non negative int
		 * 
		 * keyboard is the object to receive console input
		 * 
		 */

		 /*
		
		Scanner keyboard = new Scanner(System.in);
		
		int numplayers;
		do {
		    System.out.println("Enter The Number of Players");
		    while (!keyboard.hasNextInt()) {
		        System.out.println("Not a Valid Number!");
		        keyboard.next();
		    }
		    numplayers = keyboard.nextInt();
		} while (numplayers <= 0);
		
		keyboard.close();
		
		////////////////////////////////////////////////////////////
		
		Dice die = new Dice();
		System.out.println("Rolling Dice.");
		System.out.println(die.roll());
		*/

		Board board = new Board();
		board.createBoard();
	}
}
