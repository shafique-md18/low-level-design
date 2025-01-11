## BookMyShow

### Requirements

1. The system should allow users to view the list of movies playing in different theaters.
2. Users should be able to select a movie, theater, and show timing to book tickets.
3. The system should display the seating arrangement of the selected show and allow users to choose seats.
4. Users should be able to make payments and confirm their booking.
5. The system should handle concurrent bookings and ensure seat availability is updated in real-time.
6. The system should support different types of seats (e.g., normal, premium) and pricing.
7. The system should allow theater administrators to add, update, and remove movies, shows, and seating arrangements.
8. The system should be scalable to handle a large number of concurrent users and bookings.

### Class Diagram

```mermaid
classDiagram
    class BookingService {
        -TransactionService transactionService
        -Map~String, Theatre~ theatres
        -Map~String, Movie~ movies
        -Map~String, Booking~ bookings
        +bookTickets(User, Show, List~Seat~)
        +getSeatInformation(Show)
        +addMovie(Movie)
        +addTheatre(Theatre)
    }

    class TransactionService {
        -PaymentService paymentService
        -Map~String, Transaction~ transactions
        -double TRANSACTION_FEE
        +initiateTransaction(Booking)
    }

    class PaymentService {
        +pay() boolean
    }

    class Show {
        -String id
        -Movie movie
        -Screen screen
        -LocalDateTime startTime
        -LocalDateTime endTime
        -Map~String, SeatStatus~ seatStatusMap
        -Map~String, Instant~ seatOnHoldExpiryMap
        +holdSeat(Seat)
        +bookSeat(Seat)
        +isSeatAvailable(Seat)
    }

    class Theatre {
        -String id
        -Map~String, Screen~ screens
        -City city
        -Map~String, Show~ shows
        +addShow(Show)
    }

    class Screen {
        -String id
        -String name
        -Map~String, Seat~ seats
    }

    class Booking {
        -String id
        -double totalPrice
        -User user
        -Show show
        -List~Seat~ seats
        -BookingStatus status
    }

    class Seat {
        -String id
        -String seatCode
        -SeatType type
        -double price
    }

    class Transaction {
        -String id
        -Booking booking
        -double amount
        -TransactionStatus status
    }

    class User {
        -String id
        -String name
        -String email
    }

    class Movie {
        -String id
        -String name
        -Language language
        -long durationInMinutes
    }

    %% Enums
    class BookingStatus {
        <<enumeration>>
        PENDING
        CONFIRMED
        PAYMENT_FAILED
    }

    class TransactionStatus {
        <<enumeration>>
        PENDING
        SUCCESSFUL
        FAILED
    }

    class SeatStatus {
        <<enumeration>>
        AVAILABLE
        BOOKED
        ON_HOLD
    }

    class SeatType {
        <<enumeration>>
        DIAMOND
        GOLD
        SILVER
    }

    class Language {
        <<enumeration>>
        HINDI
        ENGLISH
        MARATHI
    }

    class City {
        <<enumeration>>
        BENGALURU
        HYDERABAD
        MUMBAI
        PUNE
    }

    %% Relationships
    BookingService --> TransactionService
    BookingService --> Theatre
    BookingService --> Movie
    BookingService --> Booking
    TransactionService --> PaymentService
    TransactionService --> Transaction
    Theatre --> Screen
    Theatre --> Show
    Theatre --> City
    Show --> Screen
    Show --> Movie
    Show --> SeatStatus
    Booking --> Show
    Booking --> User
    Booking --> Seat
    Booking --> BookingStatus
    Transaction --> Booking
    Transaction --> TransactionStatus
    Screen --> Seat
    Seat --> SeatType
    Movie --> Language
```

### Other considerations