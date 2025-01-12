package carrentalsystem.service;

import carrentalsystem.model.Car;
import carrentalsystem.model.Customer;
import carrentalsystem.model.Reservation;
import carrentalsystem.model.ReservationStatus;
import carrentalsystem.model.exception.CarUnavailableException;
import carrentalsystem.payment.PaymentProcessor;
import carrentalsystem.payment.UPIPaymentProcessor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RentalService {
    private final ConcurrentHashMap<String, Car> cars;
    private final ConcurrentHashMap<String, Reservation> reservations;
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public RentalService(ConcurrentHashMap<String, Car> cars, ConcurrentHashMap<String, Reservation> reservations) {
        this.cars = cars;
        this.reservations = reservations;
    }

    public List<Car> getAvailableCars(LocalDate startDate, LocalDate endDate) {
        readLock.lock();
        try {
            return cars.values().stream()
                    .filter(Car::isAvailable)
                    .filter(car -> isCarAvailable(car, startDate, endDate))
                    .toList();
        } finally {
            readLock.unlock();
        }
    }

    public Reservation makeReservation(Customer customer, Car car, LocalDate startDate, LocalDate endDate) {
        writeLock.lock();
        try {
            if (!isCarAvailable(car, startDate, endDate)) {
                throw new CarUnavailableException("Car unavailable for reservation");
            }
            Reservation reservation = new Reservation(car, customer, endDate, UUID.randomUUID().toString(), startDate, ReservationStatus.CONFIRMED);
            reservations.put(reservation.getId(), reservation);
            cars.get(car.getId()).setAvailable(false);
            System.out.println("Created reservation - " + reservation.getId());
            return reservation;
        } finally {
            writeLock.unlock();
        }
    }

    public void cancelReservation(String reservationId) {
        writeLock.lock();
        try {
            Reservation reservation = reservations.get(reservationId);
            reservation.setStatus(ReservationStatus.CANCELLED);
            cars.get(reservation.getCar().getId()).setAvailable(true);
        } finally {
            writeLock.unlock();
        }
    }

    public void completeReservation(String reservationId) {
        writeLock.lock();
        try {
            Reservation reservation = reservations.get(reservationId);
            PaymentProcessor processor = new UPIPaymentProcessor();
            processor.paymentProcessor(reservation.getTotalPrice());
            reservation.setStatus(ReservationStatus.COMPLETED);
            cars.get(reservation.getCar().getId()).setAvailable(true);
            System.out.println("Completed reservation - " + reservationId);
        } finally {
            writeLock.unlock();
        }
    }

    // Edge case:
    //  1. We are not handling multi-day reservations
    //  2. Validate dates are not in the past and proper dates validation
    private boolean isCarAvailable(Car car, LocalDate startDate, LocalDate endDate) {
        for (Reservation reservation : reservations.values()) {
            if (ReservationStatus.CONFIRMED.equals(reservation.getStatus())
                    && (reservation.getEndDate().isAfter(startDate) && reservation.getStartDate().isBefore(endDate))) {
                return false;
            }
        }

        return true;
    }
}
