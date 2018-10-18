package cosc4353;

import java.util.ArrayList;

public class TurnManager {

    private ActionManager actionManager;

    private int playersTurn;
    private int turnNumber;
    private int numberofplayers;
    private ArrayList<Player> Players;
    private Board board;

    public TurnManager(ArrayList<Player> Players, Board board) {
        playersTurn = 0; //index of arraylist Players
        turnNumber = 1;
        numberofplayers = Players.size();
        this.Players = Players;
        this.board = board;

        actionManager = new ActionManager();
    }

    public void attack() {

    }

    public boolean takeTurn() {
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