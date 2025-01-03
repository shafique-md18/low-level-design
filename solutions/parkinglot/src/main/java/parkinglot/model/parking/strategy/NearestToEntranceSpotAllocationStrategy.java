package parkinglot.model.parking.strategy;

import parkinglot.model.parking.EntranceGate;
import parkinglot.model.parking.ParkingSpot;
import parkinglot.model.vehicle.Vehicle;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class NearestToEntranceSpotAllocationStrategy implements SpotAllocationStrategy{
    @Override
    public ParkingSpot findAvailableSpot(Vehicle vehicle, Set<ParkingSpot> spots, EntranceGate entranceGate) throws Exception {
        Optional<ParkingSpot> nearestSpot = spots.stream()
                .filter(ParkingSpot::isAvailable)
                // TODO: Use gate distance map in ParkingSpot to find the nearest spot
                .min(Comparator.comparingDouble(ParkingSpot::getHourlyRate));

        if (nearestSpot.isEmpty()) {
            throw new Exception("No available parking spots found.");
        }

        return nearestSpot.get();
    }
}
