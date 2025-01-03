package parkinglot.model.parking;

import parkinglot.model.vehicle.Vehicle;

import java.util.UUID;

// TODO: Should we further refactor this parking spots of its kind
public class ParkingSpot implements Comparable<ParkingSpot> {
    private String id;
    private String spotNumber;
    // We need separate isAvailable in case we want to support reservations, under construction spots, etc.
    private boolean isAvailable;
    private boolean isElectricEnabled;
    private boolean isReservedForHandicapped;
    private ParkingSpotType type;
    private Vehicle vehicle;
    private double hourlyRate;

    public ParkingSpot(Builder builder) {
        this.id = builder.id;
        this.spotNumber = builder.spotNumber;
        this.isAvailable = builder.isAvailable;
        this.isElectricEnabled = builder.isElectricEnabled;
        this.isReservedForHandicapped = builder.isReservedForHandicapped;
        this.type = builder.type;
        this.vehicle = builder.vehicle;
        this.hourlyRate = builder.hourlyRate;
    }

    public double getHourlyRate() {
        return this.hourlyRate;
    }

    public boolean isAvailable() {
        return this.isAvailable;
    }

    public void parkVehicle(Vehicle vehicle) throws Exception {
        if (this.isReservedForHandicapped && !vehicle.isHandicapped()) {
            throw new Exception("");
        }
        if (!isCompatible(vehicle)) {
            throw new Exception("");
        }
        this.vehicle = vehicle;
        this.isAvailable = false;
    }

    public void vacateSpot() throws Exception {
        if (this.isAvailable) {
            throw new Exception("");
        }
        this.vehicle = null;
        this.isAvailable = true;
    }

    public boolean isCompatible(Vehicle vehicle) {
        return this.isAvailable && vehicle.getType().getCompatibleParkingSpotTypes().contains(this.type)
                && (!this.isElectricEnabled || vehicle.isElectric());
    }

    @Override
    public int compareTo(ParkingSpot spot) {
        return spotNumber.compareTo(spot.spotNumber);
    }

    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private String spotNumber;
        private boolean isAvailable = false;
        private boolean isElectricEnabled = false;
        private boolean isReservedForHandicapped = false;
        private ParkingSpotType type;
        private Vehicle vehicle = null;
        private double hourlyRate;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withSpotNumber(String spotNumber) {
            this.spotNumber = spotNumber;
            return this;
        }

        public Builder withAvailable(boolean available) {
            isAvailable = available;
            return this;
        }

        public Builder withElectricEnabled(boolean electricEnabled) {
            isElectricEnabled = electricEnabled;
            return this;
        }

        public Builder withReservedForHandicapped(boolean reservedForHandicapped) {
            isReservedForHandicapped = reservedForHandicapped;
            return this;
        }

        public Builder withType(ParkingSpotType type) {
            this.type = type;
            return this;
        }

        public Builder withVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public Builder withHourlyRate(double hourlyRate) {
            this.hourlyRate = hourlyRate;
            return this;
        }

        public ParkingSpot build() {
            // validate the members
            return new ParkingSpot(this);
        }
    }
}
