package stockexchange;

import java.time.LocalTime;

public class Trade {
    private String buyOrderId;
    private String sellOrderId;
    private double price;
    private int quantity;
    private LocalTime timestamp;

    public Trade(String buyOrderId, String sellOrderId, double price, int quantity) {
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = LocalTime.now();
    }

    @Override
    public String toString() {
        return String.format("%s %f %d %s", buyOrderId, price, quantity, sellOrderId);
    }
}