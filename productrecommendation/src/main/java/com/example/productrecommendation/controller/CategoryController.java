package com.example.productrecommendation.controller;

import com.example.productrecommendation.exception.CategoryAlreadyExistsException;
import com.example.productrecommendation.exception.CategoryNotFoundException;
import com.example.productrecommendation.exception.InsufficientCredentialsException;
import com.example.productrecommendation.exception.InternalServerErrorException;
import com.example.productrecommendation.model.Category;
import com.example.productrecommendation.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/getAllCategories")
    public ResponseEntity<?> getAllCategories() {
        try {
            Optional<List<Category>> categories = categoryService.getAllCategories();
            if(categories.get().isEmpty()) {
                throw new CategoryNotFoundException("No categories exist in the database");
            }
            return ResponseEntity.status(HttpStatus.OK).body(categories);
        } catch (CategoryNotFoundException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new InternalServerErrorException("An error occurred while fetching all categories", exception);
        }
    }

    @GetMapping("/getCategoryById/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") String id) {
        try {
            Optional<Category> categoryExists = categoryService.getCategoryById(id);
            if(categoryExists.isEmpty()) {
                throw new CategoryNotFoundException("Category does not exist with given id: "+id);
            }
            return ResponseEntity.status(HttpStatus.OK).body(categoryExists);
        } catch (CategoryNotFoundException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new InternalServerErrorException("An error occurred while fetching a particular category by id", exception);
        }
    }

}