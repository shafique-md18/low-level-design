package splitwise.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Group {
    private final String id;
    private String name;
    private String description;
    private List<Expense> expenses;
    private Set<User> members;
    private BalanceSheet balanceSheet;

    public Group(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.expenses = new ArrayList<>();
        this.members = new HashSet<>();
        this.balanceSheet = new BalanceSheet();
    }

    public void addExpense(Expense expense) {
        this.expenses.add(expense);
    }

    public void addMember(List<User> users) {
        this.members.addAll(users);
    }

    public BalanceSheet getBalanceSheet() {
        return this.balanceSheet;
    }

    public String getId() {
        return id;
    }
}
