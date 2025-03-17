package com.android_sdp.server.controllers;

import com.android_sdp.server.dtos.UserDTO;
import com.android_sdp.server.models.Users;
import com.android_sdp.server.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "Operations for managing user authentication, profiles, and orders")
public class UserController {

    @Autowired
    private UsersService usersService;

    @Operation(summary = "Get all users", description = "Returns a list of all registered users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of users")
    })
    @GetMapping("/")
    public ResponseEntity<Iterable<Users>> getAllUsers() {
        return ResponseEntity.ok(usersService.getUsers());
    }

    @Operation(summary = "Get user by email", description = "Retrieves user details by email if exists.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<Users> getUserByEmail(@PathVariable String email) {
        Users user = usersService.getUserDataMail(email);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create a new user", description = "Adds a new user to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully")
    })
    @PostMapping("/create")
    public ResponseEntity<Users> createUser(@RequestBody UserDTO userDto) {
        return ResponseEntity.ok(usersService.createUser(userDto));
    }

    @Operation(summary = "Delete a user", description = "Removes a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Users> deleteUser(@PathVariable Long id) {
        Users user = usersService.deleteUser(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update user details", description = "Updates an existing user's information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/update")
    public ResponseEntity<Users> updateUser(@RequestBody UserDTO userDto) {
        Users user = usersService.updateUser(userDto);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
}
