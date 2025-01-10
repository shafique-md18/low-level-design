package vendingmachine.model.exception;

public class InvalidItemProperty extends RuntimeException {
    public InvalidItemProperty(String message) {
        super(message);
    }
}
