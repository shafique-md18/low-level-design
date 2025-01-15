package cricbuzz.model;

import java.util.Map;

public class Team {
    private final String id;
    private final String name;
    private final Map<String, Player> playingEleven;

    private Team(Builder builder) {
        id = builder.id;
        name = builder.name;
        playingEleven = builder.playingEleven;
    }


    public static final class Builder {
        private String id;
        private String name;
        private Map<String, Player> playingEleven;

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

        public Builder withPlayingEleven(Map<String, Player> playingEleven) {
            this.playingEleven = playingEleven;
            return this;
        }

        public Team build() {
            return new Team(this);
        }
    }
}
