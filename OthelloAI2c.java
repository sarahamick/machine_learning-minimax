import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

public abstract class OthelloAI2c extends OthelloAI2 {
    private int totalTokens;



    @Override
    public double evaluate(GameState state, int player) {
        if(super.previousStates.containsKey(state)){ return previousStates.get(state.getBoard());}
        int[][] board = state.getBoard();
        totalTokens = state.countTokens()[0] + state.countTokens()[1];
        double playerTokens = state.countTokens()[player - 1];
        double opponentTokens = totalTokens - playerTokens;
        double standingRatio = (playerTokens - opponentTokens) / (playerTokens + opponentTokens);
        double standing = standingRatio * 100;
        double change = checkChange(state, player);
        double heuristic = stabilityMeasure(state, player) +  standing + mobility(state, player) + change;
        return heuristic;
    }

    public abstract double mobility(GameState state, int player);

    public abstract double stabilityMeasure(GameState state, int player);

    public abstract double checkChange(GameState state, int player);


}

