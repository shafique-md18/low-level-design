package tictactoe.model.strategy;

import tictactoe.model.Player;
import tictactoe.model.Position;

public class DefaultWinningStrategy implements WinningStrategy {
    @Override
    public boolean checkWin(Player[][] board, Position position) {
        int n = board.length;
        Player currentPlayer = board[position.getRow()][position.getCol()];

        // Check row
        boolean rowWin = true;
        for (int col = 0; col < n; col++) {
            if (board[position.getRow()][col] != currentPlayer) {
                rowWin = false;
                break;
            }
        }
        if (rowWin) return true;

        // Check column
        boolean colWin = true;
        for (int row = 0; row < n; row++) {
            if (board[row][position.getCol()] != currentPlayer) {
                colWin = false;
                break;
            }
        }
        if (colWin) return true;

        // Check diagonal if position is on diagonal
        if (position.getRow() == position.getCol()) {
            boolean diagWin = true;
            for (int i = 0; i < n; i++) {
                if (board[i][i] != currentPlayer) {
                    diagWin = false;
                    break;
                }
            }
            if (diagWin) return true;
        }

        // Check anti-diagonal if position is on anti-diagonal
        if (position.getRow() + position.getCol() == n - 1) {
            boolean antiDiagWin = true;
            for (int i = 0; i < n; i++) {
                if (board[i][n-1-i] != currentPlayer) {
                    antiDiagWin = false;
                    break;
                }
            }
            if (antiDiagWin) return true;
        }

        return false;
    }
}
