public class OthelloAI2a extends OthelloAI2 {

    @Override
    public double evaluate(GameState state, int player){
        int[][] board = state.getBoard();
        int corner = 0;
        int oppCorner = 0;
        int edge = 0;
        int oppEdge = 0;
        for (int i = 0; i < board.length; i++) {
            //j = column
            for (int j = 0; j < board.length; j++) {
                if ((i == 0 || i == board.length - 1) && (j == 0 || j == board.length - 1)) {
                    if (board[i][j] == player) corner++;
                    else if(board[i][j] != 0) oppCorner++;
                }
                if((i == 0 || i == board.length - 1) && (j == 0 || j == board.length - 1)){
                    if(board[i][j] == player) edge++;
                    else if(board[i][j] != 0) oppEdge++;
                }
            }
        }
        double cornerRat = 0;
        double edgeRat = 0;
        if(oppCorner != 0) { cornerRat = 1600 * (corner / oppCorner); }
        if(oppEdge != 0 ) { edgeRat = 100 * (edge / oppEdge); }
        int[] tokens = state.countTokens();
        int playTokens = tokens[player - 1];
        int totalTokens = tokens[0] + tokens[1];
        int oppTokens = totalTokens - playTokens;
        double tokenRat = 500 * (playTokens - oppTokens) / totalTokens;
        return cornerRat + edgeRat + tokenRat;

    }
}
