package com.example.productrecommendation.repository;

import com.example.productrecommendation.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
