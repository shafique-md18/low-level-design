package uber;

import uber.model.*;
import uber.payment.UPIPaymentProcessor;
import uber.service.RideService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer(UUID.randomUUID().toString());
        customer.setName("Shafique");
        customer.setEmail("abc@xyz.com");

        List<Driver> drivers = createDrivers(10);

        RideService rideService = new RideService.Builder().build();

        rideService.addCustomer(customer);
        drivers.forEach(rideService::addDriver);

        rideService.requestRide(customer, new Location(5.25, 45.90), new Location(80.60, 3.75));

        Driver driver1 = drivers.getFirst();

        Ride ride = Objects.requireNonNull(rideService.getRequestedRides().peek());

        rideService.acceptRide(driver1, ride);

        rideService.startRide(ride);

        rideService.completeRide(ride, new UPIPaymentProcessor());
    }

    private static List<Driver> createDrivers(int d) {
        List<Driver> drivers = new ArrayList<>();

        for (int i = 0; i < d; i++) {
            Driver driver = new Driver(UUID.randomUUID().toString());
            driver.setName("driver_"+i);
            driver.setEmail("driver."+i+"@uber.com");
            driver.setStatus(DriverStatus.AVAILABLE);
            driver.setCurrentLocation(new Location(10.00, 20.00));
            drivers.add(driver);
        }

        return drivers;
    }
}
