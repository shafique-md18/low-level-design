package parkinglot.model.parking.strategy;

import parkinglot.model.parking.PaymentStatus;

public interface PaymentStrategy {
    PaymentStatus processPayment(double amount);
}
