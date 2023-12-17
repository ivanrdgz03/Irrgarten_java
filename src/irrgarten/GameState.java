package irrgarten;

public class GameState {
    private String labyrinth;
    private String players;
    private String monsters;
    private int currentPlayer;
    private boolean winner;
    private String log;
    
    public GameState(String labyrinth, String players, String monsters,
            int currentPlayer, boolean winner, String log){
        this.labyrinth = labyrinth;
        this.players = players;
        this.monsters = monsters;
        this.currentPlayer = currentPlayer;
        this.winner = winner;
        this.log = log;
    }
    
    @Override
    public String toString(){
        return ("Current player: " + this.currentPlayer + "\nWinner: " + this.winner + "\nPlayers: " + this.players + "\nMonsters: " + this.monsters + "\nLabyrinth: " + this.labyrinth + "\nLog: " + this.log + '\n');
    }
    
    public String getLabyrinth(){
        return this.labyrinth;
    }
    
    public String getPlayers(){
        return this.players;
    }
    
    public String getMonsters(){
        return this.monsters;
    }
    
    public int getCurrentPlayer(){
        return this.currentPlayer;
    }
    
    public boolean getWinner(){
        return this.winner;
    }
    
    public String getLog(){
        return this.log;
    }
}