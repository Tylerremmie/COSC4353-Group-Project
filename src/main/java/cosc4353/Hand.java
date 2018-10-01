package cosc4353;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> hand;
    private boolean turnin;

    public Hand (){
        hand = new ArrayList<Card>();
    }

    public void insert(Card card) {
        hand.add(card);
    }

    public void turnInCards(int cardinHand1, int cardinHand2, int cardinHand3) {
        if(checkcards(cardinHand1, cardinHand2, cardinHand3)) {
            hand.remove(cardinHand1);
            hand.remove(cardinHand2);
            hand.remove(cardinHand3);
        }else{
            System.out.println("Cards must be all the same type, one of each type, or atleast one wild card.");
        }
    }

    public boolean handSize() {
        turnin = false;
        //5 or more cards, must turn in
        if(hand.size() >= 5) {
            turnin = true;
        }
        return turnin;
    }

    public boolean checkcards(int card1, int card2, int card3) {
        turnin = false;
        if(hand.size() >= 3) {
            // All cards are the same type
            if(hand.get(card1).getTypeofCard() == hand.get(card2).getTypeofCard() 
            && hand.get(card2).getTypeofCard() == hand.get(card3).getTypeofCard()) {
                turnin = true;
            // No cards are the same type
            } else if(hand.get(card1).getTypeofCard() != hand.get(card2).getTypeofCard() 
            && hand.get(card2).getTypeofCard() != hand.get(card3).getTypeofCard()
            && hand.get(card1).getTypeofCard() != hand.get(card3).getTypeofCard()) {
                turnin = true;
            // Atleast one card is wild.
            } else if(hand.get(card1).getTypeofCard() == "Wild" 
            || hand.get(card2).getTypeofCard() == "Wild"
            || hand.get(card3).getTypeofCard() == "Wild") {
                turnin = true;
            }
        }
        return turnin;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}