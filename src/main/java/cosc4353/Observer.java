package cosc4353;

abstract class Observer {
    protected TurnManager turn;
    public abstract boolean announce();
}