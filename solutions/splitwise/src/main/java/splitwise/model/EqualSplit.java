package splitwise.model;

import java.math.BigDecimal;

public class EqualSplit extends Split {
    public EqualSplit(User user, BigDecimal amount) {
        super(user, amount);
    }
}
