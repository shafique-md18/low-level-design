package amazon.service;

import amazon.model.*;
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

    public List<Product> searchProducts(String keyword) {
        return inventoryService.getAllProduct().stream()
                .filter(product ->  product.getName().contains(keyword)).toList();
    }

    public Order placeOrder(Cart cart, PaymentProcessor paymentProcessor) {
        // TODO: Validate all items has enough quantity in the inventory

        // Update the inventory to decrement the quantity
        for (CartItem item : cart.getItems().values()) {
            Product updatedProduct = inventoryService.getProduct(item.getProduct().getId());
            updatedProduct.setAvailableQuantity(updatedProduct.getAvailableQuantity() - item.getQuantity());
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
