package com.boilerplate.xotomicro_back_product_service.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.boilerplate.xotomicro_back_product_service.model.Product;
import com.boilerplate.xotomicro_back_product_service.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
public class ProductTest2 {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private KafkaSender kafkaSender;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        when(productRepository.save(Mockito.any(Product.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @DisplayName("Create Product")
    @Test
    void createProduct() {
        List<Product> productList = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            Product product = new Product();
            product.setId((long) i);
            product.setName("Product " + i);
            product.setDescription("Description " + i);
            productList.add(product);
        }

        var result = productService.createProduct(productList.get(0).toProductDto());

        assertNotNull(result);
    }

    @DisplayName("Create Product Exception")
    @Test
    void JsonProcessingException() throws JsonProcessingException {
        List<Product> productList = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            Product product = new Product();
            product.setId((long) i);
            product.setName("Product " + i);
            product.setDescription("Description " + i);
            productList.add(product);
        }

        JsonProcessingException jsonProcessingException = assertThrows(JsonProcessingException.class, () -> {
            when(objectMapper.writeValueAsString(any(Object.class))).thenThrow(mock(JsonProcessingException.class));
            objectMapper.writeValueAsString(productList.get(0));
            productService.createProduct(productList.get(0).toProductDto());
        });
    }
}
