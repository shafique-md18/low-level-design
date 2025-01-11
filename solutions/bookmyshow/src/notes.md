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

#### Initial Simpler version
```mermaid
classDiagram
    class BookingService {
        +bookTickets(User, Show, List~Seat~)
        +getSeatInformation(Show)
    }

    class Show {
        -String id
        -Movie movie
        -Screen screen
        -DateTime startTime
        -Map~String, SeatStatus~ seatStatusMap
        +bookSeat(Seat)
        +isSeatAvailable(Seat)
    }

    class Theatre {
        -String id
        -List~Screen~ screens
        -List~Show~ shows
    }

    class Screen {
        -String id
        -Map~String, Seat~ seats
    }

    class Booking {
        -String id
        -User user
        -Show show
        -List~Seat~ seats
        -BookingStatus status
    }

    class Seat {
        -String id
        -SeatType type
        -double price
    }

    class BookingStatus {
        <<enumeration>>
        CONFIRMED
        FAILED
    }

    class SeatStatus {
        <<enumeration>>
        AVAILABLE
        BOOKED
    }

    BookingService --> Theatre
    Theatre --> Screen
    Theatre --> Show
    Show --> Screen
    Show --> SeatStatus
    Booking --> Show
    Booking --> BookingStatus
    Screen --> Seat
```

#### Comprehensive design
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

1. Start Simple (First 5 minutes)
    * Draw just 4-5 core classes on the whiteboard
    * Basic models: Theatre, Movie, Show, Seat, Booking
    * One main service: BookingService
    * Ask interviewer: "I'll start with this basic design, and we can expand based on requirements"
2. Core Flow First (Next 30 minutes)
    * Implement basic seat booking first
    * Skip complex validations initially
    * Use simple collections (HashMap)
    * Basic error handling (just throw exceptions)
    * No concurrent handling yet
3. Minimum Viable Enums
    * Start with just 2 states (SUCCESS/FAILURE)
    * Don't create enums for every possible state
    * Example: Just AVAILABLE/BOOKED for seats
4. Follow-ups (Remaining time)
    * Wait for interviewer to ask about concurrency
    * Then add synchronized/locks where needed
    * Discuss potential improvements
    * Talk about what you would add in production
5. What to Skip Initially
    * Payment processing
    * Seat hold mechanism
    * User management
    * Complex state machines
    * Caching
    * Distributed systems concerns
6. Red Flags That You're Over-engineering
    * If you have more than 6-7 classes initially
    * If you're adding features without interviewer asking
    * If you're thinking about scalability before basic flow
    * If you're adding multiple services right away
7. Remember:
   * Get something working first
   * Add complexity only when asked
   * It's better to have a simple working solution than a complex incomplete one
   * You can always mention "In production, I would add..."
