package uber.model;

public class Ride {
    private final String id;
    private Customer passenger;
    private Driver driver;
    private RideStatus status;
    private double fare;
    private Location source;
    private Location destination;

    private Ride(Builder builder) {
        id = builder.id;
        passenger = builder.passenger;
        driver = builder.driver;
        status = builder.status;
        fare = builder.fare;
        source = builder.source;
        destination = builder.destination;
    }

    public String getId() {
        return id;
    }

    public Customer getPassenger() {
        return passenger;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public Location getSource() {
        return source;
    }

    public Location getDestination() {
        return destination;
    }

    public static final class Builder {
        private String id;
        private Customer passenger;
        private Driver driver;
        private RideStatus status;
        private double fare;
        private Location source;
        private Location destination;

        public Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withPassenger(Customer passenger) {
            this.passenger = passenger;
            return this;
        }

        public Builder withDriver(Driver driver) {
            this.driver = driver;
            return this;
        }

        public Builder withStatus(RideStatus status) {
            this.status = status;
            return this;
        }

        public Builder withFare(double fare) {
            this.fare = fare;
            return this;
        }

        public Builder withSource(Location source) {
            this.source = source;
            return this;
        }

        public Builder withDestination(Location destination) {
            this.destination = destination;
            return this;
        }

        public Ride build() {
            return new Ride(this);
        }
    }
}
