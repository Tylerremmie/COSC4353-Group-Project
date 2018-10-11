package cosc4353;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.IOException;

public class Engine {

	ArrayList<Player> players;
	private static Scanner keyboard = new Scanner(System.in);
	public static boolean gameover = false;

    public void StartUp() {
		clearScreen();
        System.out.println("RISK Board Game");

        while(true) {
            System.out.println("Enter Number of Players:");
            int numplayer = Get_A_Number();
            if(numplayer >= 2 && numplayer <= 6) {
                clearScreen();
                players = Create_Names_and_Turn_Position(numplayer);
                break;
            }else{
				clearScreen();
                System.out.println("Please Enter a valid number of players (2-6):");
            }
		}
		
		// --CONTINUE SETUP AFTER RETURNING PLAYER ARRAYLIST

	}

	public void Turns() {
		TurnManager turnManager = new TurnManager(players);

		while(!gameover) {
			int userChoice = menu(turnManager);
			if(userChoice == 1){
				turnManager.takeTurn();
			}
			else if(userChoice == 2)
			{
				turnManager.undo();
			}
			else if(userChoice == 3)
			{
				turnManager.redo();
			}
			else if(userChoice == 4)
			{
				turnManager.buyCredit();
				System.out.println("workinnnnng");
			}
			else if(userChoice == 5)
			{
				turnManager.buyCards();
				System.out.println("working");
			}
			else if(userChoice == 6)
			{
				gameover = true;
                clearScreen();
			}
			else
			{
				System.out.println("Please enter a number between 1 and 6");
			}
		}
	}
			

	public static int menu(TurnManager turnManager) {
		int selection = -1;
		try{
            clearScreen();
			while(selection < 1 || selection > 6) {
				System.out.println(turnManager.getCurrentPlayerName() + "'s turn. Turn number: " + turnManager.getturnNumber());
				System.out.println("-------------------------");
				System.out.println("1 - Finish Turn");
                System.out.println("2 - Undo");
                System.out.println("3 - Redo");
                System.out.println("4 - Purchase Game Credit");
                System.out.println("5 - Buy Cards using in Game Credit");
                System.out.println("6 - Exit");
				selection = Get_A_Number();
			}
		} catch (InputMismatchException e) {
			menu(turnManager);
		}
        return selection;
	}

	private ArrayList<Player> Create_Names_and_Turn_Position(int numberofplayers) {
		ArrayList<Player> tempplayers = new ArrayList<Player>();
		String names[] = new String[numberofplayers];
		int pos[] = new int[numberofplayers];

		//Set Player Names
		for(int i = 1; numberofplayers >= i; i++) {
			do {
				System.out.println("Enter the Name of Player " + i + ": ");
				names[i-1] = Get_A_String();
			}while(names[i-1] == "");
		}
		clearScreen();

		//Set Player Order
		Dice d1 = new Dice();
		for(int i = 1; numberofplayers >= i; i++) {
			System.out.println("Player " + names[i-1] + ", Roll for your position: ");
			pos[i-1] = Player_Dice_Roll(d1);
			System.out.println("Rolled a: " + pos[i-1] + "\n");
		}

		// Currently is sorting lowest rolls to highest (Turn 1 is lowest roller)
		for (int i = 0; i < numberofplayers-1; i++){
            for (int j = 0; j < numberofplayers-i-1; j++){
                if (pos[j] > pos[j+1]){
                    int temp = pos[j]; String temp2 = names[j];
                    pos[j] = pos[j+1];
                    names[j] = names[j+1];
                    pos[j+1] = temp;
                    names[j+1]=temp2;
                    
                }
            }
		}

		//Set Player Colors
		List<String> available_colors = new ArrayList<String>();
		available_colors.add("black");
		available_colors.add("blue");
		available_colors.add("green");
		available_colors.add("pink");
		available_colors.add("red");
		available_colors.add("yellow");

		for(int i = 0; i < numberofplayers; i++) {
			System.out.println("Player " + names[i]);
			String requested_color = "";
			do{
				requested_color = Get_Color();
				if(available_colors.contains(requested_color)) {
					available_colors.remove(available_colors.indexOf(requested_color));
					break;
				}else{
					System.out.println("Color Taken!\nAvailable Colors: " + available_colors + "\n");
				}
			}while(true);
			tempplayers.add(i, new Player(names[i], requested_color, (i + 1)));  // Player(Name, Color, TurnPosition)
		}
		return tempplayers;
	}

	//Scanner Functions
    public static int Get_A_Number(){
		int num;
		do {
		    while (!keyboard.hasNextInt()) {
		        System.out.println("Not a Valid Number!");
		        keyboard.next();
		    }
		    num = keyboard.nextInt();
		} while (num < 0);
		keyboard.nextLine();
			
		return num;
	}

	public static String Get_A_String(){
		String holder = keyboard.nextLine();
		return holder;
	}

	public static int Player_Dice_Roll(Dice di){
		System.out.println("Press Enter to roll:");
		keyboard.nextLine();
		return di.roll();
	}

	public static String Get_Color(){
		String color = "";
		do{
			System.out.print("Choose a color: ");
			color = keyboard.nextLine();
			if(color.equalsIgnoreCase("black")){
				color ="black";
				break;
			}else if(color.equalsIgnoreCase("blue")){
				color = "blue";
				break;
			}else if(color.equalsIgnoreCase("green")){
				color = "green";
				break;
			}else if(color.equalsIgnoreCase("pink")){
					color = "pink";
					break;
			}else if(color.equalsIgnoreCase("red")){
				color = "red";
				break;
			}else if(color.equalsIgnoreCase("yellow")){
				color = "yellow";
				break;
			}else{
				System.out.println("Color not Valid! (Black, Blue, Green, Pink, Red, or Yellow)\n");
			}
		}while(true);
		return color;
	}

    public static void clearScreen() {  
		try {
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				Runtime.getRuntime().exec("clear");
		} catch (IOException | InterruptedException ex) {}
	} 
}