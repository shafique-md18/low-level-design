package bookmyshow.model.exceptions;

public class InvalidSeatSelection extends RuntimeException {
    public InvalidSeatSelection(String message) {
        super(message);
    }
}
