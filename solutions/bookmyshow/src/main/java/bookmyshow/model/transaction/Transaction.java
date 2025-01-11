package bookmyshow.model.transaction;

import bookmyshow.model.booking.Booking;

public class Transaction {
    private final String id;
    private final Booking booking;
    private final double amount;
    private TransactionStatus status;

    public Transaction(String id, Booking booking, TransactionStatus status, double amount) {
        this.booking = booking;
        this.id = id;
        this.status = status;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public TransactionStatus getStatus() {
        return status;
    }
}
