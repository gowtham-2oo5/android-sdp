package com.android_sdp.server.services;

import com.android_sdp.server.dtos.OrderDTO;
import com.android_sdp.server.dtos.PaymentDTO;
import com.android_sdp.server.dtos.RestaurantTableDTO;
import com.android_sdp.server.dtos.MenuDTO;

import java.util.List;

public interface RestaurantManagerService {

    // ✅ View restaurant tables
    List<RestaurantTableDTO> getRestaurantTables(Long restaurantId);

    // ✅ View orders on a table
    List<OrderDTO> getOrdersForTable(Long tableId);

    // ✅ Monitor order status
    String getOrderStatus(Long orderId);

    // ✅ View menu (Read only)
    List<MenuDTO> getRestaurantMenu(Long restaurantId);

    // ✅ Monitor Payments
    List<PaymentDTO> getPendingPayments(Long restaurantId);
    List<PaymentDTO> getCompletedPayments(Long restaurantId);
}
