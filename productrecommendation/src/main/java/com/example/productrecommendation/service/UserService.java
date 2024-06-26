package com.example.productrecommendation.service;

import com.example.productrecommendation.model.User;
import com.example.productrecommendation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getExistingUserByName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<List<User>> getAllUsers() {
        return Optional.of(userRepository.findAll());
    }

    public User updateUser(Optional<User> userExists, User user) {
        User existingUser = userExists.get();
        existingUser.setName(user.getName());
        return userRepository.save(existingUser);
    }

}