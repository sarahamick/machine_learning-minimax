import java.util.HashMap;
import java.util.Map;

public class OthelloAI2d extends OthelloAI2c {
    @Override
    public double mobility(GameState state, int player) {
        GameState tempState = new GameState(state.getBoard(), state.getPlayerInTurn());
        if (tempState.getPlayerInTurn() != player) tempState.changePlayer();
        double mobility = tempState.legalMoves().size();
        tempState.changePlayer();
        double opponentMobility = tempState.legalMoves().size();
        double diffMobil = mobility - opponentMobility;
        if(mobility + opponentMobility != 0) {
            double ratioMobil = diffMobil / (mobility + opponentMobility);
            return ratioMobil * 100;
        } else return diffMobil * 10;
    }

    @Override
    public double stabilityMeasure(GameState state, int player) {
        int[] tokens = state.countTokens();

        int[][] board = state.getBoard();

        int corners = 0;
        int edges = 0;
        int oppCorners = 0;
        int oppEdges = 0;

        //i = row
        for (int i = 0; i < board.length; i++) {
            //j = column
            for (int j = 0; j < board.length; j++) {
                if (i == 0 || (i == board.length - 1) && j == 0 || j == board.length - 1) {
                    if (board[i][j] == player) corners++;
                    else if (board[i][j] != 0) oppCorners++;
                } else if (i == 0 || (i == board.length - 1) || j == 0 || j == (board.length - 1)) {
                    if (board[i][j] == player) edges++;
                    else if (board[i][j] != 0) oppEdges++;
                }
            }
        }
        int diffStabil = 16 * (corners - oppCorners) + (edges - oppEdges);
        int sumStabil = 16 * (corners + oppCorners) + edges + oppEdges;
        if(sumStabil != 0) {
            double ratioStabil = diffStabil / sumStabil;
            return ratioStabil * 100;
        } else return diffStabil;
    }

        @Override
        public double checkChange (GameState state, int player){
            int playerTokens = state.countTokens()[player - 1];
            int sumOfChange = 0;
            for(Position p : state.legalMoves()){
                GameState tempState = new GameState(state.getBoard(), state.getPlayerInTurn());
                tempState.insertToken(p);
                sumOfChange += tempState.countTokens()[player - 1];
            }
            if(sumOfChange != 0) {
                double avgChange = sumOfChange / state.legalMoves().size();
                return avgChange * 100;
            } else return 1;
        }
    }

