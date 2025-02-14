```java
enum CarType {
    ECONOMY, SEDAN, LUXURY, SUV
}

enum CarStatus {
    AVAILABLE, RESERVED, RENTED, MAINTENANCE
}

class Car {
    private String id;
    private CarType type;
    private String model;
    private CarStatus status;
    private double hourlyRate;
    
    // Constructor, getters, setters
}

class stockexchange.User {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    
    // Constructor, getters, setters
}

class Reservation {
    private String id;
    private Car car;
    private stockexchange.User user;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double totalPrice;
    private ReservationStatus status;
    
    public double calculatePrice() {
        long hours = ChronoUnit.HOURS.between(startTime, endTime);
        return hours * car.getHourlyRate();
    }
    
    // Constructor, getters, setters
}

class CarRentalSystem {
    private Map<String, Car> cars;
    private Map<String, stockexchange.User> users;
    private Map<String, Reservation> reservations;
    
    public List<Car> getAvailableCars(CarType type, 
                                     LocalDateTime startTime, 
                                     LocalDateTime endTime) {
        return cars.values().stream()
                  .filter(car -> car.getType() == type && 
                          isCarAvailable(car, startTime, endTime))
                  .collect(Collectors.toList());
    }
    
    public Reservation makeReservation(String userId, String carId,
                                     LocalDateTime startTime, 
                                     LocalDateTime endTime) {
        stockexchange.User user = users.get(userId);
        Car car = cars.get(carId);
        
        if (user == null || car == null || 
            !isCarAvailable(car, startTime, endTime)) {
            throw new IllegalArgumentException("Invalid reservation request");
        }
        
        Reservation reservation = new Reservation(
            generateId(), car, user, startTime, endTime);
        reservation.setTotalPrice(reservation.calculatePrice());
        
        reservations.put(reservation.getId(), reservation);
        car.setStatus(CarStatus.RESERVED);
        
        return reservation;
    }
    
    public void cancelReservation(String reservationId) {
        Reservation reservation = reservations.get(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("Invalid reservation ID");
        }
        
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.getCar().setStatus(CarStatus.AVAILABLE);
    }
    
    private boolean isCarAvailable(Car car, LocalDateTime start, 
                                 LocalDateTime end) {
        return reservations.values().stream()
                .filter(r -> r.getCar().getId().equals(car.getId()) && 
                        r.getStatus() == ReservationStatus.ACTIVE)
                .noneMatch(r -> hasOverlap(r, start, end));
    }
    
    private boolean hasOverlap(Reservation reservation, 
                             LocalDateTime start, 
                             LocalDateTime end) {
        return !start.isAfter(reservation.getEndTime()) && 
               !end.isBefore(reservation.getStartTime());
    }
}
```