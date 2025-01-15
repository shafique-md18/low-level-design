package amazon.service;

import amazon.model.Cart;
import amazon.model.Order;
import amazon.model.OrderStatus;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderService {
    private Map<String, Order> orders;

    public OrderService(Map<String, Order> orders) {
        this.orders = orders;
    }

    public Order createOrder(Cart cart) {
        Order order = new Order.Builder()
                .withCart(cart)
                .withOrderStatus(OrderStatus.PROCESSING)
                .withId(UUID.randomUUID().toString())
                .build();
        orders.put(order.getId(), order);
        return order;
    }

    public Order getOrder(String orderId) {
        return orders.get(orderId);
    }

    public List<Order> getAllOrders(String customerId) {
        return orders.values().stream()
                .filter(order -> order.getCustomer().getId().equalsIgnoreCase(customerId))
                .toList();
    }
}
