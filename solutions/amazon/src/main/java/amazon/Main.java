package amazon;

import amazon.model.*;
import amazon.payment.UPIPaymentProcessor;
import amazon.service.InventoryService;
import amazon.service.OrderService;
import amazon.service.ShoppingService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Map<String, Customer> customers = new HashMap<>();
        Customer customer = new Customer.Builder().withId(UUID.randomUUID().toString())
                .withName("customer_1")
                .withAddress("abc")
                .withPhoneNumber("123").build();
        customers.put(customer.getId(), customer);

        Product samsung_s23_ultra = new Product.Builder()
                .withId(UUID.randomUUID().toString())
                .withName("Samsung Galaxy S23 Ultra (256GB)")
                .withAvailableQuantity(5)
                .withAvailableQuantity(10)
                .withPrice(92500)
                .build();

        Product queen_size_bedsheet = new Product.Builder()
                .withId(UUID.randomUUID().toString())
                .withName("Queen BedSheet Cotton")
                .withAvailableQuantity(5)
                .withAvailableQuantity(10)
                .withPrice(340)
                .build();

        InventoryService inventoryService = new InventoryService(new HashMap<>());
        inventoryService.upsertProduct(samsung_s23_ultra);
        inventoryService.upsertProduct(queen_size_bedsheet);

        OrderService orderService = new OrderService(new HashMap<>());
        ShoppingService shoppingService = new ShoppingService(inventoryService, orderService, customers);

        Map<String, CartItem> cartItems = new HashMap<>();
        CartItem cartItem1 = new CartItem.Builder()
                .withProduct(samsung_s23_ultra)
                .withQuantity(2)
                .build();
        CartItem cartItem2 = new CartItem.Builder()
                .withProduct(queen_size_bedsheet)
                .withQuantity(3)
                .build();
        cartItems.put(cartItem1.getProduct().getId(), cartItem1);
        cartItems.put(cartItem2.getProduct().getId(), cartItem2);
        Cart cart = new Cart(UUID.randomUUID().toString(), customer, cartItems);

        Order order = shoppingService.placeOrder(cart, new UPIPaymentProcessor());
        order.confirmOrder();
        order.markInTransit("tracking-id-01");
        order.markDelivered();
        shoppingService.displayOrderDetails(order.getId());
    }
}
