import java.util.Objects;

public class GameStateWithFrequency implements Comparable<GameStateWithFrequency> {
    private GameState gameState;
    private double utility;
    private int frequency;

    public GameStateWithFrequency(GameState gameState, double utility, int frequency){
     this.gameState = gameState;
     this.utility = utility;
     this.frequency = frequency;
    }

    public GameState getGameState() {
        return gameState;
    }

    public double getUtility() {
        return utility;
    }

    public int getFrequency() {
        return frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameStateWithFrequency that = (GameStateWithFrequency) o;
        return Objects.equals(gameState, that.gameState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameState);
    }

    @Override
    public int compareTo(GameStateWithFrequency that) {
        if(this.frequency>that.frequency) return 1;
        if(that.frequency>this.frequency) return -1;
        return 0;
    }

    public void incrementFrequency() {
        this.frequency++;
    }

    public void setUtility(double utility) {
        this.utility = utility;
    }
}
