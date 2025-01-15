package cricbuzz.model;

public class Player {
    private final String id;
    private final String name;

    private Player(Builder builder) {
        id = builder.id;
        name = builder.name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static final class Builder {
        private String id;
        private String name;

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

        public Player build() {
            return new Player(this);
        }
    }
}
