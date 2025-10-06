package com.example.KodikaraGroupBusinessManagementApplication.services;

import com.example.KodikaraGroupBusinessManagementApplication.DTO.LoginRequest;
import com.example.KodikaraGroupBusinessManagementApplication.DTO.LoginResponse;
import com.example.KodikaraGroupBusinessManagementApplication.DTO.UserDTO;
import com.example.KodikaraGroupBusinessManagementApplication.model.User;
import com.example.KodikaraGroupBusinessManagementApplication.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());

        if (user == null) {
            return new LoginResponse(false, "User not found");
        }

        // IMPORTANT: Passwords should be hashed in a real application!
        // This is a temporary, insecure check.
        if (user.getPassword().equals(loginRequest.getPassword())) {
            UserDTO userDTO = toDto(user);
            return new LoginResponse(true, "Login successful", userDTO);
        } else {
            return new LoginResponse(false, "Incorrect password");
        }
    }

    private UserDTO toDto(User user) {
        return new UserDTO(user.getUserId(), user.getUsername(), user.getRole());
    }
}