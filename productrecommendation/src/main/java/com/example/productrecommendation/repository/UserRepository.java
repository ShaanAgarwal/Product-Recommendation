package com.example.productrecommendation.repository;

import com.example.productrecommendation.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByName(String name);
}