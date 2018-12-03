package cosc4353;

import java.util.ArrayList;

/**
 * Class for Continent objects.
 * @author Tyler Remmie
 */

public class Continent {

    private String name;
    private int bonusArmyValue;
    private Player playerControlling;
    private boolean is_controlled;
    private ArrayList<Territory> territories;

    //Constructor for Continent object
    public Continent(String name, int bonusArmyValue, ArrayList<Territory> territories) {
        this.name = name;
        this.bonusArmyValue = bonusArmyValue;
        this.territories = territories;
        is_controlled = false;

        //System.out.println(name + " created.");
    }

     //SET functions

    //Called later during game play
    public boolean setControl(Player playerControlling) {
        this.playerControlling = playerControlling;
        is_controlled = true;
        return true;
    }

    //GET Functions
    public String getName() {
        return name;
    }

    public int getBonusArmyValue() {
        return bonusArmyValue;
    }

    public boolean getIsControlled() {
        return is_controlled;
    }

    public Player getControlledBy() {
        return playerControlling;
    }

    public ArrayList<Territory> getTerritories() {
        return territories;
    }

}