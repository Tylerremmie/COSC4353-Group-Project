package cosc4353;
import java.util.Random;

public class Deck {

		private boolean isInDeck;	
		private int cardNum;
		Integer[] deck;
		
		public void initializeDeck(){
			deck = new Integer[41];
			 for (int i = 0; i < deck.length; i++){
				 deck[i]= i;
			 }
		}
		
		public int chooseCard(){
			Random rand = new Random();
			cardNum = rand.nextInt(42)+ 1;
			return cardNum;
		}
		
		public int getCardNum(){
			return cardNum;
		}
		
		public boolean isInDeck(){
			if(deck[cardNum] != null){
				isInDeck = true;
				deck[cardNum] = null;
			}
			else{
				isInDeck = false;
				chooseCard();
			}
			return isInDeck;
		}
		
		public boolean getisInDeck(){
			return isInDeck;
		}
}