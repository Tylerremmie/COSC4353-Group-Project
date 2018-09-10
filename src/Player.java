import java.util.HashMap;
import java.util.Map;

public class Player {

    private String name;
    private String color;
    private int numberofArmies;
    private int turnPosition;
    private Map<String, Territory> territoriesControlled = new HashMap<>();
    private Map<String, Continent> continentControlled = new HashMap<>();

    //Constructor for a new player
    public Player(String name, String color){
        this.name = name;
        this.color = color;
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

}