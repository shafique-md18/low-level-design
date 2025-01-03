package parkinglot.model.parking;

import parkinglot.model.parking.strategy.PaymentStrategy;
import parkinglot.model.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.*;

public class ParkingLot {
    private String id;
    private ParkingLotDetails parkingLotDetails;
    private List<ParkingFloor> floors;
    private Map<String, EntranceGate> entranceGates;
    private Map<String, ExitGate> exitGates;
    private Map<String, ParkingTicket> activeParkingTickets;
    // TODO: Is it really necessary to have two maps! In DB we would only fetch from one table
    //   Using a single query and then use them
    private Map<String, ParkingTicket> inActiveParkingTickets;
    private boolean isActive;

    private ParkingLot(Builder builder) {
        this.id = builder.id;
        this.parkingLotDetails = builder.parkingLotDetails;
        this.isActive = builder.isActive;
        this.floors = builder.floors;
        this.entranceGates = builder.entranceGates;
        this.exitGates = builder.exitGates;
        this.activeParkingTickets = builder.activeParkingTickets;
        this.inActiveParkingTickets = builder.inActiveParkingTickets;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addParkingFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public void addEntranceGate(EntranceGate entranceGate) throws Exception {
        if (entranceGates.containsKey(entranceGate.getId())) {
            throw new Exception("EntranceGate already exists");
        }
        entranceGates.put(entranceGate.getId(), entranceGate);
    }

    public void addExitGate(ExitGate exitGate) throws Exception {
        if (entranceGates.containsKey(exitGate.getId())) {
            throw new Exception("Exit already exists");
        }
        exitGates.put(exitGate.getId(), exitGate);
    }

    public ParkingTicket allocateSpotAndGenerateTicket(EntranceGate entranceGate, Vehicle vehicle) throws Exception {
        Optional<ParkingFloor> allocatedFloor = Optional.empty();
        Optional<ParkingSpot> allocatedSpot = Optional.empty();

        for (ParkingFloor floor : this.floors) {
            try {
                allocatedSpot = Optional.of(floor.assignSpot(entranceGate, vehicle));
                allocatedFloor = Optional.of(floor);
            } catch (Exception e) { // TODO - Change to proper exceptions
                continue;
            }
        }

        if (allocatedSpot.isEmpty()) {
            throw new Exception("No available spots");
        }

        ParkingTicket parkingTicket = new ParkingTicket.Builder()
                .withVehicle(vehicle)
                .withParkingFloor(allocatedFloor.get())
                .withEntranceGate(entranceGate)
                .build();

        this.activeParkingTickets.put(parkingTicket.getId(), parkingTicket);
        return parkingTicket;
    }

    public void deallocateSpotAndProcessPayment(ExitGate exitGate, String parkingTicketId, PaymentStrategy paymentMethod) throws Exception {
        if (parkingTicketId == null || !activeParkingTickets.containsKey(parkingTicketId)) {
            throw new Exception("ParkingTicket does not exist");
        }
        ParkingTicket parkingTicket = this.activeParkingTickets.get(parkingTicketId);

        LocalDateTime exitTime = LocalDateTime.now();

        PaymentStatus paymentStatus = parkingTicket.processPayment(paymentMethod, exitTime);
        if (!PaymentStatus.COMPLETED.equals(paymentStatus)) {
            throw new Exception("Payment unsuccessful");
        }
        parkingTicket.getParkingFloor().freeSpot(parkingTicket.getParkingSpot());
        parkingTicket.setExitGate(exitGate);
        parkingTicket.setExitTime(exitTime);

        // Generate a receipt and return if required!
        inActiveParkingTickets.put(parkingTicketId, parkingTicket);
        activeParkingTickets.remove(parkingTicketId);
    }

    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private ParkingLotDetails parkingLotDetails;
        private boolean isActive = false;
        private List<ParkingFloor> floors = new ArrayList<>();
        private Map<String, EntranceGate> entranceGates = new HashMap<>();
        private Map<String, ExitGate> exitGates = new HashMap<>();
        private Map<String, ParkingTicket> activeParkingTickets = new HashMap<>();
        private Map<String, ParkingTicket> inActiveParkingTickets = new HashMap<>();

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withParkingLotDetails(ParkingLotDetails parkingLotDetails) {
            this.parkingLotDetails = parkingLotDetails;
            return this;
        }

        public Builder withIsActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public ParkingLot build() {
            // Validate the members if necessary
            return new ParkingLot(this);
        }
    }
}
