package irrgarten;

public class WeaponCardDeck extends CardDeck<Weapon> {
    
    @Override 
    protected void addCards() {
        for(int i = CardDeck.NUMCARDS; i != 0; i--)
            this.addCard(new Weapon(Dice.weaponPower(), Dice.usesLeft()));
    }}
