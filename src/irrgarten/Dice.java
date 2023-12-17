package irrgarten;

import java.util.Random;
import java.util.ArrayList;

public class Dice {
    private static final int MAX_USES = 5;
    private static final float MAX_INTELLIGENCE = 10.0f;
    private static final float MAX_STRENGTH = 10.0f;
    private static final float RESURRECT_PROB = 0.3f;
    private static final int WEAPONS_REWARD = 2;
    private static final int SHIELD_REWARD = 3;
    private static final int HEALTH_REWARD = 5;
    private static final int MAX_ATTACK = 3;
    private static final int MAX_SHIELD = 2;
    
    private static final Random GENERATOR = new Random();
   
    public static int randomPos(int max){
        return GENERATOR.nextInt(max);
    }
    
    public static int whoStarts(int nplayers){
        return GENERATOR.nextInt(nplayers);
    }
    
    public static float randomIntelligence(){
        return GENERATOR.nextFloat()*MAX_INTELLIGENCE;
    }
    
    public static float randomStrength(){
        return GENERATOR.nextFloat()*MAX_STRENGTH;
    }
    
    public static boolean resurrectPlayer(){
        return GENERATOR.nextFloat()<RESURRECT_PROB;
    }
    
    public static int weaponsReward(){
        return GENERATOR.nextInt(WEAPONS_REWARD + 1);
    }
    
    public static int shieldsReward(){
        return GENERATOR.nextInt(SHIELD_REWARD + 1);
    }
    
    public static int healthReward(){
        return GENERATOR.nextInt(HEALTH_REWARD);
    }
    
    public static float weaponPower(){
        return GENERATOR.nextFloat()*MAX_ATTACK;
    }
    
    public static float shieldPower(){
        return GENERATOR.nextFloat()*MAX_SHIELD;
    }
    
    public static int usesLeft(){
        return GENERATOR.nextInt(MAX_USES);
    }
    
    public static float intensity(float competence){
        return GENERATOR.nextFloat()*competence;
    }
    
    public static boolean discardElement(int usesLeft){
        float prob = 1.0f - ((float)usesLeft / MAX_USES);
        return GENERATOR.nextFloat()<prob;
    }
    
    public static Directions nextStep(Directions preference, ArrayList<Directions> validMoves, float intelligence) {
        Directions salida;
        if (Dice.randomIntelligence() < intelligence)
            salida = preference;
        else
            salida = validMoves.get(Dice.GENERATOR.nextInt(validMoves.size()));
        
        return salida;
    }
    
}
