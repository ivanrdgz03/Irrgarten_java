package irrgarten;
import java.util.ArrayList;

public class Player extends LabyrinthCharacter{
    private static final int MAX_WEAPONS = 2;
    private static final int MAX_SHIELDS = 3;
    private static final int INITIAL_HEALTH = 10;
    private static final int HITS2LOSE = 3;
    
    private char number;
    private int consecutiveHits;
    private ShieldCardDeck shieldCardDeck;
    private WeaponCardDeck weaponCardDeck;
    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;
    
    public Player(char number, float intelligence, float strength){
        super("Player #"+number, intelligence, strength, Player.INITIAL_HEALTH);
        this.number = number;
        this.consecutiveHits = 0;
        this.weapons = new ArrayList<>();
        this.shields = new ArrayList<>();
        this.weaponCardDeck = new WeaponCardDeck();
        this.shieldCardDeck = new ShieldCardDeck();
    }
    public Player (Player other) {
        super(other);
        this.number = other.number;
        this.consecutiveHits = other.consecutiveHits;
        this.weapons = other.weapons;
        this.shields = other.shields;
        this.weaponCardDeck = other.weaponCardDeck;
        this.shieldCardDeck = other.shieldCardDeck;
    }
    public void resurrect(){
        this.weapons.clear();
        this.shields.clear();
        this.setHealth(Player.INITIAL_HEALTH);
        this.resetHits();
    }
    public char getNumber(){
        return this.number;
    }
    public Directions move(Directions direction,ArrayList<Directions> validMoves){
       int size = validMoves.size();
       Directions output;
       boolean contained = validMoves.contains(direction);
       if(size>0 && !contained){
           Directions firstElement = validMoves.get(0);
           output = firstElement;
       }
       else
           output = direction;
       
       return output;
    }
    @Override
    public float attack(){
        return this.getStrength() + this.sumWeapons();
    }
    @Override
    public boolean defend(float receivedAttack){
        return this.manageHit(receivedAttack);
    }
    public void receiveReward(){
        int wReward = Dice.weaponsReward();
        int sReward = Dice.shieldsReward();
        for(int i = 1; i < wReward; i++){
            Weapon wnew = this.weaponCardDeck.nextCard();
            this.receiveWeapon(wnew);
        }
        for(int i = 1; i< sReward;i++){
            Shield snew = this.shieldCardDeck.nextCard();
            this.receiveShield(snew);
        }
        int extraHealth = Dice.healthReward();
        this.setHealth(this.getHealth() + extraHealth);
    }
    @Override
    public String toString(){
      String weapons_string = "";
      String shields_string = "";
      for (Weapon weapon : this.weapons)
          weapons_string += " " + weapon.toString();
      for (Shield shield : this.shields)
          shields_string += " " + shield.toString();

        return "P[" + "Player #"+this.number + ", " + this.getIntelligence() + ", " + this.getStrength() + ", " + this.getHealth() + ", " + this.getRow() + ", " + this.getCol() + ", " + this.consecutiveHits + ", " + weapons_string + ", " + shields_string + "]";
    }
    private void receiveWeapon(Weapon w){
        for (int i = this.weapons.size()-1; i >= 0;i--){
            boolean discard = this.weapons.get(i).discard();
            if(discard)
                this.weapons.remove(this.weapons.get(i));
        }
        int size = this.weapons.size();
        if(size< Player.MAX_WEAPONS)
            this.weapons.add(w);
    }
    private void receiveShield(Shield s){
        for (int i = this.shields.size()-1; i >= 0;i--){
            boolean discard = this.shields.get(i).discard();
            if(discard)
                this.shields.remove(this.shields.get(i));
        }
        int size = this.shields.size();
        if(size< Player.MAX_SHIELDS)
            this.shields.add(s);
    }
    private Weapon newWeapon(){
        Weapon aux = new Weapon(Dice.weaponPower(), Dice.usesLeft());        
        
        return aux;
    }
    private Shield newShield(){
        Shield aux = new Shield(Dice.shieldPower(), Dice.usesLeft());
        
        return aux;
    }
    protected float sumWeapons(){
        float suma = 0;
            for(Weapon weapon : this.weapons)
                if(weapon != null)
                    suma += weapon.attack();
        
            
        
        return suma;
    }
    protected float sumShields(){
        float suma = 0;
        if(!this.weapons.isEmpty())
            for(Shield shield : this.shields)
                if(shield != null)
                    suma += shield.protect();

        
        return suma;
    }
    protected float defensiveEnergy(){
        return super.getIntelligence() + this.sumShields();
    }
    private boolean manageHit(float receivedAttack){
        float defense = this.defensiveEnergy();
        boolean lose;
        if(defense < receivedAttack){
            this.gotWounded();
            this.incConsecutiveHits();
        }
        else
            this.resetHits(); //Preguntar si esto está bien por el diagrama UML
        
        if((this.consecutiveHits == Player.HITS2LOSE) || this.dead()){
            this.resetHits();
            lose = true;
        }
        else{
            lose = false; //Preguntar si esta bien por el UML
        }
        
        return lose;
    }
    private void resetHits(){
        this.consecutiveHits = 0;
    }
    private void incConsecutiveHits(){
        this.consecutiveHits++;
    }
}