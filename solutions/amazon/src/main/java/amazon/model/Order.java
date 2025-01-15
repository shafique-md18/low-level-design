package amazon.model;

import bookmyshow.model.User;

public class Order {
    private final String id;
    private final Customer customer;
    private final Cart cart;
    private OrderStatus orderStatus;
    private String trackingId;

    private Order(Builder builder) {
        id = builder.id;
        customer = builder.customer;
        cart = builder.cart;
        orderStatus = builder.orderStatus;
        trackingId = builder.trackingId;
    }

    // Might want to send email/notifications to observers
    public void confirmOrder() {
        orderStatus = OrderStatus.CONFIRMED;
    }

    public void markInTransit(String trackingId) {
        this.orderStatus = OrderStatus.IN_TRANSIT;
        this.trackingId = trackingId;
    }

    public void markDelivered() {
        this.orderStatus = OrderStatus.DELIVERED;
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCELLED;
    }

    public String getOrderDetails() {
        return String.format("OrderId = %s\n" + "CartTotal = %s\n" + "OrderStatus=%s",
                id, cart.getCartTotal(), orderStatus);
    }

    public String getId() {
        return id;
    }

    public Cart getCart() {
        return cart;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public static final class Builder {
        private String id;
        private Customer customer;
        private Cart cart;
        private OrderStatus orderStatus;
        private String trackingId;

        public Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder withCart(Cart cart) {
            this.cart = cart;
            return this;
        }

        public Builder withOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Builder withTrackingId(String trackingId) {
            this.trackingId = trackingId;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
