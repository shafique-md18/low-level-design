package splitwise.manager;

import splitwise.model.*;
import splitwise.validator.SplitValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ExpenseManager {
    private final GroupManager groupManager;

    public ExpenseManager(GroupManager groupManager) {
        this.groupManager = groupManager;
    }

    public Expense createExpense(String groupId, String description, User paidBy, BigDecimal amount,
                                 SplitType splitType, List<Split> splits) {
        Group group = groupManager.getGroup(groupId);
        if (group == null) {
            throw new IllegalArgumentException("Group not found");
        }
        Expense expense = new Expense(UUID.randomUUID().toString(), description, amount, paidBy, splitType, splits);
        SplitValidator.validateSplits(expense, splits);
        group.addExpense(expense);
        updateBalances(group.getBalanceSheet(), expense);
        return expense;
    }

    private void updateBalances(BalanceSheet balanceSheet, Expense expense) {
        balanceSheet.updateBalance(expense.getPaidBy(), expense.getAmount());

        for (Split split : expense.getSplits()) {
            balanceSheet.updateBalance(split.getUser(), split.getAmount().negate());
        }
    }
}
