package parkinglot.model.vehicle;

import parkinglot.model.parking.ParkingSpotType;

import java.util.List;

public enum VehicleType {
    MOTORCYCLE(List.of(ParkingSpotType.MOTORCYCLE)),
    CAR(List.of(ParkingSpotType.COMPACT)),
    TRUCK(List.of(ParkingSpotType.LARGE));

    private final List<ParkingSpotType> compatibleParkingSpotTypes;

    VehicleType(List<ParkingSpotType> compatibleParkingSpotTypes) {
        this.compatibleParkingSpotTypes = compatibleParkingSpotTypes;
    }

    public List<ParkingSpotType> getCompatibleParkingSpotTypes() {
        return compatibleParkingSpotTypes;
    }
}

