import java.util.*;

public abstract class OthelloAI2 implements IOthelloAI{

    private int player;
    public Map<int[][], Double> previousStates = new HashMap<>();
    private double THRESHOLD = 5;

    /**
     * @desc A method to call the minimax algorithm. Takes a gamestate, currentState, and calls the minimax algorithm for each
     * legal move of the currentState. It saves each corresponding move and it's associated utility value rendered by the
     * minimax algorithm in a map, and selects the move with the highest utility value.
     * @param currentState
     * @return the position associated with the highest utility value
     */
    public Position decideMove(GameState currentState) {
        player = currentState.getPlayerInTurn();
        long start = System.nanoTime();
        int initialStage = currentState.countTokens()[0] + currentState.countTokens()[1];
        if(!currentState.legalMoves().isEmpty()) {
            if(currentState.legalMoves().size() == 1) return currentState.legalMoves().get(0);
            Map<Double, Position> moveValues = new HashMap<>();
            for (Position move : currentState.legalMoves()) {
                GameState clonedGameState = new GameState(currentState.getBoard(), currentState.getPlayerInTurn());
                clonedGameState.insertToken(move);
                moveValues.put(minValue(clonedGameState, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, initialStage), move);
            }

            double highestValue = Double.NEGATIVE_INFINITY;
            for (double i : moveValues.keySet()) {
                if (i > highestValue) highestValue = i;
            }

            return moveValues.get(highestValue);
        } else return new Position(- 1, - 1);

    }

    /**
     * @desc Implementation of the MINIMAX algorithm with alpha-beta pruning, taken from Russel and Nordvig, 2010.
     * has been exceeded, their are not more legal moves to play, or the last ply in the game has been played.
     * @param state, alpha, beta, initialStage
     * @return the expected utility of a given ply
     */
    private double maxValue(GameState state, double alpha, double beta, int initialStage){
        if(terminalTest(state, initialStage)) return evaluate(state, player);
        double v = Double.NEGATIVE_INFINITY;
        //Order the moves be the utility of the next state and process in order from highest to lowest
        TreeMap<Double, GameState> orderedMoves = orderMoves(state);
        NavigableSet<Double> reverseOrder = orderedMoves.descendingKeySet(); //making sure to process from highest to lowest
        for(Double value : reverseOrder) {
            GameState clonedGameState = orderedMoves.get(value);
            v = max(v, minValue(clonedGameState, alpha, beta, initialStage));
            if (v >= beta) return v;
            alpha = max(alpha, v);
        }
        return v;
    }

    /**
     * @desc Implementation of the MINIMAX algorithm with alpha-beta pruning, taken from Russel and Nordvig, 2010.
     * has been exceeded, their are not more legal moves to play, or the last ply in the game has been played.
     * @param state, alpha, beta, initialStage
     * @return the expected utility of a given ply
     */
    private double minValue(GameState state, double alpha, double beta, int initialStage){
        if(terminalTest(state, initialStage)) return evaluate(state, player);
        double v = Double.POSITIVE_INFINITY;
        //Order the moves be the utility of the next state and process in order from lowest to highest
        TreeMap<Double, GameState> orderedMoves = orderMoves(state);
            for(Map.Entry<Double, GameState> entry : orderedMoves.entrySet()){
            double value = entry.getKey();
            GameState clonedGameState = orderedMoves.get(value);
            v = min(v, maxValue(clonedGameState, alpha, beta, initialStage));
            if(v <= alpha) return v;
            beta = min(beta, v);
        }
        return v;
    }

    /**
     * @desc A method to stop the recursion of the minimax algorithm. The algorithm should terminate if the threshold of plys
     * has been exceeded, their are not more legal moves to play, or the last ply in the game has been played.
     * @param state, initialStage
     * @return false if non-terminal state, true if terminal state
     */
    private boolean terminalTest(GameState state, int initialStage){
        if((THRESHOLD + initialStage) <  (state.countTokens()[0] + state.countTokens()[1])){
            return true;
        }
        if(state.legalMoves().size() == 0) return true;
        if(state.isFinished()) return true;
        return false;
    }

    /**
     * @desc This method is overwritten by multiple AIs that extend OthelloAI2 and implement different evaluation functions.
     * @param state, player
     * @return the utility of a given move
     */
    public abstract double evaluate(GameState state, int player);

    /**
     * @desc This method returns the max of two doubles.
     * @param first, second
     * @return the max of first and second
     */
    private double max(double first, double second){
        if(first > second) return first;
        else if(second > first) return second;
        else return first;
    }

    /**
     * @desc This method returns the min of two doubles.
     * @param first, second
     * @return the min of first and second
     */
    private double min(double first, double second){
        if(first < second) return first;
        else if(second < first) return second;
        else return first;
    }

    /**
     * @desc A method to order the legal moves at a current state in the order of their utility values
     * @param state
     * @return a treemap of utility values to gamestates, from lowest to highest
     */
    private TreeMap<Double, GameState> orderMoves(GameState state) {
        TreeMap<Double, GameState> orderedMoves = new TreeMap<>();
        for(Position p : state.legalMoves()){
            GameState tempState = new GameState(state.getBoard(), state.getPlayerInTurn());
            tempState.insertToken(p);
            double value = evaluate(tempState, tempState.getPlayerInTurn());
            orderedMoves.put(value, tempState);
        }
        return orderedMoves;
    }
}
