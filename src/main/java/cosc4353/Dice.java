package cosc4353;

import java.util.Random;

public class Dice {
	private int faceval;
	
	public int roll(){
		Random rand = new Random();
		faceval = rand.nextInt(6)+ 1;
		System.out.println("Rolled a: "+ faceval);
		return faceval;
	}
	
	public int getval(){
		return faceval;
	}
	
	
}