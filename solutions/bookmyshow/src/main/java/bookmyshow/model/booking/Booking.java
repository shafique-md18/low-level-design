package bookmyshow.model.booking;

import bookmyshow.model.User;
import bookmyshow.model.seat.Seat;
import bookmyshow.model.theatre.Show;

import java.util.List;

public class Booking {
    private final String id;
    private final double totalPrice;
    private final User user;
    private final Show show;
    private final List<Seat> seats;
    private BookingStatus status;

    public Booking(String id, double totalPrice, User user, Show show, List<Seat> seats, BookingStatus status) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.status = status;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public Show getShow() {
        return show;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public User getUser() {
        return user;
    }

    public String getId() {
        return id;
    }
}
