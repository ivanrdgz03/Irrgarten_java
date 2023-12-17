package irrgarten;

public class ShieldCardDeck extends CardDeck<Shield>{
    
    @Override 
    protected void addCards() {
        for(int i = CardDeck.NUMCARDS; i != 0; i--)
            this.addCard(new Shield(Dice.shieldPower(), Dice.usesLeft()));
    }
}
