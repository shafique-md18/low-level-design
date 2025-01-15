package amazon.service;

import amazon.model.Cart;
import amazon.model.Customer;
import amazon.model.Item;
import amazon.model.Order;
import amazon.payment.PaymentProcessor;

import java.util.List;
import java.util.Map;

public class ShoppingService {
    private final InventoryService inventoryService;
    private final OrderService orderService;
    private final Map<String, Customer> customers;

    public ShoppingService(InventoryService inventoryService, OrderService orderService, Map<String, Customer> customers) {
        this.inventoryService = inventoryService;
        this.orderService = orderService;
        this.customers = customers;
    }

    public void addCustomer(Customer customer) {
        this.customers.put(customer.getId(), customer);
    }

    public List<Item> searchProducts(String keyword) {
        return inventoryService.getAllItems().stream()
                .filter(item ->  item.getProduct().getName().contains(keyword)).toList();
    }

    public Order placeOrder(Cart cart, PaymentProcessor paymentProcessor) {
        // TODO: Validate all items has enough quantity in the inventory

        // Update the inventory to decrement the quantity
        for (Item item : cart.getItems().values()) {
            Item updatedItem = inventoryService.getItem(item.getId());
            updatedItem.setQuantity(updatedItem.getQuantity() - item.getQuantity());
        }

        Order order = orderService.createOrder(cart);

        // Process payment
        // TODO: if unsuccessful -> inventory should revert back the quantities and update order status as cancelled
        paymentProcessor.processPayment(cart.getCartTotal());

        return order;
    }

    public void displayOrderDetails(String orderId) {
        System.out.println(orderService.getOrder(orderId).getOrderDetails());
    }
}
