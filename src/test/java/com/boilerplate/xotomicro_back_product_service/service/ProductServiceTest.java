package com.boilerplate.xotomicro_back_product_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.boilerplate.xotomicro_back_product_service.dto.ProductDto;
import com.boilerplate.xotomicro_back_product_service.model.Product;
import com.boilerplate.xotomicro_back_product_service.repository.ProductRepository;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        List<Product> productList = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            Product product = new Product();
            product.setId((long) i);
            product.setName("Product " + i);
            product.setDescription("Description " + i);
            productList.add(product);
        }
        when(productRepository.findAll()).thenReturn(productList);
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(productList.get(0)));
        when(productRepository.save(productList.get(0))).thenReturn(productList.get(0));
    }

    @DisplayName("Get All Product")
    @Test
    void getAll() {
        assertEquals(5, productService.getAll().size());
        assertNotNull(productService.getAll());
    }

    @DisplayName("Get Product By Id")
    @Test
    void getProductById() {
        ProductDto product = productService.getByProductId(1L);
        ProductDto product2 = productService.getByProductId(2L);
        assertNotNull(productRepository.findAll().get(0).toProductDto().getId());
        assertNotNull(product.getId());
        assertNull(product2);
        assertEquals(productRepository.findAll().get(0).toProductDto().getId(), product.getId());
    }
}
