package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import inventorymanage.DatabaseManager;
import inventorymanage.InventoryItem;
import java.sql.*;
import java.util.ArrayList;
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
    
    @FXML
    private void initialize() {

        try {
            db.connect();
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
    
    @FXML
    private void onRefresh() {
        try {
            ArrayList<InventoryItem> items = db.getAllItems();    
            ObservableList<InventoryItem> data = FXCollections.observableArrayList(items);
            tableView.setItems(data);
            System.out.println("[FX] Refresh clicked!");
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    
    public void shutdown() {
        try { db.disconnect(); } catch (SQLException ignored) {}
    }
}