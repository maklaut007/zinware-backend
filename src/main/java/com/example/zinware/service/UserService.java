package com.example.zinware.service;


import com.example.zinware.model.User;
import com.example.zinware.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        // TODO: implement checking for existing use
        return userRepository.save(user);
    }
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


}
