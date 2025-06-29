package com.example.ets.controller;

import com.example.ets.dto.RegisterRequest;
import com.example.ets.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "User creation endpoint for authenticated users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Create new user", description = "Creates a new user. Requires JWT token.")
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody RegisterRequest request) {
        userService.createUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("User created successfully.");
    }
}
