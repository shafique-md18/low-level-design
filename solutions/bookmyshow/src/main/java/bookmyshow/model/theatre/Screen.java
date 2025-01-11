package bookmyshow.model.theatre;

import bookmyshow.model.exceptions.UnavailableSeatException;
import bookmyshow.model.seat.Seat;

import java.util.Map;

public class Screen {
    private final String id;
    private final String name;
    private final Map<String, Seat> seats;

    public Screen(String id, String name, Map<String, Seat> seats) {
        this.id = id;
        this.name = name;
        this.seats = seats;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, Seat> getSeats() {
        return seats;
    }
}
