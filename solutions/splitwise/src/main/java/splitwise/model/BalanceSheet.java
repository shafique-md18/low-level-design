package splitwise.model;

import java.math.BigDecimal;
import java.util.*;

public class BalanceSheet {
    private Map<String, UserBalance> userBalances;
    private final BigDecimal EPSILON = new BigDecimal("0.01");

    BalanceSheet() {
        this.userBalances = new HashMap<>();
    }

    public void updateBalance(User user, BigDecimal amount) {
        userBalances.computeIfAbsent(user.getId(), _ -> new UserBalance(user)).addAmount(amount);
    }

    public Map<String, UserBalance> getUserBalances() {
        return userBalances;
    }

    public void printBalanceSheet() {
        for (UserBalance userBalance : userBalances.values()) {
            System.out.println(userBalance.getUser().getName() +
                    (userBalance.getAmount().doubleValue() > 0.0 ? " is owed " : " owes ") + userBalance.getAmount());
        }
    }

    public List<Transaction> settle() {
        // Minimum number of transactions to settle all debts
        List<Transaction> transactions = new ArrayList<>();

        // Max heap for both creditors and debtors
        PriorityQueue<UserBalance> creditors = new PriorityQueue<>(
                (a, b) -> b.getAmount().compareTo(a.getAmount())
        );
        PriorityQueue<UserBalance> debtors = new PriorityQueue<>(
                (a, b) -> b.getAmount().compareTo(a.getAmount())
        );

        // Fill the heaps
        for (UserBalance userBalance : userBalances.values()) {
            // EPSILON for better comparison -> To prevent unnecessary micro transactions
            if (userBalance.getAmount().compareTo(EPSILON) > 0) {
                creditors.offer(userBalance);
            } else {
                debtors.offer(userBalance);
            }
        }

        // Keep settling until either all debts are paid or all credits are received
        while (!debtors.isEmpty() && !creditors.isEmpty()) {
            UserBalance creditor = creditors.poll();
            UserBalance debtor = debtors.poll();

            // Find the amount that can be settled -> minimum of creditor or debtor
            BigDecimal settleAmount = debtor.getAmount().abs().min(creditor.getAmount());

            // Avoid micro transactions
            if (settleAmount.compareTo(EPSILON) > 0) {
                // Create new transaction
                Transaction transaction = new Transaction(debtor.getUser(), creditor.getUser(), settleAmount);

                // Update balances
                debtor.addAmount(settleAmount); // Reduce debt
                creditor.addAmount(settleAmount.negate()); // Reduce credit

                // If any remaining balance, put back in heap
                if (debtor.getAmount().abs().compareTo(EPSILON) > 0) {
                    debtors.offer(debtor);
                }
                if (creditor.getAmount().compareTo(EPSILON) > 0) {
                    creditors.offer(creditor);
                }

                transactions.add(transaction);
            }
        }

        return transactions;
    }
}
