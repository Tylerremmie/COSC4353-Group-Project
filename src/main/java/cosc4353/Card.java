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

    public void setTypeofCard(String typeofCard) {
        this.typeofCard = typeofCard;
    }

    public Territory getTerritory() {
        return territoryofCard;
    }

    public void setTerritory(Territory territoryofCard) {
        this.territoryofCard = territoryofCard;
    }
}