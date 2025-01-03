package parkinglot.model.parking;

import parkinglot.model.parking.strategy.DefaultSpotAllocationStrategy;
import parkinglot.model.parking.strategy.SpotAllocationStrategy;
import parkinglot.model.vehicle.Vehicle;

import java.util.Set;
import java.util.TreeSet;

public class ParkingSpotManager {
    private final Set<ParkingSpot> parkingSpots;
    private final SpotAllocationStrategy spotAllocationStrategy;

    private ParkingSpotManager(Builder builder) {
        this.parkingSpots = builder.parkingSpots;
        this.spotAllocationStrategy = builder.spotAllocationStrategy;
    }

    public ParkingSpot assignSpot(EntranceGate entranceGate, Vehicle vehicle) throws Exception {
        ParkingSpot spot = spotAllocationStrategy.findAvailableSpot(vehicle, parkingSpots, entranceGate);
        spot.parkVehicle(vehicle);
        return spot;
    }

    public void freeSpot(ParkingSpot spot) throws Exception {
        spot.vacateSpot();
    }

    public void addParkingSpot(ParkingSpot spot) {
        parkingSpots.add(spot);
    }

    public void removeParkingSpot(ParkingSpot spot) {
        if (!parkingSpots.contains(spot)) {
            return;
        }
        parkingSpots.remove(spot);
    }

    public static class Builder {
        private Set<ParkingSpot> parkingSpots = new TreeSet<>();
        private SpotAllocationStrategy spotAllocationStrategy = new DefaultSpotAllocationStrategy();

        public Builder withParkingSpots(Set<ParkingSpot> parkingSpots) {
            this.parkingSpots = parkingSpots;
            return this;
        }

        public Builder withSpotAllocationStrategy(SpotAllocationStrategy spotAllocationStrategy) {
            this.spotAllocationStrategy = spotAllocationStrategy;
            return this;
        }

        public ParkingSpotManager build() {
            return new ParkingSpotManager(this);
        }
    }
}
