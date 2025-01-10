package vendingmachine.model.state;

import vendingmachine.model.denomination.Denomination;

public interface VendingMachineState {
    void insertMoney(Denomination denomination);
    void selectProduct(String productCode);
    void dispenseProduct();
    void dispenseChange();
    void cancelTransaction();
}
