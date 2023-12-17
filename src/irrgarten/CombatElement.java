package irrgarten;

public class CombatElement {
    private float effect;
    private int uses;
    
    public CombatElement(float effect, int uses) {
        this.effect = effect;
        this.uses = uses;
    }
    protected float produceEffect() {
       float exit=0.0f;
       if(this.uses>0){
        this.uses--;
        exit=this.effect;
       }
       return exit;
    }
    public boolean discard() {
        return Dice.discardElement(uses);
    }
    @Override
    public String toString() {
        return "["+this.effect+","+this.uses+"]";
    }
}
