package vendingmachine.model.denomination;

public enum Coin {
    FIVE(5.0),
    TEN(10.0);

    Coin(double value) {
        this.value = value;
    }

    private final double value;

    public double getValue() {
        return value;
    }
}
