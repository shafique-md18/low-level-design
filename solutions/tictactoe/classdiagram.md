## TicTacToe Class Diagram

```mermaid
classDiagram
    Game *-- GameStatus
    Game o-- Player
    Game *-- WinningStrategy
    class GameStatus {
        <<enumeration>>
        ONGOING
        WIN
        DRAW
    }
    class Game {
        -id : String
        -board : Player[][]
        -winner : Player
        -status : GameStatus
        -startTime : LocalDateTime
        -endTime : LocalDateTime
        -currentPlayer : Player
        -players : Queue<Player>
        -playerSymbolMap : Map<Player, PlayerSymbolMap>
        -winningStrategy : WinningStrategy
        -boardSize : int
        -slotsFilled : int
        +makeMove(Position position) : GameStatus
        -isDraw()
        -isValidPosition(Position position)
        -isSlotAvailable(Position position)
    }
    class Player {
        
    }
    class PlayerSymbol {
        <<enumeration>>
        X
        O
    }
    class Position {
        -row : int
        -col : int
    }
    WinningStrategy <|-- DefaultWinningStrategy
    class WinningStrategy {
        <<interface>>
        +checkWin(Player[][] board, Position position) : GameStatus
    }
    class DefaultWinningStrategy {
        
    }
    GameController o-- GameService
    class GameController {
        -gameService GameService
        +createGame(Map<Player, PlayerSymbol> playerSymbolMap, int boardSize) : Game
        +makeMove(Game game, Position position) : GameStatus
        +saveGame(Game game)
        +getGame(String gameId) // helper to fetch the saved game
        
    }
    GameUI <|-- ConsoleUI
    ConsoleUI o-- GameController
    class GameUI {
        <<interface>>
        start()
        displayBoard(Game game)
        getPlayerMove(Game game) : Position
    }
    class ConsoleUI {
        
    }
    GameService o-- Game
    class GameService { // singleton
        -games : Map<String, Game>
        -instance : GameService
    }
    class GameNotFoundException {
        
    }
    class GameAlreadyOverException {
        
    }
    class InvalidGameConfigurationException {
        
    }
    class InvalidMoveException {
        
    }
```

Other considerations:
1. Exception Handling
2. MoveHistory and Undo Support
```java
class Game {
    private Stack<Move> moveHistory;
    
    class Move {
        private Position position;
        private Player player;
        private LocalDateTime timestamp;
    }
    
    public boolean undoLastMove() {
        if (moveHistory.isEmpty()) return false;
        Move lastMove = moveHistory.pop();
        board[lastMove.position.getRow()][lastMove.position.getCol()] = null;
        currentPlayer = lastMove.player;
        slotsFilled--;
        return true;
    }
}
```
3. Observable Pattern for Game State Changes
```java
interface GameStateObserver {
    void onMoveMade(Move move);
    void onGameEnd(GameStatus status, Player winner);
}

class Game {
    private List<GameStateObserver> observers = new ArrayList<>();
    
    public void addObserver(GameStateObserver observer) {
        observers.add(observer);
    }
    
    private void notifyMoveMade(Move move) {
        observers.forEach(o -> o.onMoveMade(move));
    }
}
```
4. Input validation layer
5. Metrics collection (moves per game, win rates, etc.)
6. Game Save and Resume using Momento design pattern
