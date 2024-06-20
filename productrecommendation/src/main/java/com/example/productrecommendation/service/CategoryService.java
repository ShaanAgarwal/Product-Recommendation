package com.example.productrecommendation.service;

import com.example.productrecommendation.model.Category;
import com.example.productrecommendation.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<List<Category>> getAllCategories() {
        return Optional.of(categoryRepository.findAll());
    }

    public Optional<Category> getCategoryById(String id) {
        return categoryRepository.findById(id);
    }

}
