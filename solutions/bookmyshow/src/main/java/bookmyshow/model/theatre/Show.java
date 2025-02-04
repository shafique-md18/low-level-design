package bookmyshow.model.theatre;

import bookmyshow.model.Movie;
import bookmyshow.model.exceptions.BookingUnholdedSeatException;
import bookmyshow.model.exceptions.UnavailableSeatException;
import bookmyshow.model.seat.Seat;
import bookmyshow.model.seat.SeatStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Show {
    private final String id;
    private final Movie movie;
    private final Screen screen;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    // Use ConcurrentHashMap
    private final Map<String, SeatStatus> seatStatusMap;
    private final Map<String, Instant> seatOnHoldExpiryMap;
    private final static long MINIMUM_TIME_TO_HOLD_SEAT_IN_SECONDS = 10 * 60;

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

    public synchronized boolean tryHoldSeats(List<Seat> seats) {
        // First check if all seats are available
        for (Seat seat : seats) {
            if (!isSeatAvailable(seat)) {
                return false;
            }
        }

        // If all available, hold them atomically
        for (Seat seat : seats) {
            seatStatusMap.put(seat.getId(), SeatStatus.ON_HOLD);
            seatOnHoldExpiryMap.put(seat.getId(),
                    Instant.now().plusSeconds(MINIMUM_TIME_TO_HOLD_SEAT_IN_SECONDS));
        }
        return true;
    }

    public synchronized void releaseSeats(List<Seat> seats) {
        for (Seat seat : seats) {
            // We are not tracking who held the lock for on_hold before releasing,
            // it is possible that we might release the seat holds for some other booking
            if (seatStatusMap.get(seat.getId()).equals(SeatStatus.ON_HOLD)) {
                seatStatusMap.put(seat.getId(), SeatStatus.AVAILABLE);
                seatOnHoldExpiryMap.remove(seat.getId());
            }
        }
    }

    public synchronized  void bookSeats(List<Seat> seats) {
        for (Seat seat : seats) {
            SeatStatus status = seatStatusMap.get(seat.getId());
            if (!SeatStatus.ON_HOLD.equals(status)) {
                throw new BookingUnholdedSeatException("Seat " + seat.getId() + " is not on hold");
            }
        }

        // Book all seats atomically
        for (Seat seat : seats) {
            seatStatusMap.put(seat.getId(), SeatStatus.BOOKED);
            seatOnHoldExpiryMap.remove(seat.getId());
        }
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
