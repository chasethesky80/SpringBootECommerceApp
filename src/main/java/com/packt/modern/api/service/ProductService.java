package com.packt.modern.api.service;

import com.packt.modern.api.entity.ProductEntity;
import jakarta.validation.constraints.Min;

import java.util.Optional;

public interface ProductService {

    Iterable<ProductEntity> getAllProducts();

    Optional<ProductEntity> getProduct(@Min(value = 1L, message="Invalid product Id") String id);
}
