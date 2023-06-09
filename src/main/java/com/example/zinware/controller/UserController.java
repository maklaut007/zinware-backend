package com.example.zinware.controller;

import com.example.zinware.model.login.LoginRequest;
import com.example.zinware.model.login.User;
import com.example.zinware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth/")
public class UserController {
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Register a new user
     * @param user the user to be registered
     * @return ResponseEntity<User> the user registered with status code 201
     */
    @PostMapping(path = "/register/")//http://localhost:8080/auth/register
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    /**
     * Login a user and return a JWT token
     * @param loginRequest the login request
     * @return ResponseEntity<?> the JWT token with status code 200
     */
    @PostMapping(path = "/login/")//http://localhost:8080/auth/login
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        return userService.loginUser(loginRequest);
    }

}
