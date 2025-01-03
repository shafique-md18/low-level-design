package parkinglot.model.parking;

import parkinglot.model.parking.strategy.PaymentStrategy;
import parkinglot.model.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class ParkingTicket {
    private String id;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private ParkingFloor parkingFloor;
    private EntranceGate entranceGate;
    private ExitGate exitGate;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private LocalDateTime paymentTime;
    private long durationInHours;
    private double paymentAmount;
    private PaymentStatus paymentStatus;
    private PaymentStrategy paymentMethod;

    private ParkingTicket(Builder builder) {
        this.id = builder.id;
        this.vehicle = builder.vehicle;
        this.parkingSpot = builder.parkingSpot;
        this.parkingFloor = builder.parkingFloor;
        this.entranceGate = builder.entranceGate;
        this.exitGate = builder.exitGate;
        this.entryTime = builder.entryTime;
        this.exitTime = builder.exitTime;
        this.paymentTime = builder.paymentTime;
        this.durationInHours = builder.durationInHours;
        this.paymentAmount = builder.paymentAmount;
        this.paymentStatus = builder.paymentStatus;
        this.paymentMethod = builder.paymentMethod;
    }

    public void setExitGate(ExitGate exitGate) {
        this.exitGate = exitGate;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public ParkingSpot getParkingSpot() {
        return this.parkingSpot;
    }

    public ParkingFloor getParkingFloor() {
        return this.parkingFloor;
    }

    public String getId() {
        return id;
    }

    public long calculateDuration(LocalDateTime exitTime) {
        return ChronoUnit.HOURS.between(entryTime, exitTime);
    }

    public double calculateAmount(LocalDateTime exitTime) {
        double hourlyRate = parkingSpot.getHourlyRate();
        return hourlyRate * calculateDuration(exitTime);
    }

    public PaymentStatus processPayment(PaymentStrategy paymentMethod, LocalDateTime exitTime) {
        this.paymentTime = LocalDateTime.now();
        this.paymentAmount = calculateAmount(exitTime);
        return this.paymentStatus = paymentMethod.processPayment(this.paymentAmount);
    }

    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private Vehicle vehicle;
        private ParkingSpot parkingSpot;
        private ParkingFloor parkingFloor;
        private EntranceGate entranceGate;
        private ExitGate exitGate;
        private LocalDateTime entryTime;
        private LocalDateTime exitTime;
        private LocalDateTime paymentTime;
        private long durationInHours;
        private double paymentAmount;
        private PaymentStatus paymentStatus;
        private PaymentStrategy paymentMethod;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public Builder withParkingSpot(ParkingSpot parkingSpot) {
            this.parkingSpot = parkingSpot;
            return this;
        }

        public Builder withParkingFloor(ParkingFloor parkingFloor) {
            this.parkingFloor = parkingFloor;
            return this;
        }

        public Builder withEntranceGate(EntranceGate entranceGate) {
            this.entranceGate = entranceGate;
            return this;
        }

        public Builder withExitGate(ExitGate exitGate) {
            this.exitGate = exitGate;
            return this;
        }

        public Builder withEntryTime(LocalDateTime entryTime) {
            this.entryTime = entryTime;
            return this;
        }

        public Builder withExitTime(LocalDateTime exitTime) {
            this.exitTime = exitTime;
            return this;
        }

        public Builder withPaymentTime(LocalDateTime paymentTime) {
            this.paymentTime = paymentTime;
            return this;
        }

        public Builder withDurationInHours(long durationInHours) {
            this.durationInHours = durationInHours;
            return this;
        }

        public Builder withPaymentAmount(double paymentAmount) {
            this.paymentAmount = paymentAmount;
            return this;
        }

        public Builder withPaymentStatus(PaymentStatus paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Builder withPaymentMethod(PaymentStrategy paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public ParkingTicket build() {
            return new ParkingTicket(this);
        }
    }
}
