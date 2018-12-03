package cosc4353;

import java.util.ArrayList;
import java.util.List;

public class TurnManager {

    private ActionManager actionManager;

    private int playersTurn;
    private int turnNumber;
    private int numberofplayers;
    private int territoriesConquered;
    private ArrayList<Player> Players;
    private Board board;

    private Territory attackingTerritory;
    private Territory defendingTerritory;
    private Player attackingPlayer;
    private Player defendingPlayer;
    private Tweets twitter = new Tweets();

    private List<Observer> observers = new ArrayList<>();

    public TurnManager(ArrayList<Player> Players, Board board) {
        playersTurn = 0; //index of arraylist Players
        turnNumber = 1;
        territoriesConquered = 0;
        numberofplayers = Players.size();
        this.Players = Players;
        this.board = board;

        actionManager = new ActionManager();
    }

    public boolean takeTurn() {
    	twitter.sendTweet("Player " + (this.playersTurn + 1) + " conquered " + this.territoriesConquered + " territories on turn " + this.turnNumber + ".");
        actionManager.executeAction(new Turn(this));
        return true;
    }

    public boolean undo() {
        if(actionManager.isUndoAvailable())
            actionManager.undo();
        return true;
    }

    public boolean redo() {
        if(actionManager.isRedoAvailable())
            actionManager.redo();
        return true;
    }

    public void add(Observer o) {
        observers.add(o);
    }

    public void attackState(Territory defendingTerritory, Territory attackingTerritory) {
        this.defendingTerritory = defendingTerritory;
        this.attackingTerritory = attackingTerritory;
        this.defendingPlayer = defendingTerritory.getPlayerOccupying();
        this.attackingPlayer = attackingTerritory.getPlayerOccupying();
        attack();
    }

    public Territory getAttackingTerritory() {
        return attackingTerritory;
    }

    public Territory getDefendingTerritory() {
        return defendingTerritory;
    }

    public Player getAttackingPlayer() {
        return attackingPlayer;
    }

    public Player getDefendingPlayer() {
        return defendingPlayer;
    }

    private void attack() {
        for(Observer observer : observers) {
            observer.announce();
        }
    }

    //////////////////////////////////////////////////
    // TurnManager GET AND SET functions
    //////////////////////////////////////////////////

    public ArrayList<Territory> getAttackableTerritories() {
        ArrayList<Territory> attackableTerritories = new ArrayList<Territory>();
        Player currentPlayer = Players.get(this.playersTurn);

        ArrayList<Territory> ownedTerritories = currentPlayer.getTerritories();
        ArrayList<Territory> adjacentTerritories;

        for(int i = 0; i < ownedTerritories.size(); i++) {
            adjacentTerritories = new ArrayList<Territory>(ownedTerritories.get(i).getAdjacencies());
                for(int j = 0; j < adjacentTerritories.size(); j++) {
                    if(adjacentTerritories.get(j).getPlayerOccupying() != currentPlayer && !attackableTerritories.contains(adjacentTerritories.get(j)))
                        attackableTerritories.add(adjacentTerritories.get(j));
                }
        }
        return attackableTerritories;
    }

    public ArrayList<Territory> getControlledAdjacent(Territory enemyTerritory) {
        ArrayList<Territory> friendlyAdjacent = new ArrayList<Territory>();
        ArrayList<Territory> enemyAdjacent = enemyTerritory.getAdjacencies();

        Player currentPlayer = Players.get(this.playersTurn);

        for(int i = 0; i < enemyAdjacent.size(); i++) {
            if(enemyAdjacent.get(i).getPlayerOccupying() == currentPlayer)
                friendlyAdjacent.add(enemyAdjacent.get(i));
        }
        return friendlyAdjacent;
    }

    public int getplayersTurn() {
        return playersTurn;
    }

    public boolean setplayersTurn(int playersTurn) {
        this.playersTurn = playersTurn;
        return true;
    }

    public int getturnNumber() {
        return turnNumber;
    }

    public boolean setturnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
        return true;
    }

    public int getnumberofPlayers() {
        return numberofplayers;
    }

    public boolean setnumberofPlayers(int numberofPlayers) {
        this.numberofplayers = numberofPlayers;
        return true;
    }

    public boolean incrementplayer() {
        if(playersTurn < (numberofplayers - 1))
            playersTurn++;
        else
            playersTurn = 0;

        return true;
    }

    public boolean decrementplayer() {
        if(playersTurn > 0)
            playersTurn--;
        else
            playersTurn = (numberofplayers - 1);

        return true;
    }

    public boolean incrementturn() {
        turnNumber++;
        return true;
    }

    public boolean decrementturn() {
        turnNumber--;
        return true;
    }

    public String getCurrentPlayerName() {
        return Players.get(playersTurn).getName();
    }

    public Player getCurrentPlayerObject() {
        return Players.get(playersTurn);
    }

    public Player getPlayerObject(int playerNumber) {
        return Players.get(playerNumber);
    }
    public ArrayList<Player> getPlayersObject() {
        return Players;
    }

    public Board getBoardObject() {
        return board;
    }

    //////////////////////////////////////////////////
    // Turn Object for Stack
    //////////////////////////////////////////////////

    private class Turn implements Action{
        private TurnManager manager;
        private Turn(TurnManager manager) {
            this.manager = manager;
        }

        public void execute() {
            manager.incrementturn();
            manager.incrementplayer();
        }

        public void undo() {
            manager.decrementturn();
            manager.decrementplayer();
        }
    }
    
}