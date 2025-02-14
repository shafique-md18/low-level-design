package stockexchange;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class OrderBook {
    private final ConcurrentSkipListMap<Double, PriorityBlockingQueue<Order>> buyOrders;
    private final ConcurrentSkipListMap<Double, PriorityBlockingQueue<Order>> sellOrders;
    private final List<Trade> trades;
    private final ConcurrentHashMap<String, Order> orderMap;
    private final ReentrantLock matchOrdersLock;

    public OrderBook() {
        this.buyOrders = new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        this.sellOrders = new ConcurrentSkipListMap<>();

        this.trades = Collections.synchronizedList(new ArrayList<>());
        this.orderMap = new ConcurrentHashMap<>();
        matchOrdersLock = new ReentrantLock(true);
    }

    public void addOrder(Order order) {
        orderMap.put(order.getOrderId(), order);

        if (order.getType() == OrderType.BUY) {
            buyOrders.computeIfAbsent(order.getPrice(), _ -> createOrderQueue()).offer(order);
        } else {
            sellOrders.computeIfAbsent(order.getPrice(), _ -> createOrderQueue()).offer(order);
        }

        // Only match orders if there is a possibility of a match
        if (!buyOrders.isEmpty() && !sellOrders.isEmpty() &&
                buyOrders.firstKey() >= sellOrders.firstKey()) {
            matchOrders();
        }
    }


    private void matchOrders() {
        if (!matchOrdersLock.tryLock()) {
            return; // If another thread is already matching, skip execution
        }
        try {
            while (!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
                Map.Entry<Double, PriorityBlockingQueue<Order>> highestBuy = buyOrders.firstEntry();
                Map.Entry<Double, PriorityBlockingQueue<Order>> lowestSell = sellOrders.firstEntry();

                if (highestBuy.getKey() < lowestSell.getKey()) {
                    break; // No matching possible
                }

                PriorityBlockingQueue<Order> buyQueue = highestBuy.getValue();
                PriorityBlockingQueue<Order> sellQueue = lowestSell.getValue();

                Order buyOrder = buyQueue.peek();
                Order sellOrder = sellQueue.peek();

                int matchedQuantity = Math.min(buyOrder.getRemainingQuantity(), sellOrder.getRemainingQuantity());

                // Record trade
                Trade trade = new Trade(buyOrder.getOrderId(), sellOrder.getOrderId(),
                        sellOrder.getPrice(), matchedQuantity);
                trades.add(trade);

                // Reduce order quantities
                buyOrder.setRemainingQuantity(buyOrder.getRemainingQuantity() - matchedQuantity);
                sellOrder.setRemainingQuantity(sellOrder.getRemainingQuantity() - matchedQuantity);

                // Remove completed orders
                if (buyOrder.getRemainingQuantity() == 0) {
                    buyQueue.poll();
                }
                if (sellOrder.getRemainingQuantity() == 0) {
                    sellQueue.poll();
                }

                // Remove empty queues after polling
                if (buyQueue.isEmpty()) {
                    buyOrders.remove(highestBuy.getKey());
                }
                if (sellQueue.isEmpty()) {
                    sellOrders.remove(lowestSell.getKey());
                }
            }
        } finally {
            matchOrdersLock.unlock();
        }
    }


    public void cancelOrder(String orderId) {
        Order order = orderMap.get(orderId);
        if (order != null) {
            if (order.getType() == OrderType.BUY) {
                buyOrders.remove(order);
            } else {
                sellOrders.remove(order);
            }
            orderMap.remove(orderId);
        }
    }

    public void editOrder(String orderId, double newPrice, int newQuantity) {
        cancelOrder(orderId);
        Order order = orderMap.get(orderId);
        if (order != null) {
            order.updatePrice(newPrice);
            order.updateQuantity(newQuantity);
            addOrder(order);
        }
    }

    public List<Trade> getTradesSnapshot() {
        // Return a copy to avoid modification issues
        return new ArrayList<>(trades);
    }

    private PriorityBlockingQueue<Order> createOrderQueue() {
        return new PriorityBlockingQueue<>(11, Comparator.comparing(Order::getTime));
    }
}
