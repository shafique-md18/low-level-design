package splitwise.validator;

import splitwise.constants.Constants;
import splitwise.model.*;

import java.math.BigDecimal;
import java.util.List;

public class SplitValidator {
    public static void validateSplits(Expense expense, List<Split> splits) {
        if (splits == null || splits.isEmpty()) {
            throw new IllegalArgumentException("Splits cannot be null or empty.");
        }

        if (splits.getFirst() instanceof EqualSplit) {
            validateEqualSplit(expense, splits);
        } else if (splits.getFirst() instanceof ExactSplit) {
            validateExactSplit(expense, splits);
        } else if (splits.getFirst() instanceof PercentageSplit) {
            validatePercentageSplit(expense, splits);
        }

        validateTotalAmount(expense, splits);
    }

    private static void validateTotalAmount(Expense expense, List<Split> splits) {
        BigDecimal totalSplitAmount = BigDecimal.ZERO;
        for (Split split : splits) {
            totalSplitAmount = totalSplitAmount.add(split.getAmount().abs());
        }
        // The difference between the expected and actual must not be greater than epsilon
        if (totalSplitAmount.subtract(expense.getAmount()).abs().compareTo(Constants.EPSILON) > 0) {
            throw new IllegalArgumentException("Expense total amount must be equal to total split amounts");
        }
    }

    private static void validatePercentageSplit(Expense expense, List<Split> splits) {
        BigDecimal totalPercentage = BigDecimal.ZERO;
        for (Split split : splits) {
            totalPercentage = totalPercentage.add(((PercentageSplit) split).getPercentage());
        }
        if (!(totalPercentage.compareTo(new BigDecimal(100)) == 0)) {
            throw new IllegalArgumentException("Total split percentage must be equal to 100");
        }
    }

    private static void validateExactSplit(Expense expense, List<Split> splits) {
        if (!splits.stream().allMatch(split -> split instanceof ExactSplit)) {
            throw new IllegalArgumentException("All splits must be exact splits");
        }
    }

    private static void validateEqualSplit(Expense expense, List<Split> splits) {
        BigDecimal previousSplit = splits.getFirst().getAmount();
        for (Split split : splits) {
            if (previousSplit.subtract(split.getAmount()).abs().compareTo(Constants.EPSILON) > 0) {
                throw new IllegalArgumentException("All splits must be equal in equal split");
            }
        }
    }
}
