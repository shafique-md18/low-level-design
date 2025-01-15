package amazon.model;

public class Customer {
    private final String id;
    private String name;
    private String address;
    private String phoneNumber;

    private Customer(Builder builder) {
        id = builder.id;
        name = builder.name;
        address = builder.address;
        phoneNumber = builder.phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public static final class Builder {
        private String id;
        private String name;
        private String address;
        private String phoneNumber;

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

        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}
