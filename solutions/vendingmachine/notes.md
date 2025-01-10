## Vending Machine 

### Requirements

### Requirement clarification

### Class diagram

```mermaid
classDiagram
    class VendingMachine {
        -InventoryService inventoryService
        -VendingMachineState state
        -double balance
        -double amountInserted
        -String selectedProductCode
        +insertMoney(Denomination)
        +selectProduct(String)
        +dispenseProduct()
        +dispenseChange()
        +cancelTransaction()
        +setState(VendingMachineState)
    }

    class VendingMachineState {
        <<interface>>
        +insertMoney(Denomination)
        +selectProduct(String)
        +dispenseProduct()
        +dispenseChange()
        +cancelTransaction()
    }

    class IdleState {
        -VendingMachine machine
        +insertMoney(Denomination)
        +selectProduct(String)
    }

    class ProductSelectionState {
        -VendingMachine machine
        +selectProduct(String)
    }

    class ProductDispenseState {
        -VendingMachine machine
        +dispenseProduct()
    }

    class ChangeDispenseState {
        -VendingMachine machine
        +dispenseChange()
    }

    class InventoryService {
        -InventoryRepository repository
        +getItem(String)
        +addItem(String, double, int)
        +removeItem(String)
        +updateItemStock(String, int)
        +dispenseItem(String)
        +isItemAvailable(String)
    }

    class InventoryRepository {
        <<interface>>
        +findByCode(String)
        +save(Item)
        +delete(String)
        +updateQuantity(String, int)
        +decrementQuantity(String, int)
    }

    class InMemoryInventoryRepository {
        -Map~String, Item~ inventory
    }

    class Item {
        -String itemCode
        -double itemPrice
        -int itemQuantity
        +isAvailable()
    }

    class Denomination {
        <<abstract>>
        -double denominationValue
        +getDenominationValue()
    }

    class Coin {
        +Coin(double)
    }

    class Note {
        +Note(double)
    }

    VendingMachine --> VendingMachineState
    VendingMachine --> InventoryService
    VendingMachineState <|.. IdleState
    VendingMachineState <|.. ProductSelectionState
    VendingMachineState <|.. ProductDispenseState
    VendingMachineState <|.. ChangeDispenseState
    IdleState --> VendingMachine
    ProductSelectionState --> VendingMachine
    ProductDispenseState --> VendingMachine
    ChangeDispenseState --> VendingMachine
    InventoryService --> InventoryRepository
    InventoryRepository <|.. InMemoryInventoryRepository
    InMemoryInventoryRepository --> Item
    Denomination <|-- Coin
    Denomination <|-- Note
```

### Other considerations
1. Transaction service for auditing purpose
2. Separate validator methods
3. Builder pattern
4. Separate cash inventory for managing denominations and change validations
5. How would you add new payment methods? -> Use a paymentStrategy within Vending Machine
6. How would you handle concurrent access?
   1. Use ReentrantLocks (reads/write), wherever required.
   2. Use Concurrent collections, wherever required.
   3. For SQL based repositories, we need to use "FOR UPDATE" to atomically find and decrement the quantity.
      1. If "FOR UPDATE" starts, but we never actually update, the db automatically times out the lock.
   4. For DyanamoDB, we can use `@DynamoDBVersionAttribute -> Long version`
   5. Spring provides `@Transactional` for auto locking/unlocking.