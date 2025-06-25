package com.evolution.productservice.service;

import com.evolution.productservice.dto.ProductRequest;
import com.evolution.productservice.dto.ProductResponse;

import java.util.List;

public interface IProductService {
    void createProduct(ProductRequest productRequest);
    List< ProductResponse> getAllProducts();
}
