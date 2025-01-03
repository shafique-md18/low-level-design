package parkinglot.model.parking;

import parkinglot.model.parking.strategy.DefaultSpotAllocationStrategy;
import parkinglot.model.parking.strategy.SpotAllocationStrategy;
import parkinglot.model.vehicle.Vehicle;

import java.util.TreeSet;
import java.util.UUID;

public class ParkingFloor {
    private String id;
    private ParkingSpotManager parkingSpotManager;


    private ParkingFloor(Builder builder) {
        this.id = builder.id;
        this.parkingSpotManager = builder.parkingSpotManager;
    }

    public ParkingSpot assignSpot(EntranceGate entranceGate, Vehicle vehicle) throws Exception {
        return parkingSpotManager.assignSpot(entranceGate, vehicle);
    }

    public void freeSpot(ParkingSpot spot) throws Exception {
        parkingSpotManager.freeSpot(spot);
    }

    public void addParkingSpot(ParkingSpot spot) {
        parkingSpotManager.addParkingSpot(spot);
    }

    public void removeParkingSpot(ParkingSpot spot) {
        parkingSpotManager.removeParkingSpot(spot);
    }

    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private ParkingSpotManager parkingSpotManager = new ParkingSpotManager.Builder().build();

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withParkingSpotManager(ParkingSpotManager parkingSpotManager) {
            this.parkingSpotManager = parkingSpotManager;
            return this;
        }

        public ParkingFloor build() {
            return new ParkingFloor(this);
        }
    }
}
