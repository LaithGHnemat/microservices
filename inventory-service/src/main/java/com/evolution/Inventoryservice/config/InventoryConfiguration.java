package com.evolution.Inventoryservice.config;

import com.evolution.Inventoryservice.model.Inventory;
import com.evolution.Inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author laith ghnemat
 * @version 1.0
 * @since 1/9/2024
 */
@Configuration
@Transactional
public class InventoryConfiguration {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            checkData();
        };
    }
    private void checkData() {
        if(inventoryRepository.findAll().size() > 0) return;
        inventoryRepository.saveAll(getInventories());
    }

    private List< Inventory > getInventories() {
        return List.of (Inventory.builder().skuCode("iphone_13").quantity (100).build(),
                Inventory.builder().skuCode ("iphone_13_red").quantity(0).build());
    }
}
