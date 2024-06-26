package com.example.productrecommendation.controller;

import com.example.productrecommendation.exception.CategoryNotFoundException;
import com.example.productrecommendation.exception.InsufficientCredentialsException;
import com.example.productrecommendation.exception.InternalServerErrorException;
import com.example.productrecommendation.exception.ProductDoesNotExistException;
import com.example.productrecommendation.model.Category;
import com.example.productrecommendation.model.Product;
import com.example.productrecommendation.service.CategoryService;
import com.example.productrecommendation.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/createProduct/{id}")
    public ResponseEntity<?> createProduct(@PathVariable("id") String id, @RequestBody Product product) {
        try {
            if (product.getName().isEmpty() || product.getDescription().isEmpty() || product.getAmount() == 0) {
                throw new InsufficientCredentialsException("Please enter all the credentials");
            }
            Optional<Category> categoryExists = categoryService.getCategoryById(id);
            if(categoryExists.isEmpty()) {
                throw new CategoryNotFoundException("This category does not exist in database");
            }
            product.setCategory(categoryExists.get());
            Product newProduct = productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
        } catch (InsufficientCredentialsException | CategoryNotFoundException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new InternalServerErrorException("An error occurred while creating the product.", exception);
        }
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProducts() {
        try {
            Optional<List<Product>> products = productService.getAllProducts();
            if(products.get().isEmpty()) {
                throw new ProductDoesNotExistException("No products are present in the database");
            }
            return ResponseEntity.status(HttpStatus.OK).body(products);
        } catch (ProductDoesNotExistException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new InternalServerErrorException("An occurred while getting all products.", exception);
        }
    }

    @GetMapping("/getSingleProductById/{id}")
    public ResponseEntity<?> getSingleProductById(@PathVariable("id") String id) {
        try {
            Optional<Product> product = productService.getProductById(id);
            if(product.isEmpty()) {
                throw new ProductDoesNotExistException("Product does not exist with given id: "+id);
            }
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } catch (ProductDoesNotExistException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new InternalServerErrorException("An occurred while getting single product by id", exception);
        }
    }

    @GetMapping("/getAllProductsByCategory/{id}")
    public ResponseEntity<?> getAllProductsByCategory(@PathVariable("id") String id) {
        try {
            Optional<Category> category = categoryService.getCategoryById(id);
            if(category.isEmpty()) {
                throw new CategoryNotFoundException("Category does not exist.");
            }
            Optional<List<Product>> products = productService.getAllProductsByCategoryId(id);
            if(products.get().isEmpty()) {
                throw new ProductDoesNotExistException("There are no products in this category");
            }
            return ResponseEntity.status(HttpStatus.OK).body(products);
        } catch (CategoryNotFoundException | ProductDoesNotExistException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new InternalServerErrorException("An error occurred while getting all the products for the category.", exception);
        }
    }

}