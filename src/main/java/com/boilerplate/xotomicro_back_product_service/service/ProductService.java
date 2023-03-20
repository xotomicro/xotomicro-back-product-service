package com.boilerplate.xotomicro_back_product_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.boilerplate.xotomicro_back_product_service.dto.ProductDto;
import com.boilerplate.xotomicro_back_product_service.model.Product;
import com.boilerplate.xotomicro_back_product_service.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Value("${kafka.topics.product}")
    private String productTopic;

    private final KafkaSender kafkaSender;
    private final ProductRepository productRepository;

    private final ObjectMapper objectMapper;

    public ProductService(KafkaSender kafkaSender, ProductRepository productRepository) {
        this.kafkaSender = kafkaSender;
        this.productRepository = productRepository;
        this.objectMapper = new ObjectMapper();
    }

    public List<ProductDto> getAll() {
        return productRepository.findAll().stream().map(Product::toProductDto).collect(Collectors.toList());
    }

    @Cacheable(value = "product", unless = "#result == null")
    public ProductDto getByProductId(Long productId) {
        logger.info("Query product by id: {}", productId);
        return productRepository.findById(productId).map(Product::toProductDto).orElse(null);
    }

    public ProductDto createProduct(ProductDto productDto) {
        var product = Product.fromProductDto(productDto);
        var result = productRepository.save(product).toProductDto();

        try {
            var json = objectMapper.writeValueAsString(result);
            kafkaSender.send(productTopic, json);
        } catch (JsonProcessingException e) {
            logger.error("Cannot convert object to string. ", e);
        }

        return result;
    }
}
