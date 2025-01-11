package vendingmachine.model.state;

import vendingmachine.service.VendingMachineService;

public class ProductSelectionState implements VendingMachineState {
    private final VendingMachineService machine;

    public ProductSelectionState(VendingMachineService machine) {
        this.machine = machine;
    }

    @Override
    public void insertMoney(double currentInsertedAmount) {
        throw new IllegalStateException("Operation not allowed in this state");
    }

    @Override
    public void selectProduct(String productCode) {
        System.out.println("Selecting product - " + productCode);
        if (this.machine.getInventoryService().isItemAvailable(productCode)) {
            this.machine.setSelectedProductCode(productCode);
            this.machine.setState(new ProductDispenseState(this.machine));
        } else {
            System.out.println("Selected product code is either invalid or out of stock. Please try again.");
            this.machine.setState(new IdleState(this.machine));
        }
    }

    @Override
    public void dispenseProduct() {
        throw new IllegalStateException("Operation not allowed in this state");
    }

    @Override
    public void dispenseChange() {
        throw new IllegalStateException("Operation not allowed in this state");
    }

    @Override
    public void cancelTransaction() {
        if (machine.getAmountInserted() != 0) {
            machine.setState(new ChangeDispenseState(machine));
            machine.dispenseChange();
        }
    }
}
