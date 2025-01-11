package bookmyshow.model.exceptions;

public class UnavailableSeatException extends RuntimeException {
    public UnavailableSeatException(String message) {
        super(message);
    }
}
