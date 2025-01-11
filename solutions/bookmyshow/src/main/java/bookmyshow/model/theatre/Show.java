package bookmyshow.model.theatre;

import bookmyshow.model.Movie;
import bookmyshow.model.exceptions.BookingUnholdedSeatException;
import bookmyshow.model.exceptions.UnavailableSeatException;
import bookmyshow.model.seat.Seat;
import bookmyshow.model.seat.SeatStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;

public class Show {
    private final String id;
    private final Movie movie;
    private final Screen screen;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Map<String, SeatStatus> seatStatusMap;
    private final Map<String, Instant> seatOnHoldExpiryMap;
    private final long MINIMUM_TIME_TO_HOLD_SEAT_IN_SECONDS = 10 * 60;

    public Show(String id, Movie movie, Screen screen, LocalDateTime startTime, LocalDateTime endTime,
                Map<String, SeatStatus> seatStatusMap, Map<String, Instant> seatOnHoldExpiryMap) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seatStatusMap = seatStatusMap;
        this.seatOnHoldExpiryMap = seatOnHoldExpiryMap;
    }

    public void holdSeat(Seat seat) {
        if (!isSeatAvailable(seat)) {
            throw new UnavailableSeatException("Cannot hold unavailable seat");
        }
        seatStatusMap.put(seat.getId(), SeatStatus.ON_HOLD);
        seatOnHoldExpiryMap.put(seat.getId(), Instant.now().plusSeconds(MINIMUM_TIME_TO_HOLD_SEAT_IN_SECONDS));
    }

    public void bookSeat(Seat seat) {
        SeatStatus status = seatStatusMap.get(seat.getId());
        if (!SeatStatus.ON_HOLD.equals(status)) {
            throw new BookingUnholdedSeatException("Cannot book a seat which has not been holded");
        }
        seatStatusMap.put(seat.getId(), SeatStatus.BOOKED);
    }

    public boolean isSeatAvailable(Seat seat) {
        SeatStatus status = seatStatusMap.get(seat.getId());

        if (status == null) {
            return false;
        }

        if (SeatStatus.AVAILABLE.equals(status)) {
            return true;
        }

        if (SeatStatus.ON_HOLD.equals(status)) {
            Instant expiryInstant = seatOnHoldExpiryMap.get(seat.getId());
            return expiryInstant != null && expiryInstant.isBefore(Instant.now());
        }

        return false;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Map<String, SeatStatus> getSeatStatusMap() {
        return seatStatusMap;
    }
}
