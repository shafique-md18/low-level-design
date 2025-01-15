package amazon.model;

public class Product {
    private final String id;
    private String name;
    private String description;
    private ProductCategory category;

    private Product(Builder builder) {
        id = builder.id;
        name = builder.name;
        description = builder.description;
        category = builder.category;
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

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public static final class Builder {
        private String id;
        private String name;
        private String description;
        private ProductCategory category;

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

        public Builder withCategory(ProductCategory category) {
            this.category = category;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
