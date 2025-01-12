package carrentalsystem;

import carrentalsystem.model.Car;
import carrentalsystem.model.Customer;
import carrentalsystem.model.Reservation;
import carrentalsystem.model.exception.CarUnavailableException;
import carrentalsystem.service.RentalService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {
        RentalService rentalService = new RentalService(createCars(10), new ConcurrentHashMap<>());

        Customer customer1 = new Customer("1", "a@gmail.com", "1", "aa");
        Customer customer2 = new Customer("2", "b@gmail.com", "1", "bb");

        LocalDate startDate1 = LocalDate.now();
        LocalDate endDate1 = LocalDate.now().plusDays(7);
        List<Car> availableCars = rentalService.getAvailableCars(LocalDate.now(), LocalDate.now().plusDays(7));

        System.out.println(availableCars);

        Reservation reservation1 = rentalService.makeReservation(customer1, availableCars.get(0), startDate1, endDate1);

        try {
            rentalService.makeReservation(customer2, availableCars.get(0), startDate1.plusDays(2), endDate1.plusDays(2));
        } catch (CarUnavailableException e) {
            System.out.println(e.getMessage());
        }

        Reservation reservation2 = rentalService.makeReservation(customer2, availableCars.get(0), startDate1.plusDays(8), endDate1.plusDays(8));

        rentalService.completeReservation(reservation1.getId());
        rentalService.completeReservation(reservation2.getId());
    }

    private static ConcurrentHashMap<String, Car> createCars(int count) {
        ConcurrentHashMap<String, Car> cars = new ConcurrentHashMap<>();
        for (int i = 0; i < count; i++) {
            Car car = new Car(true, UUID.randomUUID().toString(), "licence" + i, "model" + i, 1250, "2010");
            cars.put(car.getId(), car);
        }
        return cars;
    }
}
