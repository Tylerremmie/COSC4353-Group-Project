import java.util.ArrayList;

import javax.smartcardio.Card;

public class Hand {

    private ArrayList<Card> hand;
    private boolean turnin; //Hand size too large, must turn in cards (5 i think?)

    public Hand (){
        hand = new ArrayList<Card>();
    }

    public void insert(Card card) {
        hand.add(card);
    }

    public void turninCards() {
        //Work in progress
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}