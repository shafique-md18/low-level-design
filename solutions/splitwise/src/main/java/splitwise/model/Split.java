package splitwise.model;

import java.math.BigDecimal;

public class Split {
    private final User user;
    private final BigDecimal amount;

    public Split(User user, BigDecimal amount) {
        this.user = user;
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
