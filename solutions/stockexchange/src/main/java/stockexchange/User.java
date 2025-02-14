package stockexchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String id;
    private double balance;
    private Map<String, StockHolding> holdings;
    private List<Trade> tradeHistory;

    public User(String id) {
        this.id = id;
        this.balance = 0.0;
        this.holdings = new HashMap<>();
        this.tradeHistory = new ArrayList<>();
    }

    // Getters and balance management
    public void addBalance(double amount) {
        this.balance += amount;
    }

    public boolean canAfford(double price, int quantity) {
        return balance >= price * quantity;
    }

    public void deductBalance(double amount) {
        this.balance -= amount;
    }

    public void addHolding(String stockId, int quantity, double price) {
        holdings.computeIfAbsent(stockId, k -> new StockHolding(stockId, 0, 0.0));
        StockHolding holding = holdings.get(stockId);
        holding.updateHolding(quantity, price);
    }

    public boolean hasEnoughStock(String stockId, int quantity) {
        return holdings.containsKey(stockId) &&
                holdings.get(stockId).getQuantity() >= quantity;
    }
}
