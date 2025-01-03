package tictactoe.view;

import tictactoe.model.Game;
import tictactoe.model.Position;

public interface GameUI {
    void start();
    void displayBoard(Game game);
    Position getPlayerMove(Game game);
}
