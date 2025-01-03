package tictactoe;

import tictactoe.controller.GameController;
import tictactoe.service.GameService;
import tictactoe.view.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        GameService gameService = GameService.getInstance();
        ConsoleUI consoleUI = new ConsoleUI(new GameController(gameService));
        consoleUI.start();
    }
}
