package amazon.model;

import java.util.concurrent.locks.ReadWriteLock;

public class Product {
    private final String id;
    private String name;
    private String description;
    private double price;
    /*
    Issues with this approach (having quantity associated with product):

        1. Violates Single Responsibility Principle - Product class should only be concerned with product-specific attributes
        2. Makes it harder to track quantity changes and history
        3. Concurrency issues when multiple services try to update quantity
        4. Difficult to implement warehouse/location-specific inventory
        5. Complicates inventory auditing and reconciliation
     */
    private int availableQuantity;

    private Product(Builder builder) {
        id = builder.id;
        name = builder.name;
        description = builder.description;
        price = builder.price;
        availableQuantity = builder.availableQuantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public static final class Builder {
        private String id;
        private String name;
        private String description;
        private double price;
        private int availableQuantity;

        public Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder withAvailableQuantity(int availableQuantity) {
            this.availableQuantity = availableQuantity;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
