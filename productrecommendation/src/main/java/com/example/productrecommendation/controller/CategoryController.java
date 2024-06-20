package com.example.productrecommendation.controller;

import com.example.productrecommendation.exception.CategoryAlreadyExistsException;
import com.example.productrecommendation.exception.InsufficientCredentialsException;
import com.example.productrecommendation.exception.InternalServerErrorException;
import com.example.productrecommendation.model.Category;
import com.example.productrecommendation.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/createCategory")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {
            if(category.getName().isEmpty() || category.getName().trim().isEmpty()) {
                throw new InsufficientCredentialsException("Category Name is empty");
            }
            Optional<Category> categoryExists = categoryService.findByName(category.getName());
            if(categoryExists.isPresent()) {
                throw new CategoryAlreadyExistsException("Category with given name already exists");
            }
            Category createdCategory = categoryService.addCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (InsufficientCredentialsException | CategoryAlreadyExistsException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new InternalServerErrorException("An error occurred while creating a category.", exception);
        }
    }

}