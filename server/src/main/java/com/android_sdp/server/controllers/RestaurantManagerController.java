package com.android_sdp.server.controllers;

import com.android_sdp.server.dtos.OrderDTO;
import com.android_sdp.server.dtos.PaymentDTO;
import com.android_sdp.server.dtos.RestaurantTableDTO;
import com.android_sdp.server.dtos.MenuDTO;
import com.android_sdp.server.services.RestaurantManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant-manager")
@Tag(name = "Restaurant Manager", description = "Operations for managing restaurant tables, orders, and payments")
public class RestaurantManagerController {

    private final RestaurantManagerService restaurantManagerService;

    public RestaurantManagerController(RestaurantManagerService restaurantManagerService) {
        this.restaurantManagerService = restaurantManagerService;
    }

    @Operation(summary = "Get all tables in a restaurant")
    @GetMapping("/{restaurantId}/tables")
    public ResponseEntity<List<RestaurantTableDTO>> getRestaurantTables(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantManagerService.getRestaurantTables(restaurantId));
    }

    @Operation(summary = "Get all orders for a specific table")
    @GetMapping("/tables/{tableId}/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersForTable(@PathVariable Long tableId) {
        return ResponseEntity.ok(restaurantManagerService.getOrdersForTable(tableId));
    }

    @Operation(summary = "Get the status of a specific order")
    @GetMapping("/orders/{orderId}/status")
    public ResponseEntity<String> getOrderStatus(@PathVariable Long orderId) {
        return ResponseEntity.ok(restaurantManagerService.getOrderStatus(orderId));
    }

    @Operation(summary = "Get restaurant menu (read-only)")
    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<List<MenuDTO>> getRestaurantMenu(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantManagerService.getRestaurantMenu(restaurantId));
    }

    @Operation(summary = "Get all pending payments for a restaurant")
    @GetMapping("/{restaurantId}/payments/pending")
    public ResponseEntity<List<PaymentDTO>> getPendingPayments(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantManagerService.getPendingPayments(restaurantId));
    }

    @Operation(summary = "Get all completed payments for a restaurant")
    @GetMapping("/{restaurantId}/payments/completed")
    public ResponseEntity<List<PaymentDTO>> getCompletedPayments(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantManagerService.getCompletedPayments(restaurantId));
    }
}
