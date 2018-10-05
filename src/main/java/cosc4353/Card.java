package cosc4353;

public class Card {
    String typeofCard;
    Territory territoryofCard;

    public Card(String typeofCard, Territory territoryofCard){
        this.typeofCard = typeofCard;
        this.territoryofCard = territoryofCard;
    }

    public String getTypeofCard() {
        return typeofCard;
    }

    public boolean setTypeofCard(String typeofCard) {
        this.typeofCard = typeofCard;
        return true;
    }

    public Territory getTerritory() {
        return territoryofCard;
    }

    public boolean setTerritory(Territory territoryofCard) {
        this.territoryofCard = territoryofCard;
        return true;
    }
}