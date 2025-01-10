package vendingmachine.model.state;

import vendingmachine.service.VendingMachineService;
import vendingmachine.model.denomination.Denomination;
import vendingmachine.model.inventory.Item;

public class ProductDispenseState implements VendingMachineState {
    private final VendingMachineService machine;

    public ProductDispenseState(VendingMachineService machine) {
        this.machine = machine;
    }

    @Override
    public void insertMoney(Denomination denomination) {
        throw new IllegalStateException("Operation not allowed in this state");
    }

    @Override
    public void selectProduct(String productCode) {
        throw new IllegalStateException("Operation not allowed in this state");
    }

    @Override
    public void dispenseProduct() {
        System.out.println("Dispensing product - " + this.machine.getSelectedProductCode());
        Item item = this.machine.getInventoryService().getItem(this.machine.getSelectedProductCode()).get();
        if (item.getItemPrice() > this.machine.getAmountInserted()) {
            System.out.println("Insufficient funds to dispense selected product. Please try again.");
            this.machine.clearSelectedProductCode();
            this.machine.setState(new IdleState(this.machine));
        } else {
            System.out.println("Dispensing item - " + item.getItemCode());
            this.machine.getInventoryService().dispenseItem(item.getItemCode());
            this.machine.setState(new ChangeDispenseState(this.machine));
            this.machine.dispenseChange();
        }
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
