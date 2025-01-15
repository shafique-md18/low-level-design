package amazon.model;

public class CartItem {
    private final Product product;
    private int quantity;

    private CartItem(Builder builder) {
        product = builder.product;
        quantity = builder.quantity;
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

    public static final class Builder {
        private Product product;
        private int quantity = 1;

        public Builder() {
        }

        public Builder withProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public CartItem build() {
            return new CartItem(this);
        }
    }
}
