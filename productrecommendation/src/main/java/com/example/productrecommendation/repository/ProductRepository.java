package com.example.productrecommendation.repository;

import com.example.productrecommendation.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}
