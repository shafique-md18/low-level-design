package bookmyshow.model.exceptions;

public class BookingUnholdedSeatException extends RuntimeException {
    public BookingUnholdedSeatException(String message) {
        super(message);
    }
}
