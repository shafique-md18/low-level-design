package cricbuzz.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inning {
    private final String id;
    private final InningType type;
    private List<Over> overs;
    // TODO: This can be extended to use MatchFormat
    private static final int INNING_MAX_OVERS = 2;
    private final Team battingTeam;
    private final Team bowlingTeam;
    private final Scorecard scorecard;
    private Player strikerBatsman;
    private Player nonStrikerBatsman;
    private Player bowler;

    private Inning(Builder builder) {
        strikerBatsman = builder.strikerBatsman;
        id = builder.id;
        type = builder.type;
        overs = builder.overs;
        battingTeam = builder.battingTeam;
        bowlingTeam = builder.bowlingTeam;
        scorecard = builder.scorecard;
        nonStrikerBatsman = builder.nonStrikerBatsman;
        bowler = builder.bowler;
    }

    public void bowlBall(Ball ball) {
        validateDelivery(ball);
        getCurrentOver().addBall(ball);

        scorecard.updateScorecard(ball);
    }

    public void addOver(Over over) {
        validateOver(over);
        overs.add(over);
        for (Ball ball : over.getBalls()) {
            scorecard.updateScorecard(ball);
        }
    }

    private void validateDelivery(Ball ball) {
        // pass
    }

    private void validateOver(Over over) {
        // pass
    }

    public void setBowler(Player bowler) {
        this.bowler = bowler;
    }

    public void setNonStrikerBatsman(Player nonStrikerBatsman) {
        this.nonStrikerBatsman = nonStrikerBatsman;
    }

    public void setStrikerBatsman(Player strikerBatsman) {
        this.strikerBatsman = strikerBatsman;
    }

    public void rotateStrike() {
        Player temp = strikerBatsman;
        this.strikerBatsman = nonStrikerBatsman;
        this.nonStrikerBatsman = temp;
    }

    private Over getCurrentOver() {
        if (overs.isEmpty()) {
            throw new IllegalStateException("Inning not started");
        }
        return this.overs.get(overs.size() - 1);
    }

    public boolean isInningComplete() {
        if (overs.size() < INNING_MAX_OVERS) {
            return false;
        }
        return getCurrentOver().isComplete();
    }

    public Scorecard getScorecard() {
        return scorecard;
    }

    public static final class Builder {
        private Player strikerBatsman;
        private String id;
        private InningType type;
        private List<Over> overs = new ArrayList<>();
        private Team battingTeam;
        private Team bowlingTeam;
        private Scorecard scorecard = new Scorecard.Builder().build();
        private Player nonStrikerBatsman;
        private Player bowler;

        public Builder() {
        }

        public Builder withStrikerBatsman(Player strikerBatsman) {
            this.strikerBatsman = strikerBatsman;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withType(InningType type) {
            this.type = type;
            return this;
        }

        public Builder withOvers(List<Over> overs) {
            this.overs = overs;
            return this;
        }

        public Builder withBattingTeam(Team battingTeam) {
            this.battingTeam = battingTeam;
            return this;
        }

        public Builder withBowlingTeam(Team bowlingTeam) {
            this.bowlingTeam = bowlingTeam;
            return this;
        }

        public Builder withScorecard(Scorecard scorecard) {
            this.scorecard = scorecard;
            return this;
        }

        public Builder withNonStrikerBatsman(Player nonStrikerBatsman) {
            this.nonStrikerBatsman = nonStrikerBatsman;
            return this;
        }

        public Builder withBowler(Player bowler) {
            this.bowler = bowler;
            return this;
        }

        public Inning build() {
            return new Inning(this);
        }
    }
}
