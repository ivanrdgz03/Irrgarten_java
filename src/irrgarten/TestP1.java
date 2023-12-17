package irrgarten;

public class TestP1 {
    public static void prueba(){
        Weapon weapon = new Weapon(Dice.weaponPower(),Dice.usesLeft());
        Shield shield = new Shield(Dice.shieldPower(),Dice.usesLeft());
        int nplayers = 2;
        int ancho = 10;
        int alto = 10;
        float competence=10.0f;
        int salida=0;
        final int nEnsayos = 100;
        GameState gameStatus= new GameState("laberinto","3","4",1,true,"log");
        
        System.out.println("Log = "+gameStatus.getLog());
        System.out.println("Labyrinthv = "+gameStatus.getLabyrinth());
        System.out.println("Players = "+gameStatus.getPlayers());
        System.out.println("Monsters = "+gameStatus.getMonsters());
        System.out.println("Current player = "+gameStatus.getCurrentPlayer());
        System.out.println("Winner = "+gameStatus.getWinner());

        
        System.out.println(weapon.toString());
        System.out.println(shield.toString());
        System.out.println("Weapon attack = "+weapon.attack());
        System.out.println("Shield protect = "+shield.protect());
        System.out.println(weapon.toString());
        System.out.println(shield.toString());
        
        System.out.println("Who Start = "+Dice.whoStarts(nplayers));
        System.out.println("InitialPos = ["+Dice.randomPos(ancho)+", "+Dice.randomPos(alto)+"]");
        System.out.println("Intelligence = "+Dice.randomIntelligence());
        System.out.println("Resurrect Player = "+Dice.resurrectPlayer());
        System.out.println("Weapons Reward ="+Dice.weaponsReward());
        System.out.println("Shield Reward ="+Dice.shieldsReward());
        System.out.println("Health Reward ="+Dice.healthReward());
        System.out.println("Intensity = "+Dice.intensity(competence));
        for(int i=0; i<=nEnsayos;i++){
            if(Dice.resurrectPlayer()){
                ++salida;
            }
        }
     
        
        System.out.println((float)salida/nEnsayos);

    }
}
