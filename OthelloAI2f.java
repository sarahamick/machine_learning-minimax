import java.util.HashMap;
import java.util.Map;

public class OthelloAI2f extends OthelloAI2 {
    private Map<GameState, Double> previousPlayerStates = new HashMap<>();
    private Map<GameState, Double> previousOpponentStates = new HashMap<>();

    @Override
    public double evaluate(GameState state, int player) {

        // make sure the state evaluates according to the max player
        if (state.getPlayerInTurn() == player && previousPlayerStates.containsKey(state)) {
            return previousPlayerStates.get(state);
        } else if (previousOpponentStates.containsKey(state)) return previousOpponentStates.get(state);

        //check the score of the state
        GameState cloneState = new GameState(state.getBoard(), state.getPlayerInTurn());
        double utility = 0;
        int totalTokens = cloneState.countTokens()[0] + cloneState.countTokens()[1];
        int playerTokens = cloneState.countTokens()[player - 1];
        int opponentTokens = totalTokens - playerTokens;
        utility += (100 * (playerTokens - opponentTokens)) / totalTokens;

        // check the mobility aka number of legal moves for each player
        int playerMobility = cloneState.legalMoves().size();
        cloneState.changePlayer();
        int opponentMobility = cloneState.legalMoves().size();
        cloneState.changePlayer();
        if (playerMobility + opponentMobility != 0 && playerMobility - opponentMobility != 0) {
            double diffMobil = playerMobility - opponentMobility;
            double totalMobil = playerMobility + opponentMobility;
            double ratioMobil = diffMobil / totalMobil;
           utility += 100 * ratioMobil;
         }

         // check the stability of the current state
        int[][] board = cloneState.getBoard();
        utility += countCorners(cloneState, player);

        double playerStability = getStability(cloneState, player);
        cloneState.changePlayer();
        double opponentStability = getStability(cloneState, player);
        cloneState.changePlayer();

        if (playerStability + opponentStability != 0) {
            utility += (100 * (playerStability - opponentStability)) / (playerStability + opponentStability);
        }
        if (state.getPlayerInTurn() == player) previousPlayerStates.put(state, utility);
        else previousOpponentStates.put(state, utility);

        previousOpponentStates.clear();
        previousPlayerStates.clear();
        return utility;

    }

    private double getStability(GameState cloneState, int player) {

        int[][] board = cloneState.getBoard();
        int playerInTurn = cloneState.getPlayerInTurn();
        int playerTokens = cloneState.countTokens()[player - 1];
        int captureTokens = 0;

        // making sure evaluation of stability is aimed at the right player
        if(playerInTurn == player) cloneState.changePlayer();
        int numberOfOpponentMoves = cloneState.legalMoves().size();

        //check the score after the next potential moves to see the average change
        for(Position move : cloneState.legalMoves()){
            GameState tempState = new GameState(board, cloneState.getPlayerInTurn());
            tempState.insertToken(move);
            captureTokens += (tempState.countTokens()[player - 1] - playerTokens);
        }
        if(cloneState.getPlayerInTurn() != playerInTurn) cloneState.changePlayer();
        if(numberOfOpponentMoves != 0) {
            double change = captureTokens / numberOfOpponentMoves;
            return change;
        } else return 0;
    }

    private double countCorners(GameState cloneState, int player){
        int[] tokens = cloneState.countTokens();
        int totalTokens = tokens[0] + tokens[1];
        int[][] board = cloneState.getBoard();
        int trueCorner = 0;
        int opponentTrueCorner = 0;

        //counts the occupied corners
        // i = row
        for (int i = 0; i < board.length; i++) {
            //j = column
            for (int j = 0; j < board.length; j++) {
                if ((i == 0 || i == board.length - 1) && (j == 0 || j == board.length - 1)) {
                    if (board[i][j] == player) trueCorner++;
                    else if(board[i][j] != 0) opponentTrueCorner++;
                }
            }
        }

        if(trueCorner + opponentTrueCorner != 0){
            double cornerDiff = trueCorner - opponentTrueCorner;
            double cornerSum = trueCorner + opponentTrueCorner;
            double cornerRatio = cornerDiff / cornerSum;
            return 100 * cornerRatio;
        } else return 0;
    }
}




