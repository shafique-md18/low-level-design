package stockexchange;

import java.time.LocalTime;

public class Order {
    private String orderId;
    private String userId;
    private LocalTime time;
    private String stockId;
    private OrderType type;
    private double price;
    private int quantity;
    private int remainingQuantity;
    private OrderStatus status;

    public Order(String orderId, String userId, LocalTime time, String stockId,
                 OrderType type, double price, int quantity) {
        this.orderId = orderId;
        this.userId = userId;
        this.time = time;
        this.stockId = stockId;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.remainingQuantity = quantity;
        this.status = OrderStatus.PENDING;
    }

    public void updatePrice(double newPrice) {
        this.price = newPrice;
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity <= this.quantity - (this.quantity - this.remainingQuantity)) {
            this.quantity = newQuantity;
            this.remainingQuantity = newQuantity - (this.quantity - this.remainingQuantity);
        }
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getStockId() {
        return stockId;
    }

    public OrderType getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }
}
