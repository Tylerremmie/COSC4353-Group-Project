import java.util.*;
import java.lang.*;

public class Engine {
	
	public static void Startup(){
		//Risk Boardgame
				System.out.println("RISK Board Game");
				{
				while(true){
					System.out.println("Enter The Number of Players:");
					int numplayer = Get_A_Number();
					if(numplayer == 2){
						//generate player objects and names
						//create players with certain amount of army
						break;
					}else if(numplayer > 2 && numplayer <=6){
						//generate player objects and names
						
						
						
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
				}
				//generate map
				//make dice 
				//create order for turns
				
				
				
				
				
		
		
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

		Scanner keyboard = new Scanner(System.in);
		
		int num;
		do {
		    while (!keyboard.hasNextInt()) {
		        System.out.println("Not a Valid Number!");
		        keyboard.next();
		    }
		    num = keyboard.nextInt();
		} while (num <= 0);
		
		keyboard.close();		
		return num;
		
	}

	public static String Get_A_String(){
		Scanner keyboard = new Scanner(System.in);
		String holder = keyboard.nextLine();
		
		keyboard.close();
		return holder;
	}
	
	public static int Player_Dice_Roll(Dice di){
		System.out.println("Press Enter to roll:");
		Scanner keyboard = new Scanner(System.in);
		keyboard.nextLine();
		keyboard.close();
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
					
			}while(names[i-1] != "");	
		}
		
		//setting initial positions according to dice roll
		Dice d1 = new Dice();
		for(int i=1; numberoplayers >= i;i++){
			System.out.println("Player "+ names[i-1]);
			System.out.println("Roll for your position: ");
			pos[i-1] = Player_Dice_Roll(d1);
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
		
		for(int i = 0; i < numberoplayers;i++){
			organizedplayers[i].setName(names[i]);
		}
		
		//return player array sorted by turn order
		return organizedplayers;
	}
}

