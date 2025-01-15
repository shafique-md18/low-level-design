package amazon.model;

import java.util.Map;

public class Cart {
    private final String id;
    private Customer customer;
    private Map<String, CartItem> items;

    public Cart(String id, Customer customer, Map<String, CartItem> items) {
        this.id = id;
        this.items = items;
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public Map<String, CartItem> getItems() {
        return items;
    }

    public void addItem(CartItem item) {
        items.put(item.getProduct().getId(), item);
    }

    public void removeCartItem(String itemId) {
        items.remove(itemId);
    }

    public double getCartTotal() {
        return items.values().stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();
    }
}
