package tictactoe.model;

import tictactoe.model.strategy.DefaultWinningStrategy;
import tictactoe.model.strategy.WinningStrategy;

import java.util.*;

public class Game {
    private String id;
    private GameStatus status;
    private Player[][] board;
    private Player currentPlayer;
    private Player winner;
    private int N;
    private int slotsFilled;
    private Deque<Player> players;
    private Map<Player, PlayerSymbol> playerSymbolMap;
    private WinningStrategy winningStrategy;

    private Game(Builder builder) {
        this.slotsFilled = builder.slotsFilled;
        this.board = builder.board;
        this.currentPlayer = builder.currentPlayer;
        this.id = builder.id;
        this.N = builder.N;
        this.players = builder.players;
        this.playerSymbolMap = builder.playerSymbolMap;
        this.status = builder.status;
        this.winner = builder.winner;
        this.winningStrategy = builder.winningStrategy;
    }

    public GameStatus getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public Player[][] getBoard() {
        return board;
    }

    public int getN() {
        return N;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public PlayerSymbol getPlayerSymbol(Position position) {
        return playerSymbolMap.get(getPlayer(position));
    }

    public PlayerSymbol getCurrentPlayerSymbol() {
        return playerSymbolMap.get(currentPlayer);
    }

    public Player getPlayer(Position position) {
        return board[position.getRow()][position.getCol()];
    }

    public GameStatus makeMove(Position position) {
        if (!GameStatus.ONGOING.equals(status)) {
            throw new GameAlreadyOverException("Game is already completed");
        }

        if (!isValidPosition(position)) {
            // TODO: Might need a different exception so that UI can catch it
            //  this way we would not need to add validations in UI itself
            throw new InvalidMoveException("Position is not valid");
        }

        if (!isSlotAvailable(position)) {
            throw new InvalidMoveException("This position is already taken");
        }

        board[position.getRow()][position.getCol()] = currentPlayer;

        if (winningStrategy.checkWin(board, position)) {
            status = GameStatus.WIN;
        } else if (isDraw()) {
            status = GameStatus.DRAW;
        } else {
            players.addLast(currentPlayer);
            currentPlayer = players.removeFirst();
            slotsFilled++;
        }

        return status;
    }

    private boolean isDraw() {
        return slotsFilled >= N * N;
    }

    public boolean isValidPosition(Position position) {
        return position.getRow() >= 0 && position.getCol() >= 0 && position.getRow() < N && position.getCol() < N;
    }

    public boolean isSlotAvailable(Position position) {
        return getPlayer(position) == null;
    }

    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private GameStatus status = GameStatus.ONGOING;
        private Player[][] board;
        private Player currentPlayer;
        private Player winner;
        private int N;
        private int slotsFilled;
        private Deque<Player> players = new ArrayDeque<>();
        Map<Player, PlayerSymbol> playerSymbolMap = new HashMap<>();
        private WinningStrategy winningStrategy = new DefaultWinningStrategy();

        public Builder withBoard(Player[][] board) {
            this.board = board;
            return this;
        }

        public Builder withCurrentPlayer(Player currentPlayer) {
            this.currentPlayer = currentPlayer;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withN(int n) {
            N = n;
            return this;
        }

        public Builder withSlotsFilled(int slotsFilled) {
            this.slotsFilled = slotsFilled;
            return this;
        }

        public Builder withPlayers(Deque<Player> players) {
            this.players = players;
            return this;
        }

        public Builder withPlayerSymbolMap(Map<Player, PlayerSymbol> playerSymbolMap) {
            this.playerSymbolMap = playerSymbolMap;
            return this;
        }

        public Builder withStatus(GameStatus status) {
            this.status = status;
            return this;
        }

        public Builder withWinner(Player winner) {
            this.winner = winner;
            return this;
        }

        public Builder withWinningStrategy(WinningStrategy winningStrategy) {
            this.winningStrategy = winningStrategy;
            return this;
        }

        private void validate() {
            if (N <= 0) throw new InvalidGameConfigurationException("Invalid board size");
            if (board == null) board = new Player[N][N];
            if (players == null || players.size() != 2) throw new InvalidGameConfigurationException("Exactly 2 Players required");
            if (playerSymbolMap == null || playerSymbolMap.isEmpty()) throw new InvalidGameConfigurationException("Player symbols required");
            if (currentPlayer == null) currentPlayer = players.removeFirst();
        }

        public Game build() {
            validate();
            return new Game(this);
        }
    }
}
