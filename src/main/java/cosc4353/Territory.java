package cosc4353;

import java.util.ArrayList;

/**
 * Class for Territory objects.
 * @author Tyler Remmie
 * @date 9/8/2018
 */

public class Territory {

    private String name;
    private int numberofarmies;
    private Player playerOccupying;
    private boolean is_occupied;
    private ArrayList<Territory> adjacent;

    //Constructor for Territory object
    public Territory(String name) {
        this.name = name;
        is_occupied = false;
        numberofarmies = 0;
        System.out.println(name + " created.");
    }

    //SET functions

    //Called later in a board creation function
    public void createAdjacencies(ArrayList<Territory> adjacent) {
        this.adjacent = adjacent;
    }

    //Called later in a game creation function
    public void setPlayer(Player playerOccupying) {
        this.playerOccupying = playerOccupying;
        is_occupied = true;
    }

    public void setnumberofArmies(int armynumber) {
        numberofarmies = armynumber;
    }

    public void increaseArmies(int armynumber) {
        numberofarmies = numberofarmies + armynumber;
        //System.out.println(playerOccupying.getName() + " gained army size in " + name + " by " + armynumber);
    }

    public void decreaseArmies(int armynumber) {
        numberofarmies = numberofarmies - armynumber;
        //System.out.println(playerOccupying.getName() + " lost army size in " + name + " by " + armynumber);
    }

    //GET Functions
    public String getName() {
        return name;
    }

    public Player getPlayerOccupying() {
        return playerOccupying;
    }

    public int getnumberofArmies() {
        return numberofarmies;
    }

    public boolean getOccupied() {
        return is_occupied;
    }

    public ArrayList<Territory> getAdjacencies() {
        return adjacent;
    }
}