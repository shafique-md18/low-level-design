package splitwise.model;

import splitwise.constants.Constants;

import java.math.BigDecimal;

public class PercentageSplit extends Split {
    private final BigDecimal percentage;

    public PercentageSplit(User user, BigDecimal totalAmount, BigDecimal percentage) {
        super(user, totalAmount
                .multiply(percentage)
                .divide(new BigDecimal("100"), Constants.ROUNDING_SCALE, Constants.ROUNDING_MODE));
        if (percentage.compareTo(BigDecimal.ZERO) < 0 ||
                percentage.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        this.percentage = percentage;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }
}
