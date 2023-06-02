package com.example.zinware.service;

import com.example.zinware.model.login.LoginRequest;
import com.example.zinware.model.User;
import com.example.zinware.model.login.LoginResponse;
import com.example.zinware.repository.UserRepository;
import com.example.zinware.security.JwtUtils;
import com.example.zinware.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;
    private MyUserDetails myUserDetails;

    @Autowired
    public UserService(UserRepository userRepository,
                       @Lazy PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils,
                       @Lazy AuthenticationManager authenticationManager,
                       @Lazy MyUserDetails myUserDetails) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.myUserDetails = myUserDetails;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static User getCurrentLoggedInUser(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    /**
     * Register a new user
     * @param user  object of User
     * @return ResponseEntity object with status CREATED and body of User
     */
    public ResponseEntity<User> registerUser(User user) {
        // TODO: implement checking for existing use
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
     * find user by email in repository
     * @param email of user
     * @return User object
     */
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    /**
     * Login user and return JWT
     * @param loginRequest object of LoginRequest
     * @return ResponseEntity object with status OK and body of LoginResponse
     * @throws Exception if username or password is incorrect
     */
    public ResponseEntity<?> loginUser(LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            myUserDetails = (MyUserDetails) authentication.getPrincipal();
            final String JWT = jwtUtils.generateJwtToken(myUserDetails);
            return ResponseEntity.ok(new LoginResponse(JWT));
        }catch (Exception e){
            //TODO: create custom exception
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Error: username or password is incorrect"));
        }
    }

}
