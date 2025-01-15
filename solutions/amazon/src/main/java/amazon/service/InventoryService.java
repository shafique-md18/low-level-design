package amazon.service;

import amazon.model.Item;

import java.util.List;
import java.util.Map;

public class InventoryService {
    private Map<String, Item> items;

    public InventoryService(Map<String, Item> items) {
        this.items = items;
    }

    public void upsertItem(Item item) {
        this.items.put(item.getId(), item);
    }

    public void removeItem(String itemId) {
        this.items.remove(itemId);
    }

    public Item getItem(String itemId) {
        return this.items.get(itemId);
    }

    public List<Item> getAllItems() {
        return this.items.values().stream().toList();
    }
}
