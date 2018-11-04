public class OthelloAI2e extends OthelloAI2c {
    private int totalTokens;

    @Override
    public double mobility(GameState state, int player) {
        // checks the mobility of each player
        int playerTokens = state.countTokens()[player - 1];
        if (state.getPlayerInTurn() != player) state.changePlayer();
        int mobility = state.legalMoves().size();
        state.changePlayer();
        int opponentMobility = state.legalMoves().size();
        int difference = mobility - opponentMobility;
        int totalMobility = mobility + opponentMobility;
        if(totalMobility != 0) {
            return (difference / totalMobility) * 100;
        } else return difference;
    }

    @Override
    public double stabilityMeasure(GameState state, int player) {
        //an attempt at a stability measure, with need for improvement i.e the checkStability method is not doing the right thing
        // if(1 == 1) return 1;
        int[][] board = state.getBoard();
        int playerTokens = state.countTokens()[player - 1];
        int totalTokens = state.countTokens()[0] + state.countTokens()[1];
        int opponent = 0;
        if (state.getPlayerInTurn() != player) {
            state.changePlayer();
            opponent = state.getPlayerInTurn();
        } else {
            opponent = state.getPlayerInTurn();
        }
        int playerStability = 0;
        int opponentStability = 0;
        for (int i = 0; i < board.length - 1; i++) {
            for (int j = 0; j < board.length - 1; j++) {
                if (board[i][j] == player - 1) playerStability++;
                else if (board[i][j] != 0) opponentStability++;
                if (i == 0 || i == board.length - 1 && j == 0 || j == board.length - 1) {
                    if (board[i][j] == player - 1) playerStability++;
                    else if (board[i][j] != 0) opponentStability++;
                } else if (i == 0 || i == board.length - 1 && j == 0 || j == board.length - 1) {
                    if (board[i][j] == player - 1) playerStability++;
                    else if (board[i][j] != 0) opponentStability++;
                }
            }
        }
        return (4 * playerStability * (playerTokens / totalTokens)) - (4 * opponentStability * ((totalTokens - playerTokens) / totalTokens));

    }

    @Override
    public double checkChange(GameState state, int player) {
        return 0;
    }

}