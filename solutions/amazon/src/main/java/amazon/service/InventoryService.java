package amazon.service;

import amazon.model.Product;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class InventoryService {
    // This should be ConcurrentHashMap
    // We should also have Product vs Quantity(AtomicInteger)
    // private final Map<String, AtomicInteger> inventory = new ConcurrentHashMap<>();
    // and optimistic inventory reservation boolean tryReduceQuantity(Map<String, Integer> productQuantities)
    private Map<String, Product> products;

    public InventoryService(Map<String, Product> products) {
        this.products = products;
    }

    /*
    Optimistic product quantity reservations during booking
    public synchronized boolean tryReduceQuantity(Map<String, Integer> productQuantities) {
        // First verify all quantities are available
        for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            String productId = entry.getKey();
            int requiredQuantity = entry.getValue();

            AtomicInteger current = inventory.get(productId);
            if (current == null || current.get() < requiredQuantity) {
                return false;
            }
        }

        for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            inventory.get(productId).addAndGet(-quantity);
        }
        return true;
    }
     */

    public void upsertProduct(Product item) {
        this.products.put(item.getId(), item);
    }

    public void removeProduct(String productId) {
        this.products.remove(productId);
    }

    public Product getProduct(String productId) {
        return this.products.get(productId);
    }

    public List<Product> getAllProduct() {
        return this.products.values().stream().toList();
    }
}
