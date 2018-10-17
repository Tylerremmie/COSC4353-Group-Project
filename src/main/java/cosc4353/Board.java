package cosc4353;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class for creating all continent and territory objects for the "board".
 * Called at the beginning of the game to initialize the "board".
 * @author Tyler Remmie
 */

public class Board {

    private String[] territoriesArray;
    private String[] continentsArray;
    private String[] continentsSplitArray;
    private String[] adjacenciesArray;
    private String[] adjacenciessplitArray;

	private String territoriesFile = "src\\main\\resources\\territories.txt";
	private String continentsFile = "src\\main\\resources\\continents.txt";
    private String adjacenciesFile = "src\\main\\resources\\adjacent.txt";
    
    private BufferedReader filereader;
    private String inputline;
    private List<String> lines;

    private HashMap<String, Territory> territoriesMap;
    private HashMap<String, Continent> continentsMap;
    private ArrayList<Territory> territoriesList;
    private ArrayList<Territory> adjacenciesList;
    private ArrayList<Territory> territoriesbyContinent;
    private ArrayList<Territory> unoccupiedTerritories;

    private boolean boardLoaded;

    public Board() {

    }

    public boolean createBoard() {

        boardLoaded = false;

        try {
            //Read Inputs///////////////////////////////////////////////
            //Read Territories Input
            filereader = new BufferedReader(new FileReader(territoriesFile));
            lines = new ArrayList<String>();

            while ((inputline = filereader.readLine()) != null)
                lines.add(inputline);
            territoriesArray = lines.toArray(new String[]{});

            //System.out.println(Arrays.toString(territoriesArray));

            //Read Adjacency Input
            filereader = new BufferedReader(new FileReader(adjacenciesFile));
            lines = new ArrayList<String>();

            while ((inputline = filereader.readLine()) != null)
                lines.add(inputline);
            adjacenciesArray = lines.toArray(new String[]{});

            //System.out.println(Arrays.toString(adjacenciesArray));

            //Read Continent Input
            filereader = new BufferedReader(new FileReader(continentsFile));
            lines = new ArrayList<String>();

            while ((inputline = filereader.readLine()) != null)
                lines.add(inputline);
            continentsArray = lines.toArray(new String[]{});

            //System.out.println(Arrays.toString(continentsArray));

            /////Load Board////////////////////////////////////////////////
            territoriesMap = new HashMap<String, Territory>();
            continentsMap = new HashMap<String, Continent>();
            territoriesList = new ArrayList<Territory>();

            // Create Territories
            for(int i = 0; i < territoriesArray.length; i++) {
                Territory temp = new Territory(territoriesArray[i]);
                territoriesMap.put(territoriesArray[i], temp);
                territoriesList.add(temp);
            }

            // Create Adjacencies
            for(int i = 0; i < adjacenciesArray.length; i++) {
                adjacenciessplitArray = adjacenciesArray[i].split(",");
                adjacenciesList = new ArrayList<Territory>();

                for(int j = 1; j < adjacenciessplitArray.length; j++)
                    adjacenciesList.add(territoriesMap.get(adjacenciessplitArray[j]));

                territoriesMap.get(adjacenciessplitArray[0]).createAdjacencies(adjacenciesList);
            }

            // Create Continents
            for(int i = 0; i < continentsArray.length; i++) {
                continentsSplitArray = continentsArray[i].split(",");
                territoriesbyContinent = new ArrayList<Territory>();

                for(int j = 2; j < continentsSplitArray.length; j++) 
                    territoriesbyContinent.add(territoriesMap.get(continentsSplitArray[j]));
                
                continentsMap.put(continentsSplitArray[0], new Continent(continentsSplitArray[0], Integer.parseInt(continentsSplitArray[1]), territoriesbyContinent));
            }

            boardLoaded = true;
            return boardLoaded;

        }   catch (FileNotFoundException error) {
                System.out.println(error.getMessage());
                return false;
        }   catch (IOException error) {
			    System.out.println(error.getMessage());
			    return false;
		}

    }

    // Board Functionality ////////////////////////////////////////////////////////
    // return ArrayList of all territories on the board
    public ArrayList<Territory> getTerritories() {
        return territoriesList;
    }

    // return a Continent object from a provided String name
    public Continent getContinentObject(String continentName) {
        return continentsMap.get(continentName);
    }

    // return a Territory object from a provided String name
    public Territory getTerritoryObject(String territoryName) {
        return territoriesMap.get(territoryName);
    }

    // return the bonus army value of a Continent object from a provided String name
    public int getBonusArmyValue(String continentName) {
        return continentsMap.get(continentName).getBonusArmyValue();
    }

    // return ArrayList of Territory objects located in Continent from a provided String name
    public ArrayList<Territory> getTerritoriesInContinent(String continentName) {
        return continentsMap.get(continentName).getTerritories();
    }

    // set a player as the occupant of a territory by string territoryName
    // will set isOccupied = true
    public void setPlayerOccupying(String territoryName, Player player) {
        territoriesMap.get(territoryName).setPlayer(player);
    }

    // return Player object of player currently occupying territory given by territoryName
    public Player getPlayerOccupying(String territoryName) {
        return territoriesMap.get(territoryName).getPlayerOccupying();
    }

    // set the number of armies in the territory given by territoryName
    public void setNumberofArmies(String territoryName, int armynumber) {
        territoriesMap.get(territoryName).setnumberofArmies(armynumber);
    }

    // increase the number of armies by int number
    public void incrementArmies(String territoryName, int number) {
        territoriesMap.get(territoryName).increaseArmies(number);
    }

    // decrease the number of armies by int number
    public void decrementArmies(String territoryName, int number) {
        territoriesMap.get(territoryName).decreaseArmies(number);
    }

    // return number of armies in the territory given by territoryName
    public int getNumberofArmies(String territoryName) {
        return territoriesMap.get(territoryName).getnumberofArmies();
    }

    // return ArrayList of adjacent territories to the territory given by territoryName
    public ArrayList<Territory> getAdjacent(String territoryName) {
        return territoriesMap.get(territoryName).getAdjacencies();
    }

    // return ArrayList of territories that are unoccupied
    public ArrayList<Territory> getUnoccupied() {
        unoccupiedTerritories = new ArrayList<Territory>();

        for(int i = 0; i < territoriesList.size(); i++)
            if(territoriesList.get(i).isOccupied() == false)
                unoccupiedTerritories.add(territoriesList.get(i));
        
        return unoccupiedTerritories;
    }

    // return boolean true if atleast one territory is unoccupied; else returns false
    public boolean hasUnoccupied() {
        for(int i = 0; i < territoriesList.size(); i++) {
            if(territoriesList.get(i).isOccupied() == false)
                return true;
        }
        return false;
    }
    
    // return boolean true or false if String territoryOne is adjacent to String territoryTwo
    public boolean isAdjacent(String territoryOne, String territoryTwo) {
        if(territoriesMap.get(territoryOne).getAdjacencies().contains(territoriesMap.get(territoryTwo)))
            return true;
        else
            return false;
    }

    // get functions for String Arrays from file////////////////
    public String[] getTerritoriesString() {
        return territoriesArray;
    }

    public String[] getContinentsString() {
        return continentsArray;
    }

    public String[] getAdjacenciesString() {
        return adjacenciesArray;
    }
}