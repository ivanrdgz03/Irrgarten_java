package irrgarten;
import java.util.ArrayList;

public class FuzzyPlayer extends Player{
    public FuzzyPlayer(Player other) {
        super(other);
    }
    @Override
    public Directions move(Directions direction, ArrayList<Directions> validMoves) {
        return Dice.nextStep(direction, validMoves, this.getIntelligence());
    }
    @Override
    public float attack() {
        return super.attack() + Dice.intensity(this.getStrength());
    }
    @Override
    protected float defensiveEnergy() {
        return super.defensiveEnergy() + Dice.intensity(this.getIntelligence());
    }
    @Override
    public String toString() {
        return "Fuzzy" + super.toString();
    }
}
