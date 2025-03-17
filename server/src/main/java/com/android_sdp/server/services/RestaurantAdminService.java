package com.android_sdp.server.services;

import com.android_sdp.server.dtos.*;

import java.util.List;

public interface RestaurantAdminService {

    // ✅ Menu Management
    String createMenu(Long restaurantId, MenuDTO menu);
    String addItemToMenu(Long menuId, MenuItemDTO menuItem);
    String updateMenuItem(Long menuItemId, MenuItemDTO updatedItem);
    String deleteMenuItem(Long menuItemId);
    List<MenuDTO> getRestaurantMenus(Long restaurantId);

    // ✅ Restaurant Manager Management
    String createRestaurantManager(Long restaurantId, ManagerDTO manager);
    String updateRestaurantManager(Long managerId, ManagerDTO updatedManager);
    String deleteRestaurantManager(Long managerId);
    List<ManagerDTO> getAllRestaurantManagers(Long restaurantId);

    // ✅ Orders Management
    List<OrderDTO> getAllOrders(Long restaurantId);
    OrderDTO getOrderById(Long orderId);

    // ✅ Order Summaries
    OrderSummaryDTO getOrderSummary(Long restaurantId);
    PaymentSummaryDTO getPaymentSummary(Long restaurantId);

}
