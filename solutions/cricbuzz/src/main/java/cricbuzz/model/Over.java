package cricbuzz.model;

import java.util.List;

public class Over {
    private final String id;
    private final int overNumber;
    private final Player bowler;
    private List<Ball> balls;
    private static final int MAX_VALID_DELIVERIES_PER_OVER = 6;

    private Over(Builder builder) {
        id = builder.id;
        overNumber = builder.overNumber;
        bowler = builder.bowler;
        balls = builder.balls;
    }

    public void addBall(Ball ball) {
        this.balls.add(ball);
    }

    public boolean isComplete() {
        int validDeliveries = 0;
        for (Ball ball : balls) {
            if (BallType.VALID.equals(ball.getBallType())) {
                validDeliveries++;
            }
        }
        return validDeliveries == MAX_VALID_DELIVERIES_PER_OVER;
    }

    public String getId() {
        return id;
    }

    public int getOverNumber() {
        return overNumber;
    }

    public Player getBowler() {
        return bowler;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public static final class Builder {
        private String id;
        private int overNumber;
        private Player bowler;
        private List<Ball> balls;

        public Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withOverNumber(int overNumber) {
            this.overNumber = overNumber;
            return this;
        }

        public Builder withBowler(Player bowler) {
            this.bowler = bowler;
            return this;
        }

        public Builder withBalls(List<Ball> balls) {
            this.balls = balls;
            return this;
        }

        public Over build() {
            return new Over(this);
        }
    }
}
