package inventorymanage;

public class InventoryItem {
    private int id;
    private String name;
    private int quantity;
    private double price;

    public InventoryItem(int intId, String itemName, int itemQuantity, double itemPrice) {
        this.id = intId;
        this.name = itemName;
        this.quantity = itemQuantity;
        this.price = itemPrice;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Quantity: %d | Price: $%.2f", id, name, quantity, price);
    }
}