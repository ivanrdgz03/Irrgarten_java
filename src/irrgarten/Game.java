package irrgarten;
import java.util.ArrayList;

public class Game {
    private static final int MAX_ROUNDS = 10;
    private int currentPlayerIndex;
    private String log;
    private Labyrinth labyrinth;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private ArrayList<Monster> monsters;
    
    public Game(int nplayers){
        //Este if se debe a que la implementacion del ID del jugador daría problemas si es mas de 10 ya que juega con los valores ASCII, pero por lo demás no habría ningún problema
        if(nplayers <= 0 || nplayers > 9)
            throw new IndexOutOfBoundsException("Invalid number of players");
        
        int nRows = 3;
        int nCols = 3;
        int exitRow = 2;
        int exitCol = 2;
        
        this.players = new ArrayList<>();
        for(int n = 0; n < nplayers; n++)
            this.players.add(new Player((char)(n + '0'), Dice.randomIntelligence(),Dice.randomStrength()));
        
        this.currentPlayerIndex = Dice.whoStarts(nplayers);
            
        this.monsters = new ArrayList<>();
        this.log = "";
        this.currentPlayer = this.players.get(this.currentPlayerIndex);
        this.labyrinth = new Labyrinth(nRows,nCols,exitRow,exitCol);
        this.configureLabyrinth();        
    }
    public boolean finished(){
        return this.labyrinth.haveAWinner();
    }
    public boolean nextStep(Directions preferredDirection){
        this.log = "";
        boolean dead = this.currentPlayer.dead();
        if(!dead){
            Directions direction = this.actualDirection(preferredDirection);
            if(direction != preferredDirection)
                this.logPlayerNoOrders();
            
            Monster monster =  this.labyrinth.putPlayer(direction, currentPlayer);
            if(monster == null)
                this.logNoMonster();
            else{
                GameCharacter winner = this.combat(monster);
                this.manageReward(winner);
            }
        }
        else
            this.manageResurrection();
        
        boolean endGame = this.finished();
        if(!endGame)
            this.nextPlayer();
        
        return endGame;
    }
    public GameState getGameState(){
        return new GameState(this.labyrinth.toString(), this.players.toString(), this.monsters.toString(), this.currentPlayerIndex, this.finished(),this.log);
    }
    private void configureLabyrinth(){
            Monster monster = new Monster("1", Dice.randomIntelligence(), Dice.randomStrength());
            this.monsters.add(monster);
            this.monsters.add(monster);
            this.labyrinth.addMonster(2, 0, monster);
            monster = new Monster("1", Dice.randomIntelligence(), Dice.randomStrength());
            this.labyrinth.addMonster(2,1, monster);
            this.labyrinth.addBlock(Orientation.HORIZONTAL, 0, 1, 2);
            this.labyrinth.spreadPlayers(players.toArray(new Player[0]));
    }
    private void nextPlayer(){
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.size();
        this.currentPlayer = this.players.get(currentPlayerIndex);
    }
    private Directions actualDirection(Directions preferredDirection){
        int currentRow = this.currentPlayer.getRow();
        int currentCol = this.currentPlayer.getCol();
        ArrayList<Directions> validMoves = this.labyrinth.validMoves(currentRow, currentCol);
        
        Directions output = this.currentPlayer.move(preferredDirection, validMoves);
        return output;
    }
    private GameCharacter combat(Monster monster){
        int rounds = 0;
        GameCharacter winner = GameCharacter.PLAYER;
        float playerAttack = this.currentPlayer.attack();
        boolean lose = monster.defend(playerAttack);
        
        while(!lose && (rounds < Game.MAX_ROUNDS)){
            winner = GameCharacter.MONSTER;
            rounds++;
            float monsterAttack = monster.attack();
            lose = this.currentPlayer.defend(monsterAttack);
            if(!lose){
                playerAttack = this.currentPlayer.attack();
                winner = GameCharacter.PLAYER;
                lose = monster.defend(playerAttack);
            }
        }
        this.logRounds(rounds, Game.MAX_ROUNDS);
        
        return winner;
    }
    private void manageReward(GameCharacter winner){
        if(winner == GameCharacter.PLAYER){
            this.currentPlayer.receiveReward();
            this.logPlayerWon();
        }
        else
            this.logMonsterWon();
    }
    private void manageResurrection(){
        boolean resurrect = Dice.resurrectPlayer();
        if(resurrect){
            this.currentPlayer.resurrect();
            FuzzyPlayer fuzzy = new FuzzyPlayer(this.currentPlayer);
            this.labyrinth.setFuzzyPlayer(fuzzy, currentPlayer);
            this.currentPlayer = fuzzy;
            this.players.set(this.currentPlayerIndex, fuzzy);
            this.logResurrected();
        }
        else
            this.logPlayerSkipTurn();
    }
    private void logPlayerWon(){
        this.log += "The player " + this.currentPlayer.getNumber() + " has won the match\n";
    }
    private void logMonsterWon(){
        this.log += "The monster has won the combat\n";
    }
    private void logResurrected(){
        this.log += "The player " + this.currentPlayer.getNumber() + " has resurrected\n";
    }
    private void logPlayerSkipTurn(){
        this.log += "The player " + this.currentPlayer.getNumber() + " has lost his turn because he is dead\n";
    }
    private void logPlayerNoOrders(){
        this.log += "The player " + this.currentPlayer.getNumber() + " has not followed the instructions of the human player (not possible)\n";
    }
    private void logNoMonster(){
        this.log += "The player " + this.currentPlayer.getNumber() + " has moved to an empty cell or has been unable to move\n";
    }
    private void logRounds(int rounds, int max){
        this.log += rounds + " of " + max + " combat rounds have occurred\n";
    }
}
