package vendingmachine.model.denomination;

public abstract class Denomination {
    private final double denominationValue;

    public Denomination(double value) {
        this.denominationValue = value;
    }

    public double getDenominationValue() {
        return denominationValue;
    }
}
