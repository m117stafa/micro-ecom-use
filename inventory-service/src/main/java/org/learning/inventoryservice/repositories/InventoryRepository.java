package org.learning.inventoryservice.repositories;

import org.learning.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    List<Inventory> findByProductCodeIn(List<String> productCode);
}
