package com.example.zinware.service;

import com.example.zinware.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Loads user by email
     * @param s email of user
     * @return MyUserDetails object with user data
     * @throws UsernameNotFoundException if user not found
     */
    @Override
    public MyUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(s);
        return new MyUserDetails(user);
    }
}
