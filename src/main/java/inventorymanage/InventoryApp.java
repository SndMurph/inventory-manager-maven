package inventorymanage;

import java.util.Scanner;
import java.sql.SQLException;

public class InventoryApp {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		DatabaseManager db = new DatabaseManager();

		try {
			db.connect();
			System.out.println("Connected to database.");

			while (true) {
				System.out.println( "\n1. View Items\n2. Add Item\n3. Delete Item\n4. Update Quantity\n5. Update Price\n0. Exit");
				System.out.print("Choose an option: ");
				int choice = scanner.nextInt();
				scanner.nextLine(); // consume newline
					
				if (choice == 1) { 
					for (InventoryItem item : db.getAllItems()) {
						System.out.println(item);
					}
				} else if (choice == 2) {
					System.out.print("Enter name: ");
					String name = scanner.nextLine();
					System.out.print("Enter quantity: ");
					int quantity = scanner.nextInt();
					System.out.print("Enter price: ");
					double price = scanner.nextDouble();
					db.addItem(name, quantity, price);
					System.out.println("Item added.");
				} else if (choice == 3) {
					for (InventoryItem item : db.getAllItems()) {
						System.out.println(item);
					}

					System.out.print("Enter the ID of the item to delete: ");
					int id = scanner.nextInt();
					db.deleteItem(id);

				} else if (choice == 4) {
					System.out.print("Enter the name of the item to update: ");
					String name = scanner.nextLine();

					InventoryItem item = db.getItemByName(name);

					if (item != null) {
					    System.out.println("Found: " + item);
					    System.out.print("Enter new quantity: ");
					    int newQty = scanner.nextInt();
						if (newQty <= 0) {
							newQty = 0;
						}
					    scanner.nextLine(); 

					    db.updateQuantity(item.getId(), newQty);

					    System.out.println("Item updated:");
					    System.out.println(db.getItemById(item.getId()));
					} else {
					    System.out.println("Item not found.");
					}
					

				} else if (choice == 5) {
					System.out.print("Enter the name of the item to update price: ");
					String name = scanner.nextLine();

					InventoryItem item = db.getItemByName(name);

					if (item != null) {
					    System.out.println("Found: " + item);
					    System.out.print("Enter new price: ");
					    double newPrice = scanner.nextDouble();
						if (newPrice <= 0) {
							newPrice = 0.1;
						}
					    scanner.nextLine();

					    db.updatePrice(item.getId(), newPrice);

					    System.out.println("Item updated:");
					    System.out.println(db.getItemById(item.getId()));
					} else {
					    System.out.println("Item not found.");
					}
					
				} else if (choice == 0) {
					break;
				}
			}

			db.disconnect();
			System.out.println("Disconnected.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		scanner.close();
	}
}