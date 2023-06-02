package com.example.zinware.controller;

import com.example.zinware.model.login.LoginRequest;
import com.example.zinware.model.User;
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

    @PostMapping(path = "/register/")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping(path = "/login/")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        return userService.loginUser(loginRequest);
    }

}
