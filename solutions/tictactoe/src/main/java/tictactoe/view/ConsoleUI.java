package tictactoe.view;

import tictactoe.controller.GameController;
import tictactoe.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI implements GameUI {
    private final GameController gameController;
    private final Scanner scanner;

    public ConsoleUI(GameController gameController) {
        this.gameController = gameController;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void start() {
        displayMainMenu();
        int mainMenuChoice = Integer.parseInt(scanner.nextLine());
        switch (mainMenuChoice) {
            case 1:
                startNewGame();
                break;
            case 2:
                resumeGame();
                break;
            case 3:
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    @Override
    public void displayBoard(Game game) {
        System.out.println("\nCurrent Board:");
        Player[][] board = game.getBoard();
        System.out.println("-------------");
        for (int i = 0; i < board.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(game.getPlayerSymbol(new Position(i, j)));
                }
                System.out.print(" | ");
            }
            System.out.println("\n-------------");
        }
    }

    @Override
    public Position getPlayerMove(Game game) {
        while (true) {
            try {
                int boardSize = game.getN();
                System.out.println("\nPlayer " + game.getCurrentPlayer().getName() +
                        "'s turn (" + game.getCurrentPlayerSymbol() + ")");
                System.out.print("Enter row (0-" + (boardSize - 1) + "): ");
                int row = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter column (0-" + (boardSize - 1) + "): ");
                int col = Integer.parseInt(scanner.nextLine());
                Position position = new Position(row, col);

                if (!game.isValidPosition(position)) {
                    System.out.println("Position must be between 0 and " + (boardSize - 1) + "! Please try again.");
                    continue;
                }

                if (!game.isSlotAvailable(position)) {
                    System.out.println("This position is already taken! Please try again.");
                }

                return new Position(row, col);
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid numbers!");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n=== TIC TAC TOE ===");
        System.out.println("1. Start New Game");
        System.out.println("2. Resume Game");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    private void startNewGame() {
        System.out.println("\n=== New Game ===");
        System.out.println("Enter Player 1 name:");
        String player1Name = scanner.nextLine();
        System.out.println("Enter Player 2 name:");
        String player2Name = scanner.nextLine();
        System.out.println("Enter board size:");
        int boardSize = Integer.parseInt(scanner.nextLine());

        Player player1 = new Player.Builder().withName(player1Name).withActive(true).build();
        Player player2 = new Player.Builder().withName(player2Name).withActive(true).build();
        Map<Player, PlayerSymbol> playerSymbolMap = new HashMap<>();
        // Can get this from user as well
        playerSymbolMap.put(player1, PlayerSymbol.X);
        playerSymbolMap.put(player2, PlayerSymbol.O);
        Game game = gameController.createGame(playerSymbolMap, boardSize);
        System.out.println("\nGame created! Your game ID is: " + game.getId());
        System.out.println("Keep this ID if you want to resume the game later.");

        playGame(game);
    }

    private void resumeGame() {
        System.out.println("\n=== Resume Game ===");
        System.out.println("Enter your game ID:");
        String gameId = scanner.nextLine();

        try {
            Game game = gameController.getGame(gameId);
            System.out.println("Game resumed!");
            playGame(game);
        } catch (GameNotFoundException e) {
            System.out.println("Game not found! Please check your game ID.");
        }
    }

    private void playGame(Game game) {
        while (game.getStatus() == GameStatus.ONGOING) {
            displayBoard(game);
            Position move = getPlayerMove(game);
            try {
                GameStatus status = gameController.makeMove(game, move);
                handleGameStatus(game, status);
            } catch (InvalidMoveException e) {
                System.out.println("\nInvalid move! " + e.getMessage());
            }
        }
    }

    private void handleGameStatus(Game game, GameStatus status) {
        if (status == GameStatus.WIN) {
            displayBoard(game);
            System.out.println("\nCongratulations! Player " +
                    game.getCurrentPlayer().getName() + " wins!");
        } else if (status == GameStatus.DRAW) {
            displayBoard(game);
            System.out.println("\nGame is a draw!");
        }
    }
}
