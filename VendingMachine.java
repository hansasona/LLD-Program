import java.util.*;

enum State {
    IDLE,
    PAYMENT,
    READY
}

class Product {
    String name;
    int price;

    Product(String name, int price) {
        this.name = name;
        this.price = price;
    }
}

class Slot {
    String code;
    Product product;
    int quantity;

    Slot(String code, Product product, int quantity) {
        this.code = code;
        this.product = product;
        this.quantity = quantity;
    }
}

public class VendingMachine {

    State state = State.IDLE;

    Map<String, Slot> slots = new HashMap<>();

    Product selectedProduct;
    int moneyInserted = 0;

    // Add product to machine
    void addSlot(String code, Product product, int quantity) {
        slots.put(code, new Slot(code, product, quantity));
    }

    // Select product
    void selectProduct(String code) {

        if (state != State.IDLE) {
            System.out.println("Cannot select product now");
            return;
        }

        Slot slot = slots.get(code);

        if (slot == null) {
            System.out.println("Slot not found");
            return;
        }

        if (slot.quantity == 0) {
            System.out.println("Product is out of stock");
            return;
        }

        selectedProduct = slot.product;

        state = State.PAYMENT;

        System.out.println("Selected: " + selectedProduct.name);
        System.out.println("Price: " + selectedProduct.price);
    }

    // Insert money
    void insertMoney(int money) {

        if (state != State.PAYMENT) {
            System.out.println("Please select product first");
            return;
        }

        moneyInserted += money;

        System.out.println("Money inserted: " + moneyInserted);

        if (moneyInserted >= selectedProduct.price) {
            state = State.READY;
        }
    }

    // Dispense product
    void dispense() {

        if (state != State.READY) {
            System.out.println("Not ready to dispense");
            return;
        }

        System.out.println("Dispensing: " + selectedProduct.name);

        // Reduce quantity
        for (Slot slot : slots.values()) {
            if (slot.product == selectedProduct) {
                slot.quantity--;
                break;
            }
        }

        int change = moneyInserted - selectedProduct.price;

        System.out.println("Change: " + change);

        // Reset machine
        selectedProduct = null;
        moneyInserted = 0;
        state = State.IDLE;
    }

    // Cancel
    void cancel() {

        System.out.println("Transaction cancelled");
        System.out.println("Refund: " + moneyInserted);

        selectedProduct = null;
        moneyInserted = 0;
        state = State.IDLE;
    }

    public static void main(String[] args) {

        VendingMachine machine = new VendingMachine();

        // Add products
        machine.addSlot(
                "A1",
                new Product("Coke", 30),
                5
        );

        machine.addSlot(
                "A2",
                new Product("Pepsi", 20),
                5
        );

        // Customer flow
        machine.selectProduct("A1");

        machine.insertMoney(10);
        machine.insertMoney(10);
        machine.insertMoney(10);

        machine.dispense();
    }
}
