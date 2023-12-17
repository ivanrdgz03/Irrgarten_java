package irrgarten;
import java.util.ArrayList;
import java.util.Collections;

public abstract class CardDeck<T> {
    private ArrayList<T> cardDeck;
    protected static final int NUMCARDS = 20;
    
    public CardDeck() {
        this.cardDeck = new ArrayList<>();
    }
    protected abstract void addCards();
    
    protected void addCard(T card) {
        this.cardDeck.add(card);
    }
    public T nextCard(){
        if (this.cardDeck.isEmpty()){
            this.addCards();
            Collections.shuffle(cardDeck);
        }
        
        T salida = this.cardDeck.get(0);
        this.cardDeck.remove(0);
        return salida;
    }
}