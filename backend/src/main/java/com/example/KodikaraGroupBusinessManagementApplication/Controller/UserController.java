package com.example.KodikaraGroupBusinessManagementApplication.Controller;

import com.example.KodikaraGroupBusinessManagementApplication.DTO.MessageResponse;
import com.example.KodikaraGroupBusinessManagementApplication.DTO.UserDTO;
import com.example.KodikaraGroupBusinessManagementApplication.DTO.UserUpdateDTO;
import com.example.KodikaraGroupBusinessManagementApplication.model.User;
import com.example.KodikaraGroupBusinessManagementApplication.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salesman") // API route for salesman management
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // --- C (Create) ---
    @PostMapping("/create")
    @PreAuthorize("hasRole('OWNER')") // Security is now enabled
    public ResponseEntity<User> createSalesman(@Valid @RequestBody UserDTO userDTO) {
        User createdSalesman = userService.createSalesman(userDTO);
        return new ResponseEntity<>(createdSalesman, HttpStatus.CREATED);
    }

    // --- R (Read) - Get All Salesmen ---
    @GetMapping("/all")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<User>> getAllSalesmen() {
        List<User> salesmen = userService.getAllSalesmen();
        return new ResponseEntity<>(salesmen, HttpStatus.OK);
    }

    // --- R (Read) - Get One Salesman by ID ---
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<User> getSalesmanById(@PathVariable String id) {
        User salesman = userService.getSalesmanById(id);
        return new ResponseEntity<>(salesman, HttpStatus.OK);
    }

    // --- U (Update) - Update a Salesman ---
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<User> updateSalesman(@PathVariable String id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        User updatedSalesman = userService.updateSalesman(id, userUpdateDTO);
        return new ResponseEntity<>(updatedSalesman, HttpStatus.OK);
    }

    // --- D (Delete) - Delete a Salesman ---
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<MessageResponse> deleteSalesman(@PathVariable String id) {
        userService.deleteSalesman(id);
        return new ResponseEntity<>(new MessageResponse("Salesman deleted successfully!"), HttpStatus.OK);
    }
}