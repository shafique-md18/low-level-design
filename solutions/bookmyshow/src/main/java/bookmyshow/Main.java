package bookmyshow;

import bookmyshow.model.City;
import bookmyshow.model.Language;
import bookmyshow.model.Movie;
import bookmyshow.model.User;
import bookmyshow.model.booking.Booking;
import bookmyshow.model.seat.Seat;
import bookmyshow.model.seat.SeatStatus;
import bookmyshow.model.seat.SeatType;
import bookmyshow.model.theatre.Screen;
import bookmyshow.model.theatre.Show;
import bookmyshow.model.theatre.Theatre;
import bookmyshow.service.BookingService;
import bookmyshow.service.PaymentService;
import bookmyshow.service.TransactionService;

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Initialize Services
        PaymentService paymentService = new PaymentService();
        TransactionService transactionService = new TransactionService(paymentService);
        BookingService bookingService = new BookingService(
                transactionService,
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>()
        );

        // Create Movie
        Movie avengers = new Movie(
                "movie1",
                "Avengers: Endgame",
                Language.ENGLISH,
                180
        );
        bookingService.addMovie(avengers);

        // Create Seats
        Map<String, Seat> seats = new HashMap<>();
        seats.put("A1", new Seat("A1", "A1", SeatType.DIAMOND, SeatStatus.AVAILABLE, 300.0));
        seats.put("A2", new Seat("A2", "A2", SeatType.DIAMOND, SeatStatus.AVAILABLE, 300.0));

        // Create Screen
        Screen screen = new Screen("screen1", "Screen 1", seats);

        // Create Show
        LocalDateTime startTime = LocalDateTime.now().plusDays(1).withHour(18).withMinute(0);
        LocalDateTime endTime = startTime.plusMinutes(180);

        Map<String, SeatStatus> seatStatusMap = new HashMap<>();
        seats.keySet().forEach(seatId -> seatStatusMap.put(seatId, SeatStatus.AVAILABLE));
        Show show = new Show(
                "show1",
                avengers,
                screen,
                startTime,
                endTime,
                seatStatusMap,  // seatStatusMap
                new HashMap<>()   // seatHoldMap
        );

        // Create Theatre
        Map<String, Screen> screens = new HashMap<>();
        screens.put(screen.getId(), screen);

        Map<String, Show> shows = new HashMap<>();
        shows.put(show.getId(), show);

        Theatre theatre = new Theatre("theatre1", screens, City.MUMBAI, shows);
        bookingService.addTheatre(theatre);

        // Create stockexchange.User
        User user = new User("john@example.com", "user1", "John Doe");

        try {
            // Check seat availability
            Map<Seat, SeatStatus> seatInfo = bookingService.getSeatInformationForShow(show);
            System.out.println("Available seats: " + seatInfo.size());

            // Book tickets
            List<Seat> seatsToBook = new ArrayList<>();
            seatsToBook.add(seats.get("A1"));

            System.out.println("Booking ticket for: " + user.getName());
            bookingService.bookTickets(user, show, seatsToBook);
            System.out.println("Booking successful!");

        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    private static Map<String, Seat> createSeats() {
        Map<String, Seat> result = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            Seat seat = createSeat("S_" + i, SeatType.DIAMOND, 1000);
            result.put(seat.getId(), seat);
        }
        for (int i = 20; i < 60; i++) {
            Seat seat = createSeat("S_" + i, SeatType.GOLD, 800);
            result.put(seat.getId(), seat);
        }
        for (int i = 60; i < 100; i++) {
            Seat seat = createSeat("S_" + i, SeatType.SILVER, 400);
            result.put(seat.getId(), seat);
        }
        return result;
    }

    private static Seat createSeat(String code, SeatType type, double price) {
        return new Seat(UUID.randomUUID().toString(), code, type, SeatStatus.AVAILABLE, price);
    }
}
