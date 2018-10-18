package cosc4353;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	private Card cardDrawn;
	private ArrayList<Card> deck;
	
	public Deck(ArrayList<Territory> territories) {
		ArrayList<Territory> temp = new ArrayList<Territory>(territories);
		Collections.shuffle(temp);
		deck = new ArrayList<Card>();

		String cardtypes[] = new String[] { "Infantry", "Cavalry", "Artillery" };

		for(int i = 0; i < temp.size(); i++) {
			deck.add(new Card(cardtypes[i % 3], temp.get(i)));
		}
		Collections.shuffle(deck);
	}
	
	public Card draw() {
		cardDrawn = deck.get(0);
		deck.remove(0);

		return cardDrawn;
	}
	
	public boolean add(Card card) {
		deck.add(card);
		return true;
	}

	public boolean shuffle() {
		Collections.shuffle(deck);
		return true;
	}
}