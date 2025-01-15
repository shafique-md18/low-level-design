package cricbuzz.model;

public class Ball {
    private final String id;
    private final int ballNumber;
    private final Player batsman;
    private final BallType ballType;
    private final RunType runType;

    private Ball(Builder builder) {
        id = builder.id;
        ballNumber = builder.ballNumber;
        batsman = builder.batsman;
        ballType = builder.ballType;
        runType = builder.runType;
    }

    public String getId() {
        return id;
    }

    public int getBallNumber() {
        return ballNumber;
    }

    public Player getBatsman() {
        return batsman;
    }

    public BallType getBallType() {
        return ballType;
    }

    public RunType getRunType() {
        return runType;
    }

    public static final class Builder {
        private String id;
        private int ballNumber;
        private Player batsman;
        private BallType ballType;
        private RunType runType;

        public Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withBallNumber(int ballNumber) {
            this.ballNumber = ballNumber;
            return this;
        }

        public Builder withBatsman(Player batsman) {
            this.batsman = batsman;
            return this;
        }

        public Builder withBallType(BallType ballType) {
            this.ballType = ballType;
            return this;
        }

        public Builder withRunType(RunType runType) {
            this.runType = runType;
            return this;
        }

        public Ball build() {
            return new Ball(this);
        }
    }
}
