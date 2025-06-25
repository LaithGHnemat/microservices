package com.evolution.Inventoryservice.repository;

import com.evolution.Inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository< Inventory, Long> {
    Optional<Inventory> findBySkuCode(String skuCode);
    @Query("SELECT i FROM Inventory i WHERE i.skuCode IN :skuCodes")
    List<Inventory> findBySkuCodeIn(@Param("skuCodes") List<String> skuCodes);
}
