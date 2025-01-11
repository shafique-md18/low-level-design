package bookmyshow.model.seat;

public class Seat {
    private final String id;
    private final String seatCode;
    private final SeatType type;
    // TODO: Can be moved to Show if you want to have separate pricing based on the show
    private double price;

    public Seat(String id, String seatCode, SeatType type, SeatStatus status, double price) {
        this.id = id;
        this.seatCode = seatCode;
        this.type = type;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }
}
