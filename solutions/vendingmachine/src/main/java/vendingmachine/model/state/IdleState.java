package vendingmachine.model.state;

import vendingmachine.service.VendingMachineService;
import vendingmachine.model.denomination.Denomination;

public class IdleState implements VendingMachineState {
    private final VendingMachineService machine;

    public IdleState(VendingMachineService machine) {
        System.out.println("Ready for dispensing products!\n");
        this.machine = machine;
    }

    @Override
    public void insertMoney(Denomination denomination) {
        double amount = machine.getAmountInserted() + denomination.getDenominationValue();
        System.out.println("Inserting amount - " + denomination.getDenominationValue());
        System.out.println("Total inserted amount - " + amount);
        machine.setAmountInserted(amount);
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
