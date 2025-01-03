package tictactoe.controller;

import tictactoe.model.*;
import tictactoe.service.GameService;

import java.util.ArrayDeque;
import java.util.Map;

public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    public Game createGame(Map<Player, PlayerSymbol> playerSymbolMap, int boardSize) {
        Game game = new Game.Builder()
                .withBoard(new Player[boardSize][boardSize])
                .withN(boardSize)
                .withPlayerSymbolMap(playerSymbolMap)
                .withPlayers(new ArrayDeque<>(playerSymbolMap.keySet()))
                .build();
        gameService.saveGame(game);
        return game;
    }

    public GameStatus makeMove(Game game, Position position) {
        return game.makeMove(position);
    }

    public void saveGame(Game game) {
        gameService.saveGame(game);
    }

    public Game getGame(String gameId) {
        return gameService.getGame(gameId);
    }
}
