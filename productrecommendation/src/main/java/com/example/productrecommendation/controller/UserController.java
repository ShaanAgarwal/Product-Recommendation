package com.example.productrecommendation.controller;

import com.example.productrecommendation.exception.InsufficientCredentialsException;
import com.example.productrecommendation.exception.InternalServerErrorException;
import com.example.productrecommendation.exception.UserAlreadyExistsException;
import com.example.productrecommendation.exception.UserDoesNotExistException;
import com.example.productrecommendation.model.User;
import com.example.productrecommendation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            if(user.getName() == null || user.getName().trim().isEmpty()) {
                throw new InsufficientCredentialsException("Name is required");
            }
            Optional<User> userExists = userService.getExistingUserByName(user.getName());
            if(userExists.isEmpty()) {
                throw new UserDoesNotExistException("User does not exist with given name" + user.getName());
            }
            return ResponseEntity.status(HttpStatus.OK).body(userExists);
        } catch (InsufficientCredentialsException | UserDoesNotExistException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new InternalServerErrorException("An unexpected error occurred while performing login operation", exception);
        }
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        try {
            Optional<User> userExists = userService.getUserById(id);
            if(userExists.isEmpty()) {
                throw new UserDoesNotExistException("User does not exist with given id" + id);
            }
            return ResponseEntity.status(HttpStatus.OK).body(userExists);
        } catch(UserDoesNotExistException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new InternalServerErrorException("An unexpected error occurred while fetching the user by id", exception);
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        try {
            Optional<List<User>> users = userService.getAllUsers();
            if(users.get().isEmpty()) {
                throw new UserDoesNotExistException("No users are present inside the database");
            }
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (UserDoesNotExistException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new InternalServerErrorException("An unexpected error occurred while fetching all users.", exception);
        }
    }
}
