package cosc4353;

import java.util.*;

public class Engine {
	
	private static Player[] players;
	private static Scanner keyboard = new Scanner(System.in);
	private static int turnstatus = 0; // initial turn = 0 , ending turn = -num, non inital turn = +num
	
	public void Startup(){
		//Risk Boardgame
				System.out.println("RISK Board Game");
				
				while(true){
					System.out.println("Enter The Number of Players:");
					int numplayer = Get_A_Number();
					if(numplayer == 2){
						players = Create_Names_and_Turn_Position(numplayer);
						//generate player objects and names
						//create players with certain amount of army
						break;
					}else if(numplayer > 2 && numplayer <=6){
						//generate player objects and names
						players = Create_Names_and_Turn_Position(numplayer);
						
						
						if(numplayer == 3){
							//give players 35 infantry
						}else if(numplayer == 4){
							//give players 30 infantry
						}else if(numplayer == 5){
							//give players 25 infantry
						}else if(numplayer == 6){
							//give players 20 infantry
						}
						
						break;
					}else{
						System.out.println("Please Enter a valid amount of players! ");
					}
				}
				//generate map
				//do setup process for placing armies for each person
				//make dice
		}
	
	public void Turn(){
		int Playerturnindex =0;

		while(true){//break if someone has all the territories
			while(!isendturn()){
				Display_Player_Info(players[Playerturnindex]);
				//check if player has 5 or 6 cards
				//give player armies based on territories
				
				Dis_Turn_Menu();
				
				int choice = Get_A_Number_Between(1,4);
				
				if(choice == 1){
					Turn_Armies(players[Playerturnindex]);
				}else if(choice == 2){
					Turn_Cards(players[Playerturnindex]);
				}else if(choice == 3){
					Turn_Countries();
				}else if(choice == 4 && isiniturn()){
					Turn_Reset();
				}else if(choice == 4){
					System.out.println("Reset not available!");
				}else{
					System.out.println("Unknown Choice");
				}
				
				
			}
			
			Playerturnindex = (Playerturnindex +1) % players.length;
			turnstatus =0;
		
		}
	}
				 
				
	/**
	 * Checks to see if the input is a int greater than 0
	 * 
	 * keyboard is the object to receive console input
	 * 
	 * @return a non negative integer from the console
	 * 
	 * @throws ioexception if input is not a valid input
	 */
	
	public static int Get_A_Number(){
		int num;
		do {
		    while (!keyboard.hasNextInt()) {
		        System.out.println("Not a Valid Number!");
		        keyboard.next();
		    }
		    num = keyboard.nextInt();
		} while (num <= 0);
		keyboard.nextLine();
			
		return num;
		
	}
	
	public static int Get_A_Number_Between(int low,int high){
		int num = 0;
		do{
			num = Get_A_Number();
			
			if(num > high || num < low){
				System.out.println("Number not within Parameters!");
			}
			
		}while(!(num >= low) && !(num <=high));
		
		return num;
		
	}

	public static String Get_A_String(){
		String holder = keyboard.nextLine();
		return holder;
	}
	
	public static String Get_Color(){
		String color = "";
		do{
			System.out.print("Choose a color: ");
			color = keyboard.nextLine();
			if(color.equalsIgnoreCase("yellow")){
				color ="yellow";
				break;
			}else if(color.equalsIgnoreCase("red")){
				color = "red";
				break;
			}else{
				System.out.println("Color not Valid!\n");
			}
			
		}while(true);
		
		return color;
		
	}
	
	public static int Player_Dice_Roll(Dice di){
		System.out.println("Press Enter to roll:");
		keyboard.nextLine();
		return di.roll();
	}
		
	public static Player[] Create_Names_and_Turn_Position(int numberoplayers){
		
		String names[] = new String[numberoplayers];
		int pos[] = new int[numberoplayers];
		
		//getting players names
		for(int i=1;numberoplayers >= i;i++){
			do{
				System.out.println("Enter the Name of Player "+ i+": ");
				names[i-1] =Get_A_String();
					
			}while(names[i-1] == "");	
		}
		
		//setting initial positions according to dice roll
		Dice d1 = new Dice();
		for(int i=1; numberoplayers >= i;i++){
			System.out.println("Player "+ names[i-1]);
			System.out.println("Roll for your position: ");
			pos[i-1] = Player_Dice_Roll(d1);
			System.out.println("Rolled a: "+ pos[i-1]+"\n");
		}
		
		//sorting the arrays
		for (int i = 0; i < numberoplayers-1; i++){
            for (int j = 0; j < numberoplayers-i-1; j++){
                if (pos[j] > pos[j+1]){
                    int temp = pos[j]; String temp2 = names[j];
                    pos[j] = pos[j+1];
                    names[j] = names[j+1];
                    pos[j+1] = temp;
                    names[j+1]=temp2;
                    
                }
            }
		}
		
		//setting the players names in order
		Player organizedplayers[] = new Player[numberoplayers];
		List<String> avalible_colors = new ArrayList<String>();
		avalible_colors.add("yellow");
		avalible_colors.add("red");
		
		for(int i = 0; i < numberoplayers;i++){
			System.out.println("Player "+ names[i]);
			String requested_color = "";
			do{
				//System.out.println("Choose your color: ");
				requested_color = Get_Color();
				
				if(avalible_colors.contains(requested_color)){
					avalible_colors.remove(avalible_colors.indexOf(requested_color));
					break;
				}else{
					System.out.println("Color taken!");
					System.out.println("Avalible Colors: "+ avalible_colors +"\n");
				}
			}while(true);
			
			organizedplayers[i] = new Player(names[i], requested_color);

		}
		
		//return player array sorted by turn order
		return organizedplayers;
	}

	public static void Display_Player_Info(Player current){
		System.out.println(current.getName()+"\t Armies:"+ current.getNumberofArmies()+ "\t Reset Curency:"); // also needs back currency	
	}
	
	private static void Dis_Back(){
		System.out.println("0.Back");
	}
	
	private static boolean isBack(int input){
		if(input == 0){
			return true;
		}
		return false;
	}
	
	private static boolean isiniturn(){
		if(turnstatus == 0){
			return true;
		}
		return false;
	}
	
	private static boolean isendturn(){
		if(turnstatus < 0){
			return true;
		}
		return false;
	}
	
	private static void Dis_Turn_Menu(){
		Dis_Armies();
		Dis_Cards();
		Dis_Countries();
		if(isiniturn()){
			Dis_Reset();
		}
	}
	
	private static void Dis_Armies(){
		System.out.println("1.Armies");
	}
	
	private static void Turn_Armies(Player current){
		boolean initialandarmies = current.getNumberofArmies() > 0 && isiniturn();
		
		Dis_Back();
		if(initialandarmies){
			Dis_Armies_Place();
		}
		
		Dis_Armies_Move();
		
		int decision = Get_A_Number_Between(0,2);
		
		if(decision == 1 && isiniturn()){
			Armies_Place();
		}else if(decision == 1){
			System.out.println("Place is no longer avalible!");
		}else if(decision == 2){
			Armies_Move();
		}else if(isBack(decision)){
			return;
		}else{
			System.out.println("Error in Armies Selection!");
		}
		
		return;
	}
	
	private static void Dis_Armies_Place(){
		System.out.println("1.Place");
	}
	
	private static void Armies_Place(){
		System.out.println("Held territory or new teritory?");
		System.out.println("1.Held Territory");
		System.out.println("2.New Territory");
		int decision = Get_A_Number_Between(0,2);
		
		if(isBack(decision)){
			return;
		}else if(decision == 1){
			//function for:
				//display territories held 
				//allow player to select which to place // loop til 0 pieces or
				//decision to exit place using isBack()
		}else if(decision == 2){
			//function for:
				//display adjacent territories that are open
				//allow player to select which to place // loop til 0 pieces or 
				//decision to exit place using isBack()
		}
		
		return;
	}
	
	private static void Dis_Armies_Move(){
		System.out.println("2.Move");
	}
	
	private static void Armies_Move(){
		System.out.println("From? \nOwned Countries:");
		Dis_Back();
		int ownedcountriessize =0;
		//display owned countries
		int from = Get_A_Number_Between(0,ownedcountriessize);
		
		if(isBack(from)){
			return;
		}
		
		System.out.println("To? \nOwned Countries:");
		Dis_Back();
		//display owned countries
		int to = Get_A_Number_Between(0,ownedcountriessize);
		
		if(isBack(to)){
			return;
		}
		
		System.out.println("Amount of armies?");
		Dis_Back();
		//display amount of armies on both and avalible to transfer(armiesonfrom-1)
		int armiesonfrom =1; // set this to amount on from territory
		int numberofarmies = Get_A_Number_Between(0,armiesonfrom-1);
		
		if(isBack(numberofarmies)){
			return;
		}
		
		//call function that is passed 2 countries and number of armies that moves armies from the first one
		// use (playercountries[from],playercountries[to],numberofarmies)
		
		return;
	}
	
	private static void Dis_Cards(){
		System.out.println("2.Cards");
	}
	
	private static void Dis_Cards_Play(){
		System.out.print("1.Play cards");
	}
	
	private static void Turn_Cards(Player current){
		//display cards in hand of player
		Dis_Back();
		//if number of in hand cards >= 3
			Dis_Cards_Play();
		
		int decision = Get_A_Number_Between(0,1); 
		
		if(isBack(decision)){
			return;
		}
		
		//if number of in hand cards >= 3
			Play_Cards(current);
		return;
	}
	
	private static void Play_Cards(Player current){
		int numchosen =0;
		Card[] selected = new Card[3];
		//create duplicate list from hand
		
		while(numchosen!=3){
			Dis_Back();
			//display cards from duplicate list
			int decision = Get_A_Number_Between(0,6); // change to number of cards in hand
			if(isBack(decision)){
				System.out.println("Card Choosing canceled Returning to Menu!");
				return;
			}
			
			// put the card at the given index-1 into selected
			//delete the chosen card from duplicate list
			
		}
		
		//write card trade in class to check how each card matches up with each other
		//add armies to player which is current
		
		return;
	}
	
	private static void Dis_Countries(){
		System.out.println("3.Country Takeover!");
	}
	
	private static void Turn_Countries(){
		Dis_Back();
		//possibly check if both are available
		Dis_Countries_Neutral(); 
		Dis_Countries_Attack();
		int decision = Get_A_Number_Between(0,2);
		
		if(isBack(decision)){
			return;
		}else if(decision == 1){
			Turn_Countries_Neutral();
		}else if(decision == 2){
			Turn_Countries_Attack();
		}else{
			System.out.println("Error in Turn countries - invalid decision");
		}
		
		
		return;
	}
	
	private static void Dis_Countries_Neutral(){
		System.out.println("1.Obtain a neutral/open country");
	}
	
	private static void Dis_Countries_Attack(){
		System.out.println("2.Attack another players country");
	}
	
	private static void Turn_Countries_Neutral(){
		Dis_Back();
		//display adjacent open territories that can be taken over i.e. armies on that country >1
		int decision = Get_A_Number_Between(0,0); // change to amount of territories available
		
		if(isBack(decision)){
			return;
		}else{
			//put action in here for taking over/moving armies to neutral country
		}
		
		return;
		
	}
	
	private static void Turn_Countries_Attack(){
		Dis_Back();
		//list adjacent countries that are able to attack
		int decision = Get_A_Number_Between(0,0); // change to length of attack list
		
		if(isBack(decision)){
			return;
		}else{
			//call function to attack certain territory at certain index-1
		}
		
	}
	
	
	private static void Dis_Reset(){
		System.out.println("4.Reset to previous turn");
	}
	
	private static void Turn_Reset(){
		System.out.println("Are you Sure?");
		Dis_Back();
		System.out.println("1.Yes!");
		int decision = Get_A_Number_Between(0,1);
		
		if(isBack(decision)){
			return;
		}else if(decision ==1){
			//call reset function
		}else{
			System.out.println("Error undefined Decission - Turn Reset");
		}
		
	}
}

