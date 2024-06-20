package com.example.productrecommendation.service;

import com.example.productrecommendation.model.Category;
import com.example.productrecommendation.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
