package com.example.KodikaraGroupBusinessManagementApplication.services;

import com.example.KodikaraGroupBusinessManagementApplication.DTO.UserDTO;
import com.example.KodikaraGroupBusinessManagementApplication.Repo.UserRepository;
import com.example.KodikaraGroupBusinessManagementApplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // --- FIX 3: Use 'final' fields ---
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // --- FIX 3: Use Constructor Injection ---
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
            // "User already registered"
            throw new IllegalArgumentException("User already registered");
        }

        // "General Flow 5: System processes and stores the salesman profile"
        User newUser = new User();

        // Generate ID using your IdGenerator (This will now work)
        newUser.setUserId(IdGenerator.userId());
        newUser.setUsername(userDTO.getUsername());

        // Hash the password before saving
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        newUser.setRole("ROLE_SALESMAN"); // As per the use case

        return userRepository.save(newUser);
    }
}