package cricbuzz.model;

public enum BallType {
    VALID(0),
    DISMISSAL(0);

    private int extraRuns;
    BallType(int extraRuns) {
        this.extraRuns = extraRuns;
    }

    public int getExtraRuns() {
        return extraRuns;
    }
}
