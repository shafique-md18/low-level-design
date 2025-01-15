package cricbuzz.model;

import java.util.List;

public class Match {
    private final String id;
    private List<Team> teams;
    private List<Inning> innings;
    private MatchStatus status;

    private Match(Builder builder) {
        id = builder.id;
        teams = builder.teams;
        innings = builder.innings;
        status = builder.status;
    }

    public void addInning(Inning inning) {
        innings.add(inning);
    }

    public void updateMatchStatus(MatchStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public static final class Builder {
        private String id;
        private List<Team> teams;
        private List<Inning> innings;
        private MatchStatus status;

        public Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withTeams(List<Team> teams) {
            this.teams = teams;
            return this;
        }

        public Builder withInnings(List<Inning> innings) {
            this.innings = innings;
            return this;
        }

        public Builder withStatus(MatchStatus status) {
            this.status = status;
            return this;
        }

        public Match build() {
            return new Match(this);
        }
    }
}
