package vendingmachine.repository;

import vendingmachine.model.exception.ItemNotExistsException;
import vendingmachine.model.inventory.Item;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryInventoryRepository implements InventoryRepository {
    private final Map<String, Item> inventory;

    public InMemoryInventoryRepository() {
        this.inventory = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<Item> findByCode(String itemCode) {
        return Optional.ofNullable(inventory.get(itemCode));
    }

    @Override
    public Item save(Item item) {
        inventory.put(item.getItemCode(), item);
        return item;
    }

    @Override
    public void delete(String itemCode) {
        if (!inventory.containsKey(itemCode)) {
            throw new ItemNotExistsException("Cannot remove item as it does not exist - " + itemCode);
        }
        Item item = inventory.get(itemCode);
        inventory.remove(itemCode);
    }

    @Override
    public Optional<Item> decrementQuantity(String itemCode, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        return Optional.ofNullable(
                inventory.computeIfPresent(itemCode, (_, item) -> {
                    if (item.getItemQuantity() >= quantity) {
                        item.setItemQuantity(item.getItemQuantity() - quantity);
                        return item;
                    }
                    return null;
                })
        );
    }

    @Override
    public Optional<Item> updateQuantity(String itemCode, int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        return Optional.ofNullable(
                inventory.computeIfPresent(itemCode, (_, item) -> {
                    item.setItemQuantity(newQuantity);
                    return item;
                })
        );
    }
}
