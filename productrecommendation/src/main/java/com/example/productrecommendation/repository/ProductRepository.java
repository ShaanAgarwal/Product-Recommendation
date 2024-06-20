package com.example.productrecommendation.repository;

import com.example.productrecommendation.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product,String> {
    Optional<List<Product>> findAllByCategoryId(String id);
}