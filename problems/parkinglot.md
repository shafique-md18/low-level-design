# Designing a Parking Lot System

## Requirements
1. The parking lot should have multiple floors where customers can park their cars.
1. The parking lot should have multiple entry and exit points.
1. Customers can collect a parking ticket from the entry points and can pay the parking fee at the exit points on their way out.
1. Customers can pay the tickets at the automated exit panel or to the parking attendant.
1. Customers can pay via both cash and credit cards.
1. Not required -> Customers should also be able to pay the parking fee at the customer’s info portal on each floor. If the customer has paid at the info portal, they don’t have to pay at the exit.
1. The system should not allow more vehicles than the maximum capacity of the parking lot. If the parking is full, the system should be able to show a message at the entrance panel and on the parking display board on the ground floor.
1. Each parking floor will have many parking spots. The system should support multiple types of parking spots such as Compact, Large, Handicapped, Motorcycle, etc.
1. The Parking lot should have some parking spots specified for electric cars. These spots should have an electric panel through which customers can pay and charge their vehicles.
1. The system should support parking for different types of vehicles like car, truck, van, motorcycle, etc.
1. Each parking floor should have a display board showing any free parking spot for each spot type.
1. The system should support a per-hour parking fee model. For example, customers have to pay $4 for the first hour, $3.5 for the second and third hours, and $2.5 for all the remaining hours.

## Implementations
#### [Java Implementation](../solutions/parkinglot)

## Classes, Interfaces and Enumerations
1. The **ParkingLot** class follows the Singleton pattern to ensure only one instance of the parking lot exists. It maintains a list of levels and provides methods to park and unpark vehicles.
1. The **Level** class represents a level in the parking lot and contains a list of parking spots. It handles parking and unparking of vehicles within the level.
1. The **ParkingSpot** class represents an individual parking spot and tracks the availability and the parked vehicle.
1. The **Vehicle** class is an abstract base class for different types of vehicles. It is extended by Car, Motorcycle, and Truck classes.
1. The **VehicleType** enum defines the different types of vehicles supported by the parking lot.
1. Multi-threading is achieved through the use of synchronized keyword on critical sections to ensure thread safety.
1. The **ParkingLotDriver** class demonstrates the usage of the parking lot system.