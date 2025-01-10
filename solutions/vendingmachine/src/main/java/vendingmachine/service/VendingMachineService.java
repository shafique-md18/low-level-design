package vendingmachine.service;

import vendingmachine.model.denomination.Denomination;
import vendingmachine.model.exception.ItemNotExistsException;
import vendingmachine.model.inventory.Item;
import vendingmachine.model.state.IdleState;
import vendingmachine.model.state.VendingMachineState;

import java.util.Optional;

public class VendingMachineService {
    private final InventoryService inventoryService;
    private VendingMachineState state;
    private double balance;
    private double amountInserted;
    private String selectedProductCode;

    public VendingMachineService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        this.state = new IdleState(this);
        this.amountInserted = 0.0;
    }

    public void insertMoney(Denomination denomination) {
        state.insertMoney(denomination);
    }

    public void selectProduct(String productCode) {
        state.selectProduct(productCode);
    }

    public void dispenseProduct() {
        state.dispenseProduct();
    }

    public void dispenseChange() {
        state.dispenseChange();
    }

    public void cancelTransaction() {
        state.cancelTransaction();
    }

    public double getSelectedProductPrice() {
        if (selectedProductCode == null) {
            throw new IllegalStateException("No product selected");
        }
        Optional<Item> item =  this.inventoryService.getItem(selectedProductCode);
        if (item.isPresent()) {
            return item.get().getItemPrice();
        }
        throw new ItemNotExistsException("Selected product does not exist");
    }

    public InventoryService getInventoryService() {
        return inventoryService;
    }

    public void setState(VendingMachineState state) {
        this.state = state;
    }

    public double getAmountInserted() {
        return amountInserted;
    }

    public void setAmountInserted(double amountInserted) {
        this.amountInserted = amountInserted;
    }

    public String getSelectedProductCode() {
        return selectedProductCode;
    }

    public void setSelectedProductCode(String selectedProductCode) {
        this.selectedProductCode = selectedProductCode;
    }

    public void clearSelectedProductCode() {
        this.selectedProductCode = null;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
