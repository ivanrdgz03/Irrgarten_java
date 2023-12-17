package irrgarten.UI;
import irrgarten.Directions;
import irrgarten.GameState;

public interface UI {
    public Directions nextMove();
    public void showGame(GameState gameState);
    
}
