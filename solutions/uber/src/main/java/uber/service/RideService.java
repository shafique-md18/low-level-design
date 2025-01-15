package uber.service;

import uber.model.*;
import uber.payment.PaymentProcessor;

import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class RideService {
    private static final double FARE_PER_KM = 18;
    private Map<String, Customer> customers;
    private Map<String, Driver> drivers;
    private Map<String, Ride> rides;
    private Queue<Ride> requestedRides;
    private Map<String, ReentrantLock> rideLocks = new ConcurrentHashMap<>();

    private RideService(Builder builder) {
        customers = builder.customers;
        drivers = builder.drivers;
        rides = builder.rides;
        requestedRides = builder.requestedRides;
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    public void addDriver(Driver driver) {
        drivers.put(driver.getId(), driver);
    }

    public void requestRide(Customer customer, Location source, Location destination) {
        Ride ride = new Ride.Builder()
                .withId(UUID.randomUUID().toString())
                .withPassenger(customer)
                .withSource(source)
                .withDestination(destination)
                .withStatus(RideStatus.REQUESTED)
                .withFare(0.0)
                .build();
        rides.put(ride.getId(), ride);
        requestedRides.add(ride);
        notifyDrivers(ride);
    }

    public void acceptRide(Driver driver, Ride ride) {
        ReentrantLock rideLock = rideLocks.computeIfAbsent(ride.getId(), k -> new ReentrantLock(true));

        try {
            if (!rideLock.tryLock(5, TimeUnit.SECONDS)) {
                throw new RuntimeException("Could not acquire lock for the ride");
            }

            if (!RideStatus.REQUESTED.equals(ride.getStatus())) {
                throw new IllegalStateException("Cannot accept ride which is not in requested state");
            }
            // Remove from requested queue first to prevent other drivers from accepting
            if (!requestedRides.remove(ride)) {
                throw new IllegalStateException("Ride already accepted by another driver");
            }
            ride.setDriver(driver);
            ride.setStatus(RideStatus.ACCEPTED);
            notifyCustomer(ride);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(e.getMessage());
        } finally {
            rideLock.unlock();
        }
    }

    public void startRide(Ride ride) {
        if (!RideStatus.ACCEPTED.equals(ride.getStatus())) {
            throw new IllegalStateException("Cannot start ride which is not in accepted state");
        }
        ride.getDriver().setStatus(DriverStatus.BUSY);
        ride.setStatus(RideStatus.STARTED);
        notifyCustomer(ride);
    }

    public void completeRide(Ride ride, PaymentProcessor paymentProcessor) {
        if (!RideStatus.STARTED.equals(ride.getStatus())) {
            throw new IllegalStateException("Cannot complete ride which is not in started state");
        }
        double fare = calculateFare(ride);
        ride.setFare(fare);
        paymentProcessor.processPayment(ride.getFare());
        ride.setStatus(RideStatus.COMPLETED);
        ride.getDriver().setStatus(DriverStatus.AVAILABLE);
        notifyCustomer(ride);
        notifyDriver(ride);
    }

    public Queue<Ride> getRequestedRides() {
        return this.requestedRides;
    }

    private double calculateFare(Ride ride) {
        double distance = calculateDistance(ride.getSource(), ride.getDestination());

        return Math.round(distance * FARE_PER_KM * 100) / 100.0;
    }

    private double calculateDistance(Location source, Location destination) {
        return Math.random() * 20 + 1;
    }

    private void notifyDrivers(Ride ride) {
        for (Driver driver : drivers.values()) {
            if (calculateDistance(ride.getSource(), driver.getCurrentLocation()) < 10) {
                System.out.println("Notifying driver: " + driver.getName() + " for ride request - " + ride.getId());
            }
        }
    }

    private void notifyCustomer(Ride ride) {
        String message = "";

        switch (ride.getStatus()) {
            case ACCEPTED:
                message = "Your ride has been accepted by " + ride.getDriver().getName();
                break;
            case REQUESTED:
                message = "Requested nearby drivers for your ride";
                break;
            case STARTED:
                message = "Your ride has been started";
                break;
            case COMPLETED:
                message = "You ride is competed. Fare: INR " + ride.getFare();
                break;
            default:
                break;
        }
        System.out.println("Notifying customer: " + ride.getPassenger().getName() + " - " + message);
    }

    private void notifyDriver(Ride ride) {
        Driver driver = ride.getDriver();
        if (driver != null) {
            String message = "";
            switch (ride.getStatus()) {
                case COMPLETED:
                    message = "Ride completed. Fare: INR " + ride.getFare();
                    break;
                default:
                    break;
            }
            System.out.println("Notifying driver: " + driver.getName() + " - " + message);
        }
    }

    public static final class Builder {
        private Map<String, Customer> customers = new ConcurrentHashMap<>();
        private Map<String, Driver> drivers = new ConcurrentHashMap<>();
        private Map<String, Ride> rides = new ConcurrentHashMap<>();
        private Queue<Ride> requestedRides = new ConcurrentLinkedQueue<>();

        public Builder() {
        }

        public Builder withCustomers(Map<String, Customer> customers) {
            this.customers = customers;
            return this;
        }

        public Builder withDrivers(Map<String, Driver> drivers) {
            this.drivers = drivers;
            return this;
        }

        public Builder withRides(Map<String, Ride> rides) {
            this.rides = rides;
            return this;
        }

        public Builder withRequestedRides(Queue<Ride> requestedRides) {
            this.requestedRides = requestedRides;
            return this;
        }

        public RideService build() {
            return new RideService(this);
        }
    }
}
