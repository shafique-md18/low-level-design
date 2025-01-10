package vendingmachine.repository;

import vendingmachine.model.inventory.Item;

import java.util.Optional;

public interface InventoryRepository {
    Optional<Item> findByCode(String itemCode);
    Item save(Item item);
    void delete(String itemCode);
    // For dispensing items (can handle multiple quantities)
    Optional<Item> decrementQuantity(String itemCode, int quantity);
    // For refilling/restocking
    Optional<Item> updateQuantity(String itemCode, int newQuantity);
}
