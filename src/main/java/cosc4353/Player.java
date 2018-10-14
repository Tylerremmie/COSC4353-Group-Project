package cosc4353;

import java.util.HashMap;
import java.util.ArrayList;

public class Player {

    private String name;
    private String color;
    private int cardNumber;
    private int numberofArmies;
    private int turnPosition;
    private double inGameCredit;
    private HashMap<String, Territory> territoriesControlled;
    private HashMap<String, Continent> continentControlled;
    private Hand hand;

    //Constructor for a new player
    public Player(String name, String color, int turnPosition, int cardNumber) {
        this.name = name;
        this.color = color;
        this.turnPosition = turnPosition;
        this.cardNumber = cardNumber;
        territoriesControlled = new HashMap<String, Territory>();
        continentControlled = new HashMap<String, Continent>();
        hand = new Hand();
    }

    //getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getNumberofArmies() {
        return numberofArmies;
    }

    public void setNumberofArmies(int numberofArmies) {
        this.numberofArmies = numberofArmies;
    }

    public int getTurnPosition() {
        return turnPosition;
    }

    public void setTurnPosition(int turnPosition) {
        this.turnPosition = turnPosition;
    }
    
    public double getInGameCredit() {
        return inGameCredit;
    }

    public void setInGameCredit(double inGameCredit) {
        this.inGameCredit = inGameCredit;
    }
    
    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public ArrayList<Territory> getTerritories() {
        return new ArrayList<Territory>(territoriesControlled.values());
    }

    public ArrayList<Continent> getContinents() {
        return new ArrayList<Continent>(continentControlled.values());
    }
    
    public ArrayList<Card> getCardsInHand() {
        return hand.getHand();
    }
    
    public Hand getHand() {
        return hand;
    }

    public boolean turnIn(int[] cards) {
        hand.turnInCards(cards[0], cards[1], cards[2]);
        return true;
    }

}