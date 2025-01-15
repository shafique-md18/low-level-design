package uber.model;

public class Driver extends User {
    private Location currentLocation;
    private DriverStatus status;

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public Driver(String id) {
        super(id);
    }
}
