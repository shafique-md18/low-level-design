package carrentalsystem.model.exception;

public class CarUnavailableException extends RuntimeException {
    public CarUnavailableException(String message) {
        super(message);
    }
}
