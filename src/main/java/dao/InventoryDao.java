package dao;

import inventorymanage.InventoryItem;
import java.util.List;
import java.util.Optional;

public interface InventoryDao {
    List<InventoryItem> findAll() throws Exception;
    Optional<InventoryItem> findById(int id) throws Exception;
    void insert(InventoryItem item) throws Exception;
    void update(InventoryItem item) throws Exception;
    void deleteById(int id) throws Exception;
}