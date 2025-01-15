package vendingmachine;

import vendingmachine.model.denomination.Coin;
import vendingmachine.model.denomination.Note;
import vendingmachine.service.VendingMachineService;
import vendingmachine.repository.InMemoryInventoryRepository;
import vendingmachine.repository.InventoryRepository;
import vendingmachine.service.InventoryService;

public class Main {
    public static void main(String[] args) {
        VendingMachineService machine = getVendingMachine();

        // Initiate and cancel a transaction
        machine.insertCoin(Coin.FIVE);
        machine.insertNote(Note.TEN);
        machine.cancelTransaction();

        // Initiate another transaction but sufficient change does not exist
        machine.insertNote(Note.FIFTY);
        machine.selectProduct("102");
        machine.dispenseProduct();

        // Initiate another transaction and item is out of stock
        machine.insertCoin(Coin.FIVE);
        machine.insertNote(Note.TEN);
        machine.insertNote(Note.TWENTY);
        machine.selectProduct("105");

        // Initiate another transaction, item is in stock and change exists
        machine.selectProduct("104");
        machine.dispenseProduct();
    }

    private static VendingMachineService getVendingMachine() {
        InventoryRepository inventoryRepository = new InMemoryInventoryRepository();
        InventoryService inventoryService = new InventoryService(inventoryRepository);

        inventoryService.addItem("100", 10.00, 5);
        inventoryService.addItem("101", 15.00, 10);
        inventoryService.addItem("102", 20.00, 15);
        inventoryService.addItem("103", 25.00, 20);
        inventoryService.addItem("104", 25.00, 20);
        inventoryService.addItem("105", 30.00, 0);

        return new VendingMachineService(inventoryService);
    }
}
