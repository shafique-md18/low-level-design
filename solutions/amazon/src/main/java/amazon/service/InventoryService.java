package amazon.service;

import amazon.model.Product;

import java.util.List;
import java.util.Map;

public class InventoryService {
    private Map<String, Product> products;

    public InventoryService(Map<String, Product> products) {
        this.products = products;
    }

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
