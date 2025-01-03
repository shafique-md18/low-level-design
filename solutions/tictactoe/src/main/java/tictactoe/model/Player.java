package tictactoe.model;

import java.util.UUID;

public class Player {
    private String id;
    private String name;
    private boolean isActive;

    private Player(Builder builder) {
        this.id = builder.id;
        this.isActive = builder.isActive;
        this.name = builder.name;
    }

    public String getId() {
        return id;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private String name;
        private boolean isActive = false;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withActive(boolean active) {
            isActive = active;
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
