package amazon.model;

public class Item {
    private final String id;
    private final Product product;
    private int quantity;
    private double pricePerUnit;

    private Item(Builder builder) {
        id = builder.id;
        product = builder.product;
        quantity = builder.quantity;
        pricePerUnit = builder.pricePerUnit;
    }

    public String getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public static final class Builder {
        private String id;
        private Product product;
        private int quantity = 1;
        private double pricePerUnit;

        public Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder withPricePerUnit(double pricePerUnit) {
            this.pricePerUnit = pricePerUnit;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }
}
