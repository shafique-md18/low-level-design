package splitwise;

import splitwise.manager.ExpenseManager;
import splitwise.manager.GroupManager;
import splitwise.manager.UserManager;
import splitwise.model.*;
import vendingmachine.model.denomination.Coin;
import vendingmachine.model.denomination.Note;
import vendingmachine.service.VendingMachineService;
import vendingmachine.repository.InMemoryInventoryRepository;
import vendingmachine.repository.InventoryRepository;
import vendingmachine.service.InventoryService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class SplitwiseDemo {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        GroupManager groupManager = new GroupManager();
        ExpenseManager expenseManager = new ExpenseManager(groupManager);

        User ajay = userManager.addUser("Ajay", "ajay@xyz.com");
        User amit = userManager.addUser("Amit", "amit@xyz.com");
        User anwar = userManager.addUser("Anwar", "anwar@xyz.com");

        Group group = groupManager.addGroup("Chai Sutta", "Matargashti");
        group.addMember(Arrays.asList(ajay, amit, anwar));

        List<Split> teaSplits = Arrays.asList(
                new EqualSplit(ajay, new BigDecimal("5")),
                new EqualSplit(amit, new BigDecimal("5")),
                new EqualSplit(anwar, new BigDecimal("5"))
        );

        expenseManager.createExpense(group.getId(), "chai", ajay, BigDecimal.valueOf(15),
                SplitType.EQUAL, teaSplits);

        List<Split> biryaniSplits = Arrays.asList(
                new PercentageSplit(ajay, BigDecimal.valueOf(1500), new BigDecimal("33.33")),
                new PercentageSplit(amit, BigDecimal.valueOf(1500), new BigDecimal("33.33")),
                new PercentageSplit(anwar, BigDecimal.valueOf(1500), new BigDecimal("33.34"))
        );

        expenseManager.createExpense(group.getId(), "biryani", anwar, BigDecimal.valueOf(1500),
                SplitType.PERCENTAGE, biryaniSplits);

        for (Transaction transaction : group.getBalanceSheet().settle()) {
            System.out.println(transaction);
        }
    }
}
