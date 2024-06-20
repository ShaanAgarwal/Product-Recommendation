package com.example.productrecommendation.service;

import com.example.productrecommendation.model.Category;
import com.example.productrecommendation.model.Product;
import com.example.productrecommendation.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
}
