package parkinglot.model.parking.strategy;

import parkinglot.model.parking.EntranceGate;
import parkinglot.model.parking.ParkingSpot;
import parkinglot.model.vehicle.Vehicle;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public interface SpotAllocationStrategy {
    ParkingSpot findAvailableSpot(Vehicle vehicle, Set<ParkingSpot> spots, EntranceGate entranceGate) throws Exception;
}
