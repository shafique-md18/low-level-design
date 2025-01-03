package tictactoe.view;

public class InvalidMenuChoice extends RuntimeException {
    public InvalidMenuChoice(String message) {
        super(message);
    }
}
