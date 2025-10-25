package com.example.KodikaraGroupBusinessManagementApplication.services;

import com.example.KodikaraGroupBusinessManagementApplication.DTO.UserDTO;
import com.example.KodikaraGroupBusinessManagementApplication.Repo.UserRepository;
import com.example.KodikaraGroupBusinessManagementApplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // These fields are final because they are set in the constructor
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // This is the constructor for injection
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Implements Use Case 1: Salesman Management
     */
    public User createSalesman(UserDTO userDTO) {

        // "Alternate Flow 2: Details duplicated"
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            // This exception will be caught by the GlobalExceptionHandler
            throw new IllegalArgumentException("User already registered");
        }

        // "General Flow 5: System processes and stores the salesman profile"
        User newUser = new User();

        // Generate ID using your IdGenerator
        newUser.setUserId(IdGenerator.userId());
        newUser.setUsername(userDTO.getUsername());

        // --- CRITICAL: Hash the password before saving ---
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        newUser.setRole("ROLE_SALESMAN"); // As per the use case

        // "Post-conditions: A new salesman profile is created"
        return userRepository.save(newUser);
    }
}