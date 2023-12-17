package irrgarten;

public class Weapon extends CombatElement{
   public Weapon(float power, int uses){
       super(power, uses);
   }
   
   public float attack(){
       return super.produceEffect();
   }
   
   @Override
   public String toString(){
       return "W" + super.toString();
   }
}
