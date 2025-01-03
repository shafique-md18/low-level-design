package parkinglot;

import parkinglot.model.parking.*;
import parkinglot.model.parking.strategy.DefaultSpotAllocationStrategy;
import parkinglot.model.parking.strategy.NearestToEntranceSpotAllocationStrategy;
import parkinglot.service.ParkingLotService;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // Main service to manage parking lot
        ParkingLotService parkingLotService = ParkingLotService.getInstance();

        // Create two sample parking lots
        ParkingLot parkingLot1 = new ParkingLot.Builder()
                .withParkingLotDetails(new ParkingLotDetails.Builder()
                        .withName("Parking-Lot-1")
                        .withContactNumber("0123456789")
                        .withLocation(new Location("ABC", "City1", "State1", "012345", "India"))
                        .build())
                .build();
        parkingLot1.addEntranceGate(new EntranceGate("Entrance-01"));
        parkingLot1.addEntranceGate(new EntranceGate("Entrance-02"));
        parkingLot1.addExitGate(new ExitGate("Exit-01"));
        parkingLotService.addParkingLot(parkingLot1);

        ParkingLot parkingLot2 = new ParkingLot.Builder()
                .withParkingLotDetails(new ParkingLotDetails.Builder()
                        .withName("Parking-Lot-2")
                        .withContactNumber("1123456789")
                        .withLocation(new Location("ABC", "City1", "State1", "012345", "India"))
                        .build())
                .build();
        parkingLot2.addEntranceGate(new EntranceGate("Entrance-01"));
        parkingLot2.addExitGate(new ExitGate("Exit-01"));
        parkingLotService.addParkingLot(parkingLot2);

        // Create parking floors with Spots in both parking lots
        for (int i = 0; i < 5; i++) {
            Set<ParkingSpot> parkingSpots1 = new TreeSet<>();
            Set<ParkingSpot> parkingSpots2 = new TreeSet<>();
            for (int j = 0; j < 10; j++) {
                parkingSpots1.add(new ParkingSpot.Builder()
                        .withAvailable(true)
                        .withHourlyRate(1.0)
                        .withSpotNumber("Parking-Spot-" + j)
                        .withElectricEnabled(true)
                        .withType(ParkingSpotType.values()[new Random().nextInt(ParkingSpotType.values().length)])
                        .build()
                );

                parkingSpots2.add(new ParkingSpot.Builder()
                        .withAvailable(true)
                        .withHourlyRate(2.0)
                        .withSpotNumber("Parking-Spot-" + j)
                        .withElectricEnabled(false)
                        .withType(ParkingSpotType.values()[new Random().nextInt(ParkingSpotType.values().length)])
                        .build()
                );
            }
            parkingLot1.addParkingFloor(new ParkingFloor.Builder()
                    .withParkingSpotManager(new ParkingSpotManager.Builder()
                            .withParkingSpots(parkingSpots1)
                            .withSpotAllocationStrategy(new DefaultSpotAllocationStrategy())
                            .build())
                    .build());
            parkingLot2.addParkingFloor(new ParkingFloor.Builder()
                    .withParkingSpotManager(new ParkingSpotManager.Builder()
                            .withParkingSpots(parkingSpots2)
                            .withSpotAllocationStrategy(new NearestToEntranceSpotAllocationStrategy())
                            .build())
                    .build());
        }

        // Create vehicles and park
        for (int i = 0; i < 50; i++) {
            // Generate ticket - Handle Parking Lot full
        }

        // Display Parking Lot Status for each floor

        // Exit the vehicles

        // Display Parking Lot Status for each floor
    }
}