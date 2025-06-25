package com.evolution.productservice.controller;

import com.evolution.productservice.dto.ProductRequest;
import com.evolution.productservice.dto.ProductResponse;
import com.evolution.productservice.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author laith ghnemat
 * @version 1.0
 * @since 1/5/2024
 */

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List< ProductResponse > getAllProducts() {
        return productService.getAllProducts();
    }

}
