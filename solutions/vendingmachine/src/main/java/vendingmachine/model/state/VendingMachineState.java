package vendingmachine.model.state;

public interface VendingMachineState {
    void insertMoney(double currentInsertedAmount);
    void selectProduct(String productCode);
    void dispenseProduct();
    void dispenseChange();
    void cancelTransaction();
}
