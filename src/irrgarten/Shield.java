package irrgarten;

class Shield extends CombatElement{
    public Shield(float protection, int uses){
       super(protection, uses);
   }
   
   public float protect(){
       return super.produceEffect();
   }
   
   @Override
   public String toString(){
       return "S" + super.toString();
   }
}
