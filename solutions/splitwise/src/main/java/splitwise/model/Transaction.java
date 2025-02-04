package splitwise.model;

import java.math.BigDecimal;

public class Transaction {
    private final User fromUser;
    private final User toUser;
    private final BigDecimal amount;

    public Transaction(User fromUser, User toUser, BigDecimal amount) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format("%s owes %s: %.2f", fromUser.getName(), toUser.getName(), amount);
    }
}
