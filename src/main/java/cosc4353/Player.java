package cosc4353;

import java.util.HashMap;
import java.util.ArrayList;

public class Player {

    private String name;
    private String color;
    private int numberofArmies;
    private int turnPosition;
    private HashMap<String, Territory> territoriesControlled;
    private HashMap<String, Continent> continentControlled;
    private Hand hand;

    //Constructor for a new player
    public Player(String name, String color){
        this.name = name;
        this.color = color;
        turnPosition = 0;

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

    public ArrayList<Territory> getTerritories() {
        return new ArrayList<Territory>(territoriesControlled.values());
    }

    public ArrayList<Continent> getContinents() {
        return new ArrayList<Continent>(continentControlled.values());
    }

    public ArrayList<Card> getCards() {
        return hand.getCards();
    }

    public Hand getHand() {
        return hand;
    }

    public void turnIn(int[] cards) {
        hand.turnInCards(cards[0], cards[1], cards[2]);
    }
}