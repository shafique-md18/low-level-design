package parkinglot.model.parking.strategy;

import parkinglot.model.parking.PaymentStatus;

public class UpiPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentStatus processPayment(double amount) {
        return PaymentStatus.COMPLETED;
    }
}
