package cosc4353;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Engine implements GetPayment {
	public static String sentMessage = null;
	public static String userInputString = "";
	public static boolean needUserInput = false;
	public static boolean playingTelegramGame;

	ArrayList<Player> players;
	Board board;
	Deck deck;

	private static Scanner keyboard = new Scanner(System.in);
	public static boolean gameover = false;
	public static boolean setup = false;
	public Integer numplayer = null;
	
	
	public static void telegramOrConsole(){
		System.out.println("Will you be playing a telegram game?");
		String holder = keyboard.nextLine();
		if(holder.equalsIgnoreCase("yes")){
			holder = "yes";
		}else if(holder.equalsIgnoreCase("Yes")){
			holder = "Yes";
		}else if(holder.equalsIgnoreCase("YES")){
			holder = "YES";
		}
		if(holder == "yes" || holder == "Yes" ||holder == "YES"){
			playingTelegramGame =true;
		}else{
			playingTelegramGame =false;
		}
	}
	
    public void StartUp() throws InterruptedException {
    	Thread.sleep(5000);	
    	if(playingTelegramGame == true){
    		sentMessage = "RISK Board Game";	
    		TelegramBotsApi sendUserMessage = new TelegramBotsApi();	
    		try {														
    			sendUserMessage.registerBot(new TelegramBot());
	        	} catch (TelegramApiException e) {
	        		e.printStackTrace();
	        	}
    		TelegramBot sendUserMessage1 = new TelegramBot();		
   		 	sendUserMessage1.sendUserMessage();
   		 	needUserInput = true;				
   		 	sentMessage = "Enter Number of Players:";	//1
   		 	sendUserMessage1.sendUserMessage();	//2
   		 System.out.println("it should get the user input here");
   		 	Thread.sleep(5000);		//4
   		 System.out.println("this is user input");
   		System.out.println(userInputString);
   		 	numplayer = Integer.parseInt(userInputString);
   		 players = Create_Names_and_Turn_Position(numplayer);
   		 System.out.println("this is what numplayer was set to");
   		 System.out.println(numplayer);
    	}
    	else if(Main.instructionsExecuted == false && playingTelegramGame == false){
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
          }
    	//System.out.println("hiiii0");
		clearScreen();
		// Create Board
		board = new Board();
		board.createBoard();
		// Setup the players and board with armies
		setupArmies(players, board);

		// Create and Shuffle Deck
		deck = new Deck(board.getTerritories());
		//System.out.println("hiiii1");
		clearScreen();
		//System.out.println("hiiii2");
          
	}

	public void Turns() {

		TurnManager turnManager = new TurnManager(players, board);
		new AttackWatcher(turnManager);

		while(!gameover) {
			int userChoice = Integer.parseInt(getinput(turnManager.getCurrentPlayerName(), turnManager.getturnNumber()));
			switch(userChoice) {
                case 1:
					// Place Armies
					break;

				case 2:
					// Attack
					Territory enemyTerritory = chooseattackEnemyMenu(turnManager);
					Territory friendlyTerritory = chooseattackFriendlyMenu(turnManager, enemyTerritory);
					turnManager.attackState(enemyTerritory, friendlyTerritory);
					break;
				
                case 3:
					// Fortify
					break;
				
				case 4:
					System.out.println("You purchased 10 credits");
					(turnManager.getCurrentPlayerObject()).incrementInGameCredit(10);
					break;

                case 5:
                	int playerNum;
                	turnManager.getnumberofPlayers();
            		System.out.println("Which Player would you like to transfer credit to?");
            		playerNum = Get_A_Number();
            		if(turnManager.getnumberofPlayers() < playerNum) {
            			System.out.println("Player number must be valid");
            		}
                	(turnManager.getPlayerObject(playerNum)).decrementInGameCredit(giveOtherPlayerCredit());
                	System.out.print("you gave 10 credit to: ");
                	System.out.println(playerNum);
					break;
				
				case 6:
					turnManager.undo();
					break;

				case 7:
					turnManager.redo();
					break;

				case 8:
					turnManager.takeTurn();
					break;

				case 9:
					gameover = true;
					break;
				case 10:
					//(turnManager.getCurrentPlayerObject()).givePlayerUndo();
					(turnManager.getCurrentPlayerObject()).decrementInGameCredit(10);
					System.out.println("You have purchased 1 undo for 10 credits?");
					
					break;
			}
		}
	}

	public String getinput(String currentplayername, int turnnumber) {

		Timer timer = new Timer();
        Menu menu = new Menu();
        
		timer.start();
        menu.start();
        
        menu.setTurnNumber(turnnumber);
        menu.setCurrentPlayer(currentplayername);
		
		//System.out.println("Started while!");
		String holder = "";
		while(timer.isAlive() && !timer.isInterrupted()){
			if(menu.getdata() != ""){
				holder = menu.getdata();
				timer.interrupt();
			}
		}
		
		if(!menu.interrupted()){
			menu.interrupt();
		}
		
		if(holder == "")
			return "8";
		
        return holder;
    }

	public static int menu(TurnManager turnManager) {
		int selection = -1;
		try{
			while(selection < 1 || selection > 10) {
				System.out.println("=======================================================================================================");
				System.out.println(turnManager.getCurrentPlayerName() + "'s turn. Please choose an action. Turn number: " + turnManager.getturnNumber());
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
				System.out.println("10: Purchase Undo");
				selection = Get_A_Number();
			}
		} catch (InputMismatchException e) {
			menu(turnManager);
		}
        return selection;
	}


	private ArrayList<Player> Create_Names_and_Turn_Position(int numberofplayers) throws InterruptedException {
		ArrayList<Player> tempplayers = new ArrayList<Player>();
		String names[] = new String[numberofplayers];
		int pos[] = new int[numberofplayers];

		//Set Player Names
		for(int i = 1; numberofplayers >= i; i++) {
			do {
				if(playingTelegramGame == true){
					sentMessage = ("Enter the Name of Player " + i + ": ");
			        TelegramBotsApi sendUserMessage = new TelegramBotsApi();
			        try {
						 sendUserMessage.registerBot(new TelegramBot());
				        } catch (TelegramApiException e) {
				            e.printStackTrace();
				        }
			   	 TelegramBot sendUserMessage1 = new TelegramBot();
			   		 sendUserMessage1.sendUserMessage(); 
	            	Thread.sleep(5000);
	            	names[i-1] = userInputString;
	            }else if(playingTelegramGame == false){
	            	System.out.println("Enter the Name of Player " + i + ": ");
					names[i-1] = Get_A_String();	
	            }
			}while(names[i-1] == "");
		}
		//Set Player Order
		Dice d1 = new Dice();
		for(int i = 1; numberofplayers >= i; i++) {
			if(playingTelegramGame == true){
				sentMessage = ("Player " + names[i-1] + ", Press enter r to roll for your position: ");
		        TelegramBotsApi sendUserMessage = new TelegramBotsApi();
		        try {
					 sendUserMessage.registerBot(new TelegramBot());
			        } catch (TelegramApiException e) {
			            e.printStackTrace();
			        }
		   	 TelegramBot sendUserMessage1 = new TelegramBot();
		   		 sendUserMessage1.sendUserMessage(); 
            	Thread.sleep(10000);
            	if(userInputString == "r"){
            		pos[i-1] = Player_Dice_Roll(d1);
            		sentMessage = ("Rolled a: " + pos[i-1]);
            		TelegramBotsApi sendUserRoll = new TelegramBotsApi();
            		try {
   					 sendUserRoll.registerBot(new TelegramBot());
   			        } catch (TelegramApiException e) {
   			            e.printStackTrace();
   			        }
            		TelegramBot sendUserMessage2 = new TelegramBot();
            		sendUserMessage2.sendUserMessage();	
            	}	
            }else if(playingTelegramGame == false){
            	System.out.println("Player " + names[i-1] + ", Roll for your position: ");
    			pos[i-1] = Player_Dice_Roll(d1);
    			System.out.println("Rolled a: " + pos[i-1] + "\n");	
            }
		}
		clearScreen();
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
			if(playingTelegramGame == true){
				sentMessage = ("Player " + names[i] + " choose a color");
				 TelegramBotsApi sendUserMessage = new TelegramBotsApi();
			        try {
						 sendUserMessage.registerBot(new TelegramBot());
				        } catch (TelegramApiException e) {
				            e.printStackTrace();
				        }
			   	 TelegramBot sendUserMessage1 = new TelegramBot();
			   		 sendUserMessage1.sendUserMessage(); 
	            	Thread.sleep(5000);
	            	String requested_color = "";
	            	requested_color = userInputString;
					tempplayers.add(i, new Player(names[i], requested_color, (i + 1), getArmyCount(numberofplayers)));  // Player(Name, Color, TurnPosition, numberofArmies)
			}else if(playingTelegramGame == false){
				System.out.println("Player " + names[i]);
				String requested_color = "";
				do{
					requested_color = Get_Color();
					if(available_colors.contains(requested_color)) {
						available_colors.remove(available_colors.indexOf(requested_color));
						break;
					}
					else{
						System.out.println("Color Taken!\nAvailable Colors: " + available_colors + "\n");
					}
				}while(true);
			}
		}

		return tempplayers;
	}

	public static void setupArmies(ArrayList<Player> players, Board board) throws InterruptedException {
		int playerorder = 0;
		int currentplayer = playerorder % players.size();
		if(playingTelegramGame == true){
			while(board.hasUnoccupied()) {
				ArrayList<Territory> unoccupiedList = new ArrayList<Territory>(board.getUnoccupied());
				sentMessage = (players.get(currentplayer).getName() + ": Please choose a territory to occupy.   [" + players.get(currentplayer).getNumberofArmies() + "] armies remaining.");
				TelegramBotsApi sendUserMessage = new TelegramBotsApi();
		        try {
					 sendUserMessage.registerBot(new TelegramBot());
			        } catch (TelegramApiException e) {
			            e.printStackTrace();
			        }
		   	 	TelegramBot sendUserMessage1 = new TelegramBot();
		   		 sendUserMessage1.sendUserMessage();
				int userChoice = chooseUnoccupiedMenu(board, unoccupiedList);
				System.out.println("uerchoice = ");
				System.out.println(userChoice);
				board.setPlayerOccupying(unoccupiedList.get(userChoice).getName(), players.get(currentplayer));
				board.setNumberofArmies(unoccupiedList.get(userChoice).getName(), 1);
				players.get(currentplayer).reduceArmiesBy(1);
				players.get(currentplayer).setTerritories(unoccupiedList.get(userChoice).getName(), unoccupiedList.get(userChoice));
				playerorder++;
				currentplayer = playerorder % players.size();
			}
		}else if(playingTelegramGame == false){
		// Initial claiming of all 42 territories
			while(board.hasUnoccupied()) {
				ArrayList<Territory> unoccupiedList = new ArrayList<Territory>(board.getUnoccupied());
				System.out.println("\n=======================================================================================================");
				System.out.println(players.get(currentplayer).getName() + ": Please choose a territory to occupy.   [" + players.get(currentplayer).getNumberofArmies() + "] armies remaining.");
				System.out.println("=======================================================================================================");
				int userChoice = chooseUnoccupiedMenu(board, unoccupiedList);
			
				board.setPlayerOccupying(unoccupiedList.get(userChoice).getName(), players.get(currentplayer));
				board.setNumberofArmies(unoccupiedList.get(userChoice).getName(), 1);
				players.get(currentplayer).reduceArmiesBy(1);
				players.get(currentplayer).setTerritories(unoccupiedList.get(userChoice).getName(), unoccupiedList.get(userChoice));
				playerorder++;
				currentplayer = playerorder % players.size();
		}
		}

		// Continue placing armies onto controlled territories until everyone is out of armies.
		while(players.get(currentplayer).hasArmy()) {
			ArrayList<Territory> occupiedList = new ArrayList<Territory>(players.get(currentplayer).getTerritories());
			System.out.println("\n=======================================================================================================");
			System.out.println(players.get(currentplayer).getName() + ": Please choose a controlled territory to place an additonal army.   [" + players.get(currentplayer).getNumberofArmies() + "] armies remaining.");
			System.out.println("=======================================================================================================");
			
			int userChoice = chooseOccupiedTerritoryMenu(board, occupiedList);

			board.incrementArmies(occupiedList.get(userChoice).getName(), 1);
			players.get(currentplayer).reduceArmiesBy(1);
			playerorder++;
			currentplayer = playerorder % players.size();
		}
	}

	public static int chooseUnoccupiedMenu(Board board, ArrayList<Territory> unoccupiedList) throws InterruptedException {
		int selection = -1;
		if(playingTelegramGame == true){
			try{
				while(selection < 1 || selection > unoccupiedList.size()) {
					for(int i = 0; i < unoccupiedList.size(); i++)
						sentMessage =((i + 1) + ": " + unoccupiedList.get(i).getName());TelegramBotsApi sendUserMessage = new TelegramBotsApi();
				        try {
							 sendUserMessage.registerBot(new TelegramBot());
					        } catch (TelegramApiException e) {
					            e.printStackTrace();
					        }
				   	 	TelegramBot sendUserMessage1 = new TelegramBot();
				   		 sendUserMessage1.sendUserMessage(); 
				   		 System.out.println("hi");
				   		System.out.println(unoccupiedList.size());
				   		Thread.sleep(5000);
					selection = Integer.parseInt(userInputString);
					System.out.println(selection);
				}
			} catch (InputMismatchException e) {
				chooseUnoccupiedMenu(board, unoccupiedList);
			}
		}
		if(playingTelegramGame == false){
			try{
				while(selection < 1 || selection > unoccupiedList.size()) {
					for(int i = 0; i < unoccupiedList.size(); i++)
						System.out.println((i + 1) + ": " + unoccupiedList.get(i).getName());
					selection = Get_A_Number();
				}
			} catch (InputMismatchException e) {
				chooseUnoccupiedMenu(board, unoccupiedList);
			}
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

	public static Territory chooseattackEnemyMenu(TurnManager turnManager) {
		int selection = -1;
		ArrayList<Territory> attackableTerritories = new ArrayList<Territory>(turnManager.getAttackableTerritories());
		try{
			while(selection < 1 || selection > attackableTerritories.size()) {
				System.out.println("\n=======================================================================================================");
				System.out.println(turnManager.getCurrentPlayerName() + ": Please choose an enemy territory to attack against.");
				System.out.println("=======================================================================================================");
		
				for(int i = 0; i < attackableTerritories.size(); i++) {
					System.out.println((i + 1) + ": " + attackableTerritories.get(i).getName() + " [" + attackableTerritories.get(i).getnumberofArmies() + "] - occupied by " + attackableTerritories.get(i).getPlayerOccupying().getName());
				}
				selection = Get_A_Number();
			}
		} catch (InputMismatchException e) {
			chooseattackEnemyMenu(turnManager);
		}
		return attackableTerritories.get(selection - 1);
	}

	public static Territory chooseattackFriendlyMenu(TurnManager turnManager, Territory enemyTerritory) {
		int selection = -1;
		ArrayList<Territory> friendlyTerritories = new ArrayList<Territory>(turnManager.getControlledAdjacent(enemyTerritory));
		try{
			while(selection < 1 || selection > friendlyTerritories.size()) {
				System.out.println("\n=======================================================================================================");
				System.out.println(turnManager.getCurrentPlayerName() + ": Please choose a friendly territory to attack with.");
				System.out.println("=======================================================================================================");
		
				for(int i = 0; i < friendlyTerritories.size(); i++) {
					System.out.println((i + 1) + ": " + friendlyTerritories.get(i).getName() + " [" + friendlyTerritories.get(i).getnumberofArmies() + "]");
				}
				selection = Get_A_Number();
			}
		} catch (InputMismatchException e) {
			chooseattackFriendlyMenu(turnManager, enemyTerritory);
		}
		return friendlyTerritories.get(selection - 1);
	}
	
	
	public static void giveCreditsMenu(TurnManager turnmanager){
		int selection = -1;
		try{
		 //print players
			ArrayList<Player> playerlist = turnmanager.getPlayersObject();
			
			while(selection < 1 || selection >(playerlist.size())){
				
				for(int i =0;i<(turnmanager.getPlayersObject()).size();i++){
					
					if((playerlist.get(i)).getName()!=turnmanager.getCurrentPlayerName()){
						System.out.println((i+1)+")"+(playerlist.get(i)).getName());
					}
				}
				
				
				selection = Get_A_Number();
			
			}
			
			selection -= 1;
			
			int amount =-1;
			
			while(amount < 0){
				
				System.out.println("How Much? to player:"+ playerlist.get(selection).getName());
				amount = Get_A_Number();
				
				if(amount < 0 || amount > (turnmanager.getCurrentPlayerObject()).getInGameCredit()){
					System.out.println("Invalid amount!");
					amount = -1;
				}
				
			}
			
			//do transfer between (turnmanager.getCurrentPlayerObject()) to playerlist.get(selection)
			
			transferGameCredit((turnmanager.getCurrentPlayerObject()),playerlist.get(selection),amount);
			
		}catch(InputMismatchException e){
			giveCreditsMenu(turnmanager);
		}
		
		
		
	}
	
	public static void playerPurchaseUndo(TurnManager turnmanager){
		int selection = -1;
		try{
		 //print players
			ArrayList<Player> playerlist = turnmanager.getPlayersObject();
			
			while(selection < 1 || selection >(playerlist.size())){
				
				for(int i =0;i<(turnmanager.getPlayersObject()).size();i++){
					
					if((playerlist.get(i)).getName()!=turnmanager.getCurrentPlayerName()){
						System.out.println((i+1)+")"+(playerlist.get(i)).getName());
					}
				}
				
				
				selection = Get_A_Number();
			
			}
			
			selection -= 1;
			
			int amount =-1;
			
			while(amount < 0){
				
				System.out.println("How Much? to player:"+ playerlist.get(selection).getName());
				amount = Get_A_Number();
				
				if(amount < 0 || amount > (turnmanager.getCurrentPlayerObject()).getInGameCredit()){
					System.out.println("Invalid amount!");
					amount = -1;
				}
				
			}
			
			//do transfer between (turnmanager.getCurrentPlayerObject()) to playerlist.get(selection)
			
			transferGameCredit((turnmanager.getCurrentPlayerObject()),playerlist.get(selection),amount);
			
		}catch(InputMismatchException e){
			giveCreditsMenu(turnmanager);
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
    
    public static void transferGameCredit(Player from, Player to,int amount){
    	from.decrementInGameCredit(amount);
    	to.incrementInGameCredit(amount);
    }

	public int givePlayerCredit() {
		System.out.println("You purchased 100 credit");
		return 100;
	}

	public int giveOtherPlayerCredit() {
		System.out.println("You gave 10 credit");
		return 10;
	}
}