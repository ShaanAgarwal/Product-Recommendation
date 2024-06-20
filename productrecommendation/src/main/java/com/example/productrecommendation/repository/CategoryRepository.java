package com.example.productrecommendation.repository;

import com.example.productrecommendation.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findByName(String name);
}
