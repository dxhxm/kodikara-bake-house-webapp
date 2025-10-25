package com.example.KodikaraGroupBusinessManagementApplication.Controller;

import com.example.KodikaraGroupBusinessManagementApplication.DTO.UserDTO;
import com.example.KodikaraGroupBusinessManagementApplication.model.User;
import com.example.KodikaraGroupBusinessManagementApplication.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // This line will be fixed
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/salesman") // API route for salesman management
public class UserController {

    // --- This is the fix for the "Field injection" warning ---
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    //@PreAuthorize("hasRole('OWNER')") // This line will be fixed
    public ResponseEntity<User> createSalesman(@Valid @RequestBody UserDTO userDTO) {
        // @Valid handles "Alternate Flow 1: Required fields are empty"

        User createdSalesman = userService.createSalesman(userDTO);
        return new ResponseEntity<>(createdSalesman, HttpStatus.CREATED);
    }
}