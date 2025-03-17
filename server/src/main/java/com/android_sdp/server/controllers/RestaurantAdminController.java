package com.android_sdp.server.controllers;

import com.android_sdp.server.dtos.*;
import com.android_sdp.server.services.RestaurantAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/res-admin")
@Tag(name = "Restaurant Admin Controller", description = "Operations for managing restaurant menus, managers, and orders")
public class RestaurantAdminController {

    @Autowired
    private RestaurantAdminService restaurantAdminService;

    // ------------------ MENU MANAGEMENT ------------------ //

    @Operation(summary = "Create a new menu for a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu created successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @PostMapping("/menu/{restaurantId}")
    public ResponseEntity<String> createMenu(@PathVariable Long restaurantId, @RequestBody MenuDTO menuDTO) {
        return ResponseEntity.ok(restaurantAdminService.createMenu(restaurantId, menuDTO));
    }

    @Operation(summary = "Add an item to an existing menu")
    @PostMapping("/menu/{menuId}/items")
    public ResponseEntity<String> addItemToMenu(@PathVariable Long menuId, @RequestBody MenuItemDTO menuItemDTO) {
        return ResponseEntity.ok(restaurantAdminService.addItemToMenu(menuId, menuItemDTO));
    }

    @Operation(summary = "Update an existing menu item")
    @PutMapping("/menu/items/{menuItemId}")
    public ResponseEntity<String> updateMenuItem(@PathVariable Long menuItemId, @RequestBody MenuItemDTO updatedItem) {
        return ResponseEntity.ok(restaurantAdminService.updateMenuItem(menuItemId, updatedItem));
    }

    @Operation(summary = "Delete a menu item")
    @DeleteMapping("/menu/items/{menuItemId}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long menuItemId) {
        return ResponseEntity.ok(restaurantAdminService.deleteMenuItem(menuItemId));
    }

    @Operation(summary = "Get all menus of a restaurant")
    @GetMapping("/menu/{restaurantId}")
    public ResponseEntity<List<MenuDTO>> getRestaurantMenus(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantAdminService.getRestaurantMenus(restaurantId));
    }

    // ------------------ MANAGER MANAGEMENT ------------------ //

    @Operation(summary = "Create a new restaurant manager")
    @PostMapping("/managers/{restaurantId}")
    public ResponseEntity<String> createRestaurantManager(@PathVariable Long restaurantId, @RequestBody ManagerDTO managerDTO) {
        return ResponseEntity.ok(restaurantAdminService.createRestaurantManager(restaurantId, managerDTO));
    }

    @Operation(summary = "Update a restaurant manager's details")
    @PutMapping("/managers/{managerId}")
    public ResponseEntity<String> updateRestaurantManager(@PathVariable Long managerId, @RequestBody ManagerDTO updatedManager) {
        return ResponseEntity.ok(restaurantAdminService.updateRestaurantManager(managerId, updatedManager));
    }

    @Operation(summary = "Delete a restaurant manager")
    @DeleteMapping("/managers/{managerId}")
    public ResponseEntity<String> deleteRestaurantManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(restaurantAdminService.deleteRestaurantManager(managerId));
    }

    @Operation(summary = "Get all managers of a restaurant")
    @GetMapping("/managers/{restaurantId}")
    public ResponseEntity<List<ManagerDTO>> getAllRestaurantManagers(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantAdminService.getAllRestaurantManagers(restaurantId));
    }

    // ------------------ ORDER MANAGEMENT ------------------ //

    @Operation(summary = "Get all orders of a restaurant")
    @GetMapping("/orders/{restaurantId}")
    public ResponseEntity<List<OrderDTO>> getAllOrders(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantAdminService.getAllOrders(restaurantId));
    }

    @Operation(summary = "Get details of a specific order")
    @GetMapping("/orders/details/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(restaurantAdminService.getOrderById(orderId));
    }

    // ------------------ ORDER & PAYMENT SUMMARIES ------------------ //

    @Operation(summary = "Get order summary for a restaurant")
    @GetMapping("/orders/summary/{restaurantId}")
    public ResponseEntity<OrderSummaryDTO> getOrderSummary(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantAdminService.getOrderSummary(restaurantId));
    }

    @Operation(summary = "Get payment summary for a restaurant")
    @GetMapping("/payments/summary/{restaurantId}")
    public ResponseEntity<PaymentSummaryDTO> getPaymentSummary(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantAdminService.getPaymentSummary(restaurantId));
    }
}
