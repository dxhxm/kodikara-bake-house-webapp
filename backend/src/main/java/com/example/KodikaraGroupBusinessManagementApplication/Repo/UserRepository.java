package com.example.KodikaraGroupBusinessManagementApplication.Repo;

import com.example.KodikaraGroupBusinessManagementApplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // This method is used for login
    Optional<User> findByUsername(String username);

    // --- THIS IS THE LINE YOU ARE MISSING ---
    // This fixes your error by allowing you to check for duplicates
    Boolean existsByUsername(String username);
}