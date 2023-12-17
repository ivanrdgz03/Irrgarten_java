package irrgarten.main;
import irrgarten.Game;
import irrgarten.UI.GraphicalUI;
import irrgarten.controller.Controller;

public class Main {  
    public static void main(String[] args) {
       Game juego = new Game(1);
       GraphicalUI graphicalUI = new GraphicalUI();
       Controller controller = new Controller(juego,graphicalUI);
       controller.play();
    }
    
}

