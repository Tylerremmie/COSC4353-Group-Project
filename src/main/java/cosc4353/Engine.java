package cosc4353;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Engine implements GetPayment {

	ArrayList<Player> players;
	Board board;
	Deck deck;

	private static Scanner keyboard = new Scanner(System.in);
	public static boolean gameover = false;
	public static boolean setup = false;

    public void StartUp() {
        System.out.println("RISK Board Game");

        while(true) {
            System.out.println("Enter Number of Players:");
            int numplayer = Get_A_Number();
            if(numplayer >= 2 && numplayer <= 6) {
                players = Create_Names_and_Turn_Position(numplayer);
                break;
            }else{
                System.out.println("Please Enter a valid number of players (2-6):");
            }
		}
		// Create Board
		//board = new Board();
		//board.createBoard();

		// Setup the players and board with armies
		//setupArmies(players, board);

		// Create and Shuffle Deck
		//deck = new Deck(board.getTerritories());
        
        Turns();
       
		clearScreen();
		
	}

	public void Turns() {

		TurnManager turnManager = new TurnManager(players, board);

		while(!gameover) {
			int userChoice = menu(turnManager);
			switch(userChoice) {
                case 1:
					// Place Armies
					break;

				case 2:
					// Attack
					attackMenu(turnManager, board);
					break;
				
                case 3:
					// Fortify
					break;
					
                case 4:
                	(turnManager.getCurrentPlayerObject()).incrementInGameCredit(givePlayerCredit());
                	break;
                    
                case 5:
                    gameover = true;

                    // Spend Credits
					break;
				
				case 5:
					turnManager.undo();
					break;

				case 6:
					turnManager.redo();
					break;

				case 7:
					turnManager.takeTurn();
					break;

				case 8:
					gameover = true;
>>>>>>> 53059ef674a9a218e3488868b8026688a5442546
					break;
			}
		}
	}

	public static int menu(TurnManager turnManager) {
		int selection = -1;
		try{
<<<<<<< HEAD
			while(selection < 1 || selection > 5) {
				System.out.println(turnManager.getCurrentPlayerName() + "'s turn. Turn number: " + turnManager.getturnNumber());
				System.out.println("-------------------------");
				System.out.println("1 - Finish Turn");
                System.out.println("2 - Undo");
                System.out.println("3 - Redo");
                System.out.println("4 - Purchase Additional Cards ");
                System.out.println("5 - Exit");
=======
			while(selection < 1 || selection > 8) {
				System.out.println(turnManager.getCurrentPlayerName() + "'s turn. Please choose an action. Turn number: " + turnManager.getturnNumber());
				System.out.println("----------------------------------------------------------");
				System.out.println("1: Place New Armies");
                System.out.println("2: Attack");
                System.out.println("3: Fortify");
				System.out.println("4: Spend Credits");
				System.out.println("5: Undo Turn");
				System.out.println("6: Redo Turn");
				System.out.println("7: End Turn");
				System.out.println("8: End Game");
>>>>>>> 53059ef674a9a218e3488868b8026688a5442546
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
			tempplayers.add(i, new Player(names[i], requested_color, (i + 1), getArmyCount(numberofplayers)));  // Player(Name, Color, TurnPosition, numberofArmies)
		}
		return tempplayers;
	}

	public static void setupArmies(ArrayList<Player> players, Board board) {
		int playerorder = 0;
		int currentplayer = playerorder % players.size();

		// Initial claiming of all 42 territories
		while(board.hasUnoccupied()) {
			ArrayList<Territory> unoccupiedList = new ArrayList<Territory>(board.getUnoccupied());
			System.out.println("\n================================================================");
			System.out.println(players.get(currentplayer).getName() + ": Please choose a territory to occupy.   [" + players.get(currentplayer).getNumberofArmies() + "] armies remaining.");
			System.out.println("================================================================");
			int userChoice = chooseUnoccupiedMenu(board, unoccupiedList);
			
			board.setPlayerOccupying(unoccupiedList.get(userChoice).getName(), players.get(currentplayer));
			board.setNumberofArmies(unoccupiedList.get(userChoice).getName(), 1);
			players.get(currentplayer).reduceArmiesBy(1);
			players.get(currentplayer).setTerritories(unoccupiedList.get(userChoice).getName(), unoccupiedList.get(userChoice));
			playerorder++;
			currentplayer = playerorder % players.size();
		}

		// Continue placing armies onto controlled territories until everyone is out of armies.
		while(players.get(currentplayer).hasArmy()) {
			ArrayList<Territory> occupiedList = new ArrayList<Territory>(players.get(currentplayer).getTerritories());
			System.out.println("\n================================================================");
			System.out.println(players.get(currentplayer).getName() + ": Please choose a controlled territory to place an additonal army.   [" + players.get(currentplayer).getNumberofArmies() + "] armies remaining.");
			System.out.println("================================================================");
			
			int userChoice = chooseOccupiedTerritoryMenu(board, occupiedList);

			board.incrementArmies(occupiedList.get(userChoice).getName(), 1);
			players.get(currentplayer).reduceArmiesBy(1);
			playerorder++;
			currentplayer = playerorder % players.size();
		}
	}

	public static int chooseUnoccupiedMenu(Board board, ArrayList<Territory> unoccupiedList) {
		int selection = -1;

		try{
			while(selection < 1 || selection > unoccupiedList.size()) {
				System.out.println("-------------------------");
				for(int i = 0; i < unoccupiedList.size(); i++)
					System.out.println((i + 1) + ": " + unoccupiedList.get(i).getName());
				selection = Get_A_Number();
			}
		} catch (InputMismatchException e) {
			chooseUnoccupiedMenu(board, unoccupiedList);
		}
		return selection - 1;
	}

	public static int chooseOccupiedTerritoryMenu(Board board, ArrayList<Territory> territoryList) {
		int selection = -1;

		try{
			while(selection < 1 || selection > territoryList.size()) {
				for(int i = 0; i < territoryList.size(); i++)
					System.out.println((i + 1) + ": " + territoryList.get(i).getName() + " [" + territoryList.get(i).getnumberofArmies() + "]");
				selection = Get_A_Number();
			}

		} catch (InputMismatchException e) {
			chooseOccupiedTerritoryMenu(board, territoryList);
		}
		return selection - 1;
	}

	public static void attackMenu(TurnManager turnManager, Board board) {
		ArrayList<Territory> attackableTerritories = new ArrayList<Territory>(turnManager.getAttackableTerritories());
		System.out.println("\n================================================================");
		System.out.println(turnManager.getCurrentPlayerName() + ": Please choose a territory to attack.");
		System.out.println("================================================================");

		for(int i = 0; i < attackableTerritories.size(); i++) {
			System.out.println((i + 1) + ": " + attackableTerritories.get(i).getName() + " [" + attackableTerritories.get(i).getnumberofArmies() + "] - occupied by " + attackableTerritories.get(i).getPlayerOccupying().getName());
		}
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

	public static int getArmyCount(int numberofplayers) {
		if(numberofplayers == 2) {
			return 35;
		}else if(numberofplayers == 3) {
			return 35;
		}else if(numberofplayers == 4) {
			return 30;
		}else if(numberofplayers == 5) {
			return 25;
		}else if(numberofplayers == 6) {
			return 20;
		} else {
			return 0;
		}
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

	public int givePlayerCredit() {
		System.out.println("You purchased  100 credit");
		
		
		return 100;
	} 
}