package cosc4353;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;

public class Deck {

		private Card cardDrawn;
		private ArrayList<Territory> territories;
		private ArrayList<Card> deck;
		
		public Deck(ArrayList<Territory> territories) {
			Collections.shuffle(territories);
			deck = new ArrayList<Card>();

			String cardtypes[] = new String[] { "Infantry", "Cavalry", "Artillery" };

			for(int i = 0; i < territories.size(); i++) {
				deck.add(new Card(cardtypes[i % 3], territories.get(i)));
			}
			Collections.shuffle(deck);
		}
		
		public Card draw() {
			cardDrawn = deck.get(0);
			deck.remove(0);

			return drawnCard;
    	}
		
		public void add(Card card) {
			deck.add(card);
		}
	
		public void shuffle() {
			Collections.shuffle(deck);
		}
}