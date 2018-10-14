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

    public boolean buyCredit() {
    	System.out.println("Would you like to purchase in game credit?");
    	Engine.purchaseInGameCredit();
        return true;
    }
    
    public boolean buyCards() {
    	System.out.println("what card do you want to buy?");
    	
        return true;
    }
    ////////////////////////////////////////////////////
    // TurnManager GET AND SET functions
    ////////////////////////////////////////////////////

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