package dao;

import inventorymanage.DatabaseManager;
import inventorymanage.InventoryItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcInventoryDao implements InventoryDao {

    private final DatabaseManager db;

    public JdbcInventoryDao(DatabaseManager db) {
        this.db = db;
    }

    @Override
    public List<InventoryItem> findAll() throws SQLException {
        List<InventoryItem> items = new ArrayList<>();
        String sql = "SELECT id, name, quantity, price FROM items ORDER BY id";

        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                items.add(new InventoryItem(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getDouble("price")
                ));
            }
        }
        return items;
    }

    @Override
    public Optional<InventoryItem> findById(int id) throws SQLException {
        String sql = "SELECT * FROM items WHERE id = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                InventoryItem item = new InventoryItem(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getDouble("price")
                );
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    @Override
    public void insert(InventoryItem item) throws SQLException {
        String sql = "INSERT INTO items (name, quantity, price) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setInt(2, item.getQuantity());
            stmt.setDouble(3, item.getPrice());
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(InventoryItem item) throws SQLException {
        String sql = "UPDATE items SET name = ?, quantity = ?, price = ? WHERE id = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setInt(2, item.getQuantity());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM items WHERE id = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}