package com.evolution.Inventoryservice.controller;

import com.evolution.Inventoryservice.dto.InventoryResponse;
import com.evolution.Inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author laith ghnemat
 * @version 1.0
 * @since 1/9/2024
 */
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
// http://localhost:8082/api/inventory/?skuCode=iphone_13_red&skuCode=iphone_13
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        log.info("Received inventory check request for skuCode: {}", skuCode);
        return inventoryService.isInStock(skuCode);
    }


    // http://localhost:8082/api/inventory/api-gateway-test/?skuCode=iphone_13
    @GetMapping("/api-gateway-test")
    @ResponseStatus(HttpStatus.OK)
    public Boolean isInStock(@RequestParam String skuCode) {
        log.info("Received inventory check request for skuCode: {}", skuCode);
        return inventoryService.isInStock(skuCode);
    }

}
