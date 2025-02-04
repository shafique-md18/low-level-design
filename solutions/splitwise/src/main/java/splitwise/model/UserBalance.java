package splitwise.model;

import java.math.BigDecimal;

public class UserBalance {
    private final User user;
    private BigDecimal amount;

    public UserBalance(User user) {
        this.user = user;
        this.amount = BigDecimal.ZERO;
    }

    public User getUser() {
        return user;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void addAmount(BigDecimal delta) {
        this.amount = this.amount.add(delta);
    }
}
