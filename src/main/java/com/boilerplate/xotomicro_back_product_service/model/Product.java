package com.boilerplate.xotomicro_back_product_service.model;

import javax.persistence.*;

import com.boilerplate.xotomicro_back_product_service.dto.ProductDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product", schema = "public")
@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public ProductDto toProductDto() {
        return new ProductDto(id, name, description);
    }

    public static Product fromProductDto(ProductDto productDto) {
        var product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        return product;
    }
}
