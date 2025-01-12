package carrentalsystem.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private String id;
    private Car car;
    private Customer customer;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus status;
    private double totalPrice;

    public Reservation(Car car, Customer customer, LocalDate endDate, String id, LocalDate startDate, ReservationStatus status) {
        this.car = car;
        this.customer = customer;
        this.endDate = endDate;
        this.id = id;
        this.startDate = startDate;
        this.status = status;
        this.totalPrice = calculateTotalPrice();
    }

    public String getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private double calculateTotalPrice() {
        long daysRented = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return daysRented * car.getPricePerDay();
    }
}
