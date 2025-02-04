package splitwise.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Expense {
    private final String id;
    private String description;
    private BigDecimal amount;
    private User paidBy;
    private SplitType splitType;
    private List<Split> splits;
    private long timestamp;

    public Expense(String id, String description, BigDecimal amount, User paidBy, SplitType splitType, List<Split> splits) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.paidBy = paidBy;
        this.splitType = splitType;
        this.splits = new ArrayList<>(splits);
        this.timestamp = System.currentTimeMillis();
    }

    public User getPaidBy() {
        return paidBy;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public List<Split> getSplits() {
        return splits;
    }

    public SplitType getSplitType() {
        return splitType;
    }
}
