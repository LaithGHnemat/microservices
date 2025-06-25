package com.evolution.productservice;

import com.evolution.productservice.dto.ProductRequest;
import com.evolution.productservice.dto.ProductResponse;
import com.evolution.productservice.model.Product;
import com.evolution.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author laith ghnemat
 * @version 1.0
 * @since 1/5/2024
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }
    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
        String productRequestString = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform( MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestString))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, productRepository.findAll().size());
    }


    @Test
    void shouldGetAllProducts() throws Exception {
        ProductRequest initialProduct = ProductRequest.builder()
                .name("Initial Product")
                .description("Initial Description")
                .price(BigDecimal.valueOf(100))
                .build();
        productRepository.save(mapToEntity(initialProduct));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    List< ProductResponse > productResponses = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            objectMapper.getTypeFactory().constructCollectionType(List.class,ProductResponse.class) );
                    Assertions.assertEquals(1, productResponses.size());
                    Assertions.assertEquals(BigDecimal.valueOf(100), productResponses.get (0).getPrice ());
                    Assertions.assertEquals("Initial Product", productResponses.get(0).getName());});


    }
    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("iPhone 13")
                .description("iPhone 13")
                .price(BigDecimal.valueOf(1200))
                .build();
    }

    private Product mapToEntity(ProductRequest productRequest) {
        return Product.builder ()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
    }

}
