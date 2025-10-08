package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import inventorymanage.InventoryItem;
import inventorymanage.DatabaseManager;
import dao.InventoryDao;
import dao.JdbcInventoryDao;

public class InventoryController {

    @FXML private Button refreshBtn;
    @FXML private Button addBtn;
    @FXML private Button editBtn;
    @FXML private Button deleteBtn;

    @FXML private TableView<InventoryItem> tableView;
    @FXML private TableColumn<InventoryItem, Integer> idColumn;
    @FXML private TableColumn<InventoryItem, String> nameColumn;
    @FXML private TableColumn<InventoryItem, Integer> quantityColumn;
    @FXML private TableColumn<InventoryItem, Double> priceColumn;

    private final DatabaseManager db = new DatabaseManager();
    private InventoryDao dao;
    
    @FXML
    private void initialize() {

        try {		
            db.connect();
            dao = new JdbcInventoryDao(db);
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        
    	idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    	nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    	quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    	priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    	onRefresh();
        System.out.println("[FX] Controller initialized. FXML injected = "
                + (tableView != null));
    }
    
    private Optional<inventorymanage.InventoryItem> showItemDialog(InventoryItem toEdit) {
        try {
            var loader = new FXMLLoader(getClass().getResource("/ui/item_form.fxml"));
            DialogPane pane = loader.load();
            ItemFormController controller = loader.getController();
            controller.setItem(toEdit);

            Dialog<InventoryItem> dialog = new Dialog<>();
            dialog.setTitle(toEdit == null ? "Add Item" : "Edit Item");
            dialog.setDialogPane(pane);

            
          
            dialog.setResultConverter(bt -> {
                boolean isSave = bt != null && bt.getButtonData().isDefaultButton();
                return controller.getResult(isSave).orElse(null);
            });

            return dialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    
    @FXML
    private void onAdd() {
        showItemDialog(null).ifPresent(item -> {
            try {
                dao.insert(item);
                onRefresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void onEdit() {
        InventoryItem selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        showItemDialog(selected).ifPresent(updated -> {
            try {
                dao.update(updated);
                onRefresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    @FXML
    private void onRefresh() {
        try {
            List<InventoryItem> items = dao.findAll();    
            ObservableList<InventoryItem> data = FXCollections.observableArrayList(items);
            tableView.setItems(data);
            System.out.println("[FX] Refresh clicked!");
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    @FXML
    private void onDelete(javafx.event.ActionEvent e) {
        var selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        var alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.CONFIRMATION,
            "Delete " + selected.getName() + "?",
            javafx.scene.control.ButtonType.OK,
            javafx.scene.control.ButtonType.CANCEL
        );
        alert.showAndWait().ifPresent(bt -> {
            if (bt == javafx.scene.control.ButtonType.OK) {
                try {
                    dao.deleteById(selected.getId());
                    onRefresh(); // reload table
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    public void shutdown() {
        try { db.disconnect(); } catch (SQLException ignored) {}
    }
}