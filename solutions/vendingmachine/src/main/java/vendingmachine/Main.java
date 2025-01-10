package vendingmachine;

import vendingmachine.service.VendingMachineService;
import vendingmachine.model.denomination.FiftyRupeeNote;
import vendingmachine.model.denomination.FiveRupeeCoin;
import vendingmachine.model.denomination.TenRupeeNote;
import vendingmachine.model.denomination.TwentyRupeeNote;
import vendingmachine.repository.InMemoryInventoryRepository;
import vendingmachine.repository.InventoryRepository;
import vendingmachine.service.InventoryService;

public class Main {
    public static void main(String[] args) {
        VendingMachineService machine = getVendingMachine();

        // Initiate and cancel a transaction
        machine.insertMoney(new FiveRupeeCoin());
        machine.insertMoney(new TenRupeeNote());
        machine.cancelTransaction();

        // Initiate another transaction but sufficient change does not exist
        machine.insertMoney(new FiftyRupeeNote());
        machine.selectProduct("102");
        machine.dispenseProduct();

        // Initiate another transaction and change exists
        machine.insertMoney(new FiveRupeeCoin());
        machine.insertMoney(new TenRupeeNote());
        machine.insertMoney(new TwentyRupeeNote());
        machine.selectProduct("105");
        machine.dispenseProduct();
    }

    private static VendingMachineService getVendingMachine() {
        InventoryRepository inventoryRepository = new InMemoryInventoryRepository();
        InventoryService inventoryService = new InventoryService(inventoryRepository);

        inventoryService.addItem("100", 10.00, 5);
        inventoryService.addItem("101", 15.00, 10);
        inventoryService.addItem("102", 20.00, 15);
        inventoryService.addItem("103", 25.00, 20);
        inventoryService.addItem("105", 30.00, 25);

        return new VendingMachineService(inventoryService);
    }
}
