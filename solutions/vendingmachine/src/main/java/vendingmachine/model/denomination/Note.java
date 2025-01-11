package vendingmachine.model.denomination;

public enum Note {
    TEN(10.0),
    TWENTY(20.0),
    FIFTY(50.0),
    HUNDRED(100.0);

    private final double value;
    Note(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
