## Amazon

### Requirements

1. The online shopping service should allow users to browse products, add them to the shopping cart, and place orders.
2. The system should support multiple product categories and provide search functionality.
3. Users should be able to manage their profiles, view order history, and track order status.
4. The system should handle inventory management and update product availability accordingly.
5. The system should support multiple payment methods and ensure secure transactions.
6. The system should handle concurrent user requests and ensure data consistency.
7. The system should be scalable to handle a large number of products and users.
8. The system should provide a user-friendly interface for a seamless shopping experience.


### Class diagram

### Other considerations
1. For simplicity in the first iteration, put product available quantity and price in product itself.
2. For all order state management happens in order itself. -> Later, we can use State Design Pattern. stockexchange.Order will contain this state and manage it.
    ```java
    import stockexchange.Order;public enum OrderStatus {
        CREATED, PAID, SHIPPED, DELIVERED, CANCELLED
    }
    
    // State interface
    public interface OrderState {
    void processPayment(Order order);
    void ship(Order order);
    void deliver(Order order);
    void cancel(Order order);
    }
   ```
3. We can use Observer design pattern for order notifications.
4. We do not have any id for CartItem because it is just a map of product and quantities purchased.
5. As we are managing the available product quantities in Product itself, it is easier to handle concurrency using read write locks.
6. Instead of a generic stockexchange.User interface, using the Customer concrete class for simplicity.