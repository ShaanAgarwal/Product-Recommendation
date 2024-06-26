package com.example.productrecommendation.service;

import com.example.productrecommendation.model.Category;
import com.example.productrecommendation.model.Product;
import com.example.productrecommendation.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<List<Product>> getAllProducts() {
        return Optional.of(productRepository.findAll());
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Optional<List<Product>> getAllProductsByCategoryId(String id) {
        return productRepository.findAllByCategoryId(id);
    }
}
