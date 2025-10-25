package com.example.KodikaraGroupBusinessManagementApplication.services;

import com.example.KodikaraGroupBusinessManagementApplication.DTO.UserDTO;
import com.example.KodikaraGroupBusinessManagementApplication.DTO.UserUpdateDTO;
import com.example.KodikaraGroupBusinessManagementApplication.Repo.UserRepository;
import com.example.KodikaraGroupBusinessManagementApplication.exception.ResourceNotFoundException;
import com.example.KodikaraGroupBusinessManagementApplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // --- C (Create) ---
    public User createSalesman(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("User already registered");
        }
        User newUser = new User();
        newUser.setUserId(IdGenerator.userId());
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setRole("ROLE_SALESMAN");
        return userRepository.save(newUser);
    }

    // --- R (Read) - Get All Salesmen ---
    public List<User> getAllSalesmen() {
        return userRepository.findByRole("ROLE_SALESMAN");
    }

    // --- R (Read) - Get One Salesman by ID ---
    public User getSalesmanById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Salesman not found with id: " + userId));
    }

    // --- U (Update) - Update Salesman Details ---
    public User updateSalesman(String userId, UserUpdateDTO userUpdateDTO) {
        // 1. Find the existing user
        User userToUpdate = getSalesmanById(userId); // Re-uses the method above

        // 2. Check if the new username is already taken by ANOTHER user
        Optional<User> userWithNewUsername = userRepository.findByUsername(userUpdateDTO.getUsername());
        if (userWithNewUsername.isPresent() && !userWithNewUsername.get().getUserId().equals(userId)) {
            throw new IllegalArgumentException("Username is already taken");
        }

        // 3. Update the fields
        userToUpdate.setUsername(userUpdateDTO.getUsername());

        // 4. Save the changes
        return userRepository.save(userToUpdate);
    }

    // --- D (Delete) - Delete a Salesman ---
    public void deleteSalesman(String userId) {
        // 1. Check if user exists before trying to delete
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Salesman not found with id: " + userId);
        }
        // 2. Delete the user
        userRepository.deleteById(userId);
    }
}