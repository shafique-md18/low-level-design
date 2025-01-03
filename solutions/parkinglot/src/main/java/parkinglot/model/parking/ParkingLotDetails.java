package parkinglot.model.parking;

public class ParkingLotDetails {
    private String name;
    private Location location;
    private String contactNumber;

    public String getName() {
        return name;
    }
    public Location getLocation() {
        return location;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public ParkingLotDetails(Builder builder) {
        this.name = builder.name;
        this.location = builder.location;
        this.contactNumber = builder.contactNumber;
    }

    public static class Builder {
        private String name;
        private Location location;
        private String contactNumber;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withLocation(Location location) {
            this.location = location;
            return this;
        }

        public Builder withContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
            return this;
        }

        public ParkingLotDetails build() {
            return new ParkingLotDetails(this);
        }
    }
}
