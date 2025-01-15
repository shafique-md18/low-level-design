package cricbuzz.model;

import java.util.HashMap;
import java.util.Map;

public class Scorecard {
    private final String id;
    private double overs;
    private int wickets;
    private int totalScore;
    private Map<String, Integer> playerBattingScore;

    private Scorecard(Builder builder) {
        id = builder.id;
        overs = builder.overs;
        wickets = builder.wickets;
        totalScore = builder.totalScore;
        playerBattingScore = builder.playerBattingScore;
    }

    public void updateScorecard(Ball ball) {
        if (BallType.DISMISSAL.equals(ball.getBallType())) {
            wickets++;
        }
        int ballScore = ball.getRunType().getRuns() + ball.getBallType().getExtraRuns();
        String playerId = ball.getBatsman().getId();
        if (!playerBattingScore.containsKey(playerId)) {
            playerBattingScore.put(playerId, 0);
        }
        playerBattingScore.put(playerId,  playerBattingScore.get(playerId) + ballScore);
        totalScore += ballScore;
    }

    public void setOvers(double overs) {
        this.overs = overs;
    }

    public void displayScorecard() {
        System.out.printf("Runs = %s/%s Overs = %s%n", totalScore, wickets, overs);
    }

    public static final class Builder {
        private String id;
        private double overs = 0.0;
        private int wickets = 0;
        private int totalScore = 0;
        private Map<String, Integer> playerBattingScore = new HashMap<>();

        public Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withOvers(double overs) {
            this.overs = overs;
            return this;
        }

        public Builder withWickets(int wickets) {
            this.wickets = wickets;
            return this;
        }

        public Builder withTotalScore(int totalScore) {
            this.totalScore = totalScore;
            return this;
        }

        public Builder withPlayerBattingScore(Map<String, Integer> playerBattingScore) {
            this.playerBattingScore = playerBattingScore;
            return this;
        }

        public Scorecard build() {
            return new Scorecard(this);
        }
    }
}
