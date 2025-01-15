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
                .build();

        Product queen_size_bedsheet = new Product.Builder()
                .withId(UUID.randomUUID().toString())
                .withName("Queen BedSheet Cotton")
                .build();

        InventoryService inventoryService = new InventoryService(new HashMap<>());
        Item productItem1 = new Item.Builder().withId(UUID.randomUUID().toString())
                .withProduct(samsung_s23_ultra).withQuantity(5).withPricePerUnit(92500).build();
        inventoryService.upsertItem(productItem1);
        Item productItem2 = new Item.Builder().withId(UUID.randomUUID().toString())
                .withProduct(queen_size_bedsheet).withQuantity(15).withPricePerUnit(340).build();
        inventoryService.upsertItem(productItem2);

        OrderService orderService = new OrderService(new HashMap<>());
        ShoppingService shoppingService = new ShoppingService(inventoryService, orderService, customers);

        Map<String, Item> cartItems = new HashMap<>();
        Item cartItem1 = new Item.Builder().withId(productItem1.getId())
                .withProduct(productItem1.getProduct())
                .withQuantity(2)
                .withPricePerUnit(productItem1.getPricePerUnit()).build();
        Item cartItem2 = new Item.Builder().withId(productItem2.getId())
                .withProduct(productItem2.getProduct())
                .withQuantity(3)
                .withPricePerUnit(productItem2.getPricePerUnit()).build();
        cartItems.put(cartItem1.getId(), cartItem1);
        cartItems.put(cartItem2.getId(), cartItem2);
        Cart cart = new Cart(UUID.randomUUID().toString(), customer, cartItems);

        Order order = shoppingService.placeOrder(cart, new UPIPaymentProcessor());
        order.confirmOrder();
        order.markInTransit("tracking-id-01");
        order.markDelivered();
        shoppingService.displayOrderDetails(order.getId());
    }
}
