package tictactoe.service;

import tictactoe.model.Game;
import tictactoe.model.GameNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Singleton to manage the games
public class GameService {
    private Map<String, Game> games;
    private static GameService instance;

    private GameService() {
        games = new HashMap<>();
    }

    public static GameService getInstance() {
        if (instance == null) {
            instance = new GameService();
        }
        return instance;
    }

    public void saveGame(Game game) {
        games.put(game.getId(), game);
    }

    public Game getGame(String gameId) {
        Game game = games.get(gameId);
        if (Objects.isNull(game)) {
            throw new GameNotFoundException("Game not found with id - " + gameId);
        }
        return game;
    }
}
