package vendingmachine.service;

import vendingmachine.model.denomination.Coin;
import vendingmachine.model.denomination.Note;
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

    public void insertCoin(Coin coin) {
        // State focuses on amount and not payment type
        state.insertMoney(coin.getValue());
    }

    public void insertNote(Note note) {
        state.insertMoney(note.getValue());
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

    public void clearAmountInserted() {
        this.amountInserted = 0.0;
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
