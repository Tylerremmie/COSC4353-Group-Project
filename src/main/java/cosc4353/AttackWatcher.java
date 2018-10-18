package cosc4353;

class AttackWatcher extends Observer {

    public AttackWatcher(TurnManager turn) {
        this.turn = turn;
        turn.add(this);
    }

    public void announce() {
        System.out.println("=======================================================================================================");
        System.out.println("AttackWatcher Observer: " + turn.getDefendingPlayer().getName() + ", your territory " + turn.getDefendingTerritory().getName() + " is under attack by Player " + turn.getAttackingPlayer().getName() + ": " + turn.getAttackingTerritory().getName() + ".");
        System.out.println("=======================================================================================================");
    }
}