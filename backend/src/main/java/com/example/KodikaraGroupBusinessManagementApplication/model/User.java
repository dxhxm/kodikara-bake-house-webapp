package com.example.KodikaraGroupBusinessManagementApplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User {

    @Id
    // ERROR 1: Changed from CHAR(10) to VARCHAR(15) to match the database
    @Column(name = "user_id", length = 15)
    private String userId;

    // ERROR 2: Changed length from 50 to 30 to match the database
    @Column(name = "username", length = 30, nullable = false, unique = true)
    private String username;

    // ERROR 3: Added the missing 'password' field.
    // Must match 'password VARCHAR(60) NOT NULL'
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    // ERROR 4: Changed length from 20 to 12 to match the database
    @Column(name = "role", length = 12, nullable = false)
    private String role;

    // ERROR 5: Added the required no-argument constructor for JPA
    public User() {
    }

    // --- Getters and Setters ---

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // ERROR 6: Added the missing getter for 'role'
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}