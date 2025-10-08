package inventorymanage;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
	private Connection conn;

	public void connect() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/inventory_db";
		String user = "root";
		String password = "Your_password_here"; // replace with your password
		conn = DriverManager.getConnection(url, user, password);
	}
	
	public Connection getConnection() {
	    return conn; 
	}
	
	public void disconnect() throws SQLException {
		if (conn != null && !conn.isClosed()) {
			conn.close();
		}
	}

	public void addItem(String name, int quantity, double price) throws SQLException {
		String sql = "INSERT INTO items (name, quantity, price) VALUES (?, ?, ?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, name);
		stmt.setInt(2, quantity);
		stmt.setDouble(3, price);
		stmt.executeUpdate();
	}

	public void deleteItem(int id) throws SQLException {
		String sql = "DELETE FROM items WHERE id = ? ";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.executeUpdate();
	}

	public void updateQuantity(int id, int quantity) throws SQLException {
		String sql = "UPDATE items SET quantity = ? WHERE id = ? ";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, quantity);
		stmt.setInt(2, id);
		stmt.executeUpdate();
	}

	public void updatePrice(int id, double price) throws SQLException {
		String sql = "UPDATE items SET price = ? WHERE id = ? ";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setDouble(1, price);
		stmt.setInt(2, id);
		stmt.executeUpdate();
	}

	public InventoryItem getItemById(int id) throws SQLException {
		String sql = "SELECT * FROM items WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			return new InventoryItem(rs.getInt("id"), rs.getString("name"), rs.getInt("quantity"),
					rs.getDouble("price"));
		}
		return null;
	}

	public InventoryItem getItemByName(String name) throws SQLException {
		String sql = "SELECT * FROM items WHERE name = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			return new InventoryItem(rs.getInt("id"), rs.getString("name"), rs.getInt("quantity"),
					rs.getDouble("price"));
		}
		return null;
	}

	public ArrayList<InventoryItem> getAllItems() throws SQLException {
		ArrayList<InventoryItem> items = new ArrayList<>();
		String sql = "SELECT * FROM items";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			InventoryItem item = new InventoryItem(rs.getInt("id"), rs.getString("name"), rs.getInt("quantity"),
					rs.getDouble("price"));
			items.add(item);
		}
		return items;
	}
}