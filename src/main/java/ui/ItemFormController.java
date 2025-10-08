package ui;

import inventorymanage.InventoryItem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import java.util.Optional;

public class ItemFormController {

    @FXML private DialogPane dialogPane;
    @FXML private TextField nameField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private ButtonType saveButtonType;   
    @FXML private ButtonType cancelButtonType; 

    private InventoryItem original; 

    @FXML
    private void initialize() {
        
    }

   
    public void setItem(InventoryItem item) {
        this.original = item;
        if (item != null) {
            nameField.setText(item.getName());
            quantityField.setText(String.valueOf(item.getQuantity()));
            priceField.setText(String.valueOf(item.getPrice()));
        }
    }

  
    public Optional<InventoryItem> getResult(boolean isSavePressed) {
        if (!isSavePressed) return Optional.empty();

      
        String name = nameField.getText() == null ? "" : nameField.getText().trim();
        if (name.isEmpty()) return Optional.empty();

        int qty;
        double price;
        try {
            qty = Integer.parseInt(quantityField.getText().trim());
            price = Double.parseDouble(priceField.getText().trim());
        } catch (Exception e) {
            return Optional.empty();
        }
        if (qty < 0 || price < 0) return Optional.empty();

        if (original == null) {
            // Add
            return Optional.of(new InventoryItem(0, name, qty, price));
        } else {
           
            return Optional.of(new InventoryItem(original.getId(), name, qty, price));
        }
    }


    public Button getSaveButton() {
        return (Button) dialogPane.lookupButton(saveButtonType);
    }
}