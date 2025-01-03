package parkinglot.service;

import parkinglot.model.parking.EntranceGate;
import parkinglot.model.parking.ExitGate;
import parkinglot.model.parking.ParkingLot;
import parkinglot.model.parking.ParkingTicket;
import parkinglot.model.parking.strategy.PaymentStrategy;
import parkinglot.model.vehicle.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParkingLotService {
    // Use Id based here so that we can uniquely identify if the parking lot exists from Main
    // TODO: We can check here if we want to use Set/List Instead!
    private final Map<String, ParkingLot> parkingLots;
    private static ParkingLotService instance;

    private ParkingLotService() {
        this.parkingLots = new HashMap<>();
    }

    public static synchronized ParkingLotService getInstance() {
        if (instance == null) {
            instance = new ParkingLotService();
        }
        return instance;
    }

    public void addParkingLot(ParkingLot parkingLot) throws Exception {
        if (parkingLots.containsKey(parkingLot.getId())) {
            throw new Exception("ParkingLot already exists!");
        }
        this.parkingLots.put(parkingLot.getId(), parkingLot);
    }

    public void removeParkingLot(ParkingLot parkingLot) throws Exception {
        if (!doesParkingLotExist(parkingLot.getId())) {
            throw new Exception("ParkingLot does not exist for removal");
        }
        parkingLot.setActive(false);
    }

    public ParkingTicket generateTicket(String lotId, EntranceGate entranceGate, Vehicle vehicle) throws Exception {
        if (!doesParkingLotExist(lotId)) {
            throw new Exception("ParkingLot does not exist to generate ticket");
        }
        return this.parkingLots.get(lotId).allocateSpotAndGenerateTicket(entranceGate, vehicle);
    }

    public void exitVehicle(String lotId, ExitGate exitGate, String parkingTicketId, PaymentStrategy paymentMethod) throws Exception {
        if (!doesParkingLotExist(lotId)) {
            throw new Exception("ParkingLot does not exist to generate ticket");
        }
        ParkingLot parkingLot = this.parkingLots.get(lotId);
        parkingLot.deallocateSpotAndProcessPayment(exitGate, parkingTicketId, paymentMethod);

        // Return a receipt if required
    }

    private boolean doesParkingLotExist(String parkingLotId) throws Exception {
        return parkingLotId != null && this.parkingLots.containsKey(parkingLotId);
    }
}
