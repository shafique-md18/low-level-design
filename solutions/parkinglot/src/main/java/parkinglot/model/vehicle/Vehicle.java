package parkinglot.model.vehicle;

public class Vehicle {
    private final String licensePlateNumber;
    private final VehicleType type;
    private boolean isElectric;
    private boolean isHandicapped;

    Vehicle(String licensePlateNumber, VehicleType type, boolean isElectric) {
        this.licensePlateNumber = licensePlateNumber;
        this.type = type;
        this.isElectric = isElectric;
    }

    public String getLicensePlateNumber() {
        return this.licensePlateNumber;
    }

    public VehicleType getType() {
        return this.type;
    }

    public boolean isElectric() {
        return this.isElectric;
    }

    public boolean isHandicapped() {
        return this.isHandicapped;
    }

    public boolean setIsElectric(boolean isElectric) {
        return this.isElectric = isElectric;
    }

    public boolean setIsHandicapped(boolean isHandicapped) {
        return this.isHandicapped = isHandicapped;
    }
}
