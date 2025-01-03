package tictactoe.model.strategy;

import tictactoe.model.Player;
import tictactoe.model.Position;

public interface WinningStrategy {
    public boolean checkWin(Player[][] board, Position position);
}
