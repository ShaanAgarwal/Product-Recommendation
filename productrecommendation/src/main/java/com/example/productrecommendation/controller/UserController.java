package com.example.productrecommendation.controller;

import com.example.productrecommendation.exception.InsufficientCredentialsException;
import com.example.productrecommendation.exception.InternalServerErrorException;
import com.example.productrecommendation.exception.UserAlreadyExistsException;
import com.example.productrecommendation.model.User;
import com.example.productrecommendation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            if (user.getName() == null || user.getName().trim().isEmpty()) {
                throw new InsufficientCredentialsException("Name is required.");
            }
            Optional<User> userExists = userService.getExistingUserByName(user.getName());
            if (userExists.isPresent()) {
                throw new UserAlreadyExistsException("User already exists with name: " + user.getName());
            }
            User createdUser = userService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (UserAlreadyExistsException | InsufficientCredentialsException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new InternalServerErrorException("An unexpected error occurred while registering the user", exception);
        }
    }
}
