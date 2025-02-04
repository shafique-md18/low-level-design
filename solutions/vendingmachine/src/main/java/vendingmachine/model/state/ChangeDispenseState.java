package vendingmachine.model.state;

import vendingmachine.service.VendingMachineService;

public class ChangeDispenseState implements VendingMachineState {
    private final VendingMachineService machine;

    public ChangeDispenseState(VendingMachineService machine) {
        this.machine = machine;
    }

    @Override
    public void insertMoney(double currentInsertedAmount) {
        throw new IllegalStateException("Operation not allowed in this state");
    }

    @Override
    public void selectProduct(String productCode) {
        throw new IllegalStateException("Operation not allowed in this state");
    }

    @Override
    public void dispenseProduct() {
        throw new IllegalStateException("Operation not allowed in this state");
    }

    // TODO: Once the transaction is complete, we can save the transaction by using a transaction service
    @Override
    public void dispenseChange() {
        double changeToDispense = getChangeToDispense();
        if (changeToDispense < 0) {
            throw new IllegalStateException("Change to dispense cannot be negative");
        } else if (changeToDispense == 0) {
            System.out.println("No change required to dispense");
        } else if (hasSufficientFundsToDispenseChange(changeToDispense)) {
            System.out.println("Dispensing change - " + changeToDispense);
        } else {
            System.out.println("Insufficient funds to dispense change for the product");
            System.out.println("Returning the inserted amount - " + this.machine.getAmountInserted());
            this.machine.setSelectedProductCode(null);
            System.out.println("Cancelled transaction, please try again.");
        }
        this.machine.setBalance(this.machine.getBalance() + this.machine.getAmountInserted() - changeToDispense);
        this.machine.setAmountInserted(0);
        this.machine.setState(new IdleState(this.machine));
    }

    // TODO: Maybe, we can give option to give change as charity
    @Override
    public void cancelTransaction() {
        if (machine.getAmountInserted() != 0) {
            machine.dispenseChange();
        }
    }

    private boolean hasSufficientFundsToDispenseChange(double changeToDispense) {
        return this.machine.getBalance() + this.machine.getAmountInserted() >= changeToDispense;
    }

    private double getChangeToDispense() {
        double amountInserted = this.machine.getAmountInserted();
        double productPrice = 0.0;
        if (this.machine.getSelectedProductCode() != null) {
            productPrice = this.machine.getSelectedProductPrice();
        }
        return amountInserted - productPrice;
    }
}
