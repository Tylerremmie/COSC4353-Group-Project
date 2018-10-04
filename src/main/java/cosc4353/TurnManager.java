package cosc4353;

import java.util.ArrayList;

public class TurnManager {

    private ActionManager actionManager;

    private int playersTurn;
    private int turnNumber;
    private int numberofplayers;
    private ArrayList<Player> Players;

    public TurnManager(ArrayList<Player> Players) {
        playersTurn = 0; //index of arraylist Players
        turnNumber = 1;
        numberofplayers = Players.size();
        this.Players = Players;

        actionManager = new ActionManager();
    }

    public void takeTurn() {
        actionManager.executeAction(new Turn(this));
    }

    public void undo() {
        if(actionManager.isUndoAvailable())
            actionManager.undo();
    }

    public void redo() {
        if(actionManager.isRedoAvailable())
            actionManager.redo();
    }

    //////////////////////////////////////////////////
    // TurnManager GET AND SET functions
    //////////////////////////////////////////////////

    public int getplayersTurn() {
        return playersTurn;
    }

    public void setplayersTurn(int playersTurn) {
        this.playersTurn = playersTurn;
    }

    public int getturnNumber() {
        return turnNumber;
    }

    public void setturnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public int getnumberofPlayers() {
        return numberofplayers;
    }

    public void setnumberofPlayers(int numberofPlayers) {
        this.numberofplayers = numberofPlayers;
    }

    public void incrementplayer() {
        if(playersTurn < (numberofplayers - 1))
            playersTurn++;
        else
            playersTurn = 0;
    }

    public void decrementplayer() {
        if(playersTurn > 0)
            playersTurn--;
        else
            playersTurn = (numberofplayers - 1);
    }

    public void incrementturn() {
        turnNumber++;
    }

    public void decrementturn() {
        turnNumber--;
    }

    public String getCurrentPlayerName() {
        return Players.get(playersTurn).getName();
    }

    public ArrayList<Player> getPlayersObject() {
        return Players;
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