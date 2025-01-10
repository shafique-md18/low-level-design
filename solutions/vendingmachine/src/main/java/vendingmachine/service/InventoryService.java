package vendingmachine.service;

import vendingmachine.model.exception.InvalidItemProperty;
import vendingmachine.model.exception.ItemAlreadyExistsException;
import vendingmachine.model.inventory.Item;
import vendingmachine.repository.InventoryRepository;

import java.util.Optional;

public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Optional<Item> getItem(String itemCode) {
        return inventoryRepository.findByCode(itemCode);
    }

    public void addItem(String itemCode, double itemPrice, int itemQuantity) {
        if (inventoryRepository.findByCode(itemCode).isPresent()) {
            throw new ItemAlreadyExistsException("Cannot add item as it already exists");
        }
        if (itemPrice < 0) {
            throw new InvalidItemProperty("Item price cannot be negative");
        }
        if (itemQuantity < 0) {
            throw new InvalidItemProperty("Item quantity cannot be negative");
        }
        Item item = new Item();
        item.setItemCode(itemCode);
        item.setItemQuantity(itemQuantity);
        item.setItemPrice(itemPrice);
        inventoryRepository.save(item);
    }

    public void removeItem(String itemCode) {
        inventoryRepository.delete(itemCode);
    }

    public void updateItemStock(String itemCode, int itemQuantity) {
        if (itemQuantity < 0) {
            throw new InvalidItemProperty("Item quantity cannot be negative");
        }
        inventoryRepository.updateQuantity(itemCode, itemQuantity);
    }

    public void dispenseItem(String itemCode) {
        inventoryRepository.decrementQuantity(itemCode, 1);
    }

    public boolean isItemAvailable(String itemCode) {
        Optional<Item> item = inventoryRepository.findByCode(itemCode);
        return item.isPresent() && item.get().isAvailable();
    }
}
