package bookmyshow.service;

import bookmyshow.model.Movie;
import bookmyshow.model.User;
import bookmyshow.model.booking.Booking;
import bookmyshow.model.booking.BookingStatus;
import bookmyshow.model.exceptions.InvalidSeatSelection;
import bookmyshow.model.exceptions.UnavailableSeatException;
import bookmyshow.model.seat.Seat;
import bookmyshow.model.seat.SeatStatus;
import bookmyshow.model.theatre.Show;
import bookmyshow.model.theatre.Theatre;
import bookmyshow.model.transaction.Transaction;
import bookmyshow.model.transaction.TransactionStatus;

import java.util.*;

public class BookingService {
    private final TransactionService transactionService;
    private final Map<String, Theatre> theatres;
    private final Map<String, Movie> movies;
    private final Map<String, Booking> bookings;


    public BookingService(TransactionService transactionService, Map<String, Theatre> theatres, Map<String, Movie> movies, Map<String, Booking> bookings) {
        this.transactionService = transactionService;
        this.theatres = theatres;
        this.movies = movies;
        this.bookings = bookings;
    }

    public void addMovie(Movie movie) {
        this.movies.put(movie.getId(), movie);
    }

    public void addTheatre(Theatre theatre) {
        this.theatres.put(theatre.getId(), theatre);
    }

    public Map<Seat, SeatStatus> getSeatInformationForShow(Show show) {
        Map<Seat, SeatStatus> seatInformation = new HashMap<>();
        for (Seat seat : show.getScreen().getSeats().values()) {
            seatInformation.put(seat, show.getSeatStatusMap().get(seat.getId()));
        }
        return seatInformation;
    }

    public void bookTickets(User user, Show show, List<Seat> seats) {
        if (seats.isEmpty()) {
            throw new InvalidSeatSelection("Cannot book empty seats");
        }

        validateSeatsAvailability(show, seats);

        Booking booking = createBooking(user, show, seats);

        seats.forEach(show::holdSeat);

        Transaction transaction = transactionService.initiateTransaction(booking);
        processTransaction(transaction, show, seats, booking);

        bookings.put(booking.getId(), booking);
    }

    private double calculateTotalPrice(List<Seat> selectedSeats) {
        return selectedSeats.stream().mapToDouble(Seat::getPrice).sum();
    }

    private void validateSeatsAvailability(Show show, List<Seat> seats) {
        for (Seat seat : seats) {
            if (!show.isSeatAvailable(seat)) {
                throw new UnavailableSeatException("One or more of selected seats are unavailable");
            }
        }
    }

    private Booking createBooking(User user, Show show, List<Seat> seats) {
        return new Booking(UUID.randomUUID().toString(), calculateTotalPrice(seats), user,
                show, seats, BookingStatus.PENDING);
    }

    private void processTransaction(Transaction transaction, Show show, List<Seat> seats, Booking booking) {
        if (transaction.getStatus().equals(TransactionStatus.SUCCESSFUL)) {
            seats.forEach(show::bookSeat);
            booking.setStatus(BookingStatus.CONFIRMED);
        } else {
            booking.setStatus(BookingStatus.PAYMENT_FAILED);
        }
    }
}
