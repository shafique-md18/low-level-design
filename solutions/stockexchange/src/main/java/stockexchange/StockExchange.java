package stockexchange;

import java.util.*;

public class StockExchange  {
    private final Map<String, User> users;
    private final Set<String> stocks;
    private final Map<String, OrderBook> orderBooks;

    public StockExchange() {
        this.users = new HashMap<>();
        this.stocks = new HashSet<>();
        this.orderBooks = new HashMap<>();
    }

    public User registerUser(String userId) {
        return users.computeIfAbsent(userId, _ -> new User(userId));
    }

    public void addStock(String stockId) {
        stocks.add(stockId);
        orderBooks.putIfAbsent(stockId, new OrderBook());
    }

    public void addStock(String userId, String stockId, int quantity, double price) {
        User user = users.get(userId);
        if (user != null) {
            user.addHolding(stockId, quantity, price);
        }
    }

    public void addBalance(String userId, double amount) {
        User user = users.get(userId);
        if (user != null) {
            user.addBalance(amount);
        }
    }

    public void placeOrder(Order order) {
        User user = users.get(order.getUserId());
        if (user == null || !stocks.contains(order.getStockId())) {
            return;
        }

        if (order.getType() == OrderType.BUY) {
            if (!user.canAfford(order.getPrice(), order.getQuantity())) {
                return;
            }
        } else {
            if (!user.hasEnoughStock(order.getStockId(), order.getQuantity())) {
                return;
            }
        }

        OrderBook orderBook = orderBooks.get(order.getStockId());
        orderBook.addOrder(order);
    }

    public List<Trade> getStockTrades(String stockId) {
        return orderBooks.get(stockId).getTradesSnapshot();
    }
}
