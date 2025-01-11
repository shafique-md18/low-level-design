package vendingmachine.model.state;

import vendingmachine.service.VendingMachineService;

public class IdleState implements VendingMachineState {
    private final VendingMachineService machine;

    public IdleState(VendingMachineService machine) {
        System.out.println("Ready for dispensing products!\n");
        this.machine = machine;
    }

    @Override
    public void insertMoney(double currentAmountInserted) {
        System.out.println("Inserting amount - " + currentAmountInserted);
        double totalAmount = machine.getAmountInserted() + currentAmountInserted;
        machine.setAmountInserted(totalAmount);
    }

    @Override
    public void selectProduct(String productCode) {
        machine.setState(new ProductSelectionState(this.machine));
        machine.selectProduct(productCode);
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
        System.out.println("Cancelling transaction.");
        if (machine.getAmountInserted() != 0) {
            machine.setState(new ChangeDispenseState(machine));
            machine.dispenseChange();
        }
    }
}
