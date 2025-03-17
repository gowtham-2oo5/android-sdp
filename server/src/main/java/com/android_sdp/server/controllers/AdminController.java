package com.android_sdp.server.controllers;

import com.android_sdp.server.dtos.AdminDTO;
import com.android_sdp.server.models.Admins;
import com.android_sdp.server.services.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Controller", description = "Operations for managing restaurant admins, users, and global settings")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "Create a Super Admin", description = "Creates a new Super Admin with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Super Admin created successfully")
    })
    @PostMapping("/super")
    public ResponseEntity<String> createSuperAdmin(@RequestBody AdminDTO superAdmin) {
        return ResponseEntity.ok(adminService.createSuperAdmin(superAdmin));
    }

    @Operation(summary = "Get all admins", description = "Returns a list of all registered admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of admins")
    })
    @GetMapping
    public ResponseEntity<List<Admins>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @Operation(summary = "Get only Super Admins", description = "Returns a list of all super admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of super admins")
    })
    @GetMapping("/super")
    public ResponseEntity<List<Admins>> getSuperAdmins() {
        return ResponseEntity.ok(adminService.getSuperAdmins());
    }

    @Operation(summary = "Create a Regular Admin", description = "Creates a new admin with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin created successfully")
    })
    @PostMapping
    public ResponseEntity<String> createAdmin(@RequestBody AdminDTO admin) {
        return ResponseEntity.ok(adminService.createAdmin(admin));
    }

    @Operation(summary = "Assign an Admin to a Restaurant", description = "Assigns an existing admin to a restaurant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin assigned to restaurant successfully")
    })
    @PostMapping("/{adminId}/assign/{restaurantId}")
    public ResponseEntity<String> assignAdminToRestaurant(
            @PathVariable Long adminId,
            @PathVariable Long restaurantId) {
        return ResponseEntity.ok(adminService.assignAdminToRestaurant(adminId, restaurantId));
    }

    @Operation(summary = "Create a Restaurant Admin", description = "Creates a new restaurant admin with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant Admin created successfully")
    })
    @PostMapping("/restaurant")
    public ResponseEntity<String> createRestaurantAdmin(@RequestBody AdminDTO admin) {
        return ResponseEntity.ok(adminService.createRestaurantAdmin(admin));
    }
}
