package stockexchange;

import java.time.LocalTime;

public class StockExchangeDemo {
    public static void main(String[] args) {
        StockExchange exchange = new StockExchange();

        // Register users
        exchange.registerUser("user1");
        exchange.registerUser("user2");

        // Add stock
        exchange.addStock("Meesho");

        // Add user balance
        exchange.addBalance("user1", 1000000.0);
        exchange.addBalance("user2", 1000000.0);

        // Add user stock
        exchange.addStock("user1", "Meesho", 200, 100.0);
        exchange.addStock("user2", "Meesho", 200, 100.0);

        // Place orders
        Order order1 = new Order("#1", "user1", LocalTime.parse("09:45"), "Meesho",
                OrderType.SELL, 240.12, 100);
        Order order2 = new Order("#2", "user1", LocalTime.parse("09:46"), "Meesho",
                OrderType.SELL, 237.45, 90);
        Order order3 = new Order("#3", "user2", LocalTime.parse("09:47"), "Meesho",
                OrderType.BUY, 238.10, 110);
        Order order4 = new Order("#4", "user2", LocalTime.parse("09:48"), "Meesho",
                OrderType.BUY, 237.80, 10);
        Order order5 = new Order("#5", "user2", LocalTime.parse("09:49"), "Meesho",
                OrderType.BUY, 237.80, 40);
        Order order6 = new Order("#6", "user1", LocalTime.parse("09:50"), "Meesho",
                OrderType.SELL, 236.00, 50);

        exchange.placeOrder(order1);
        exchange.placeOrder(order2);
        exchange.placeOrder(order3);
        exchange.placeOrder(order4);
        exchange.placeOrder(order5);
        exchange.placeOrder(order6);

        for (Trade trade : exchange.getStockTrades("Meesho")) {
            System.out.println(trade);
        }
    }
}
