package com.android_sdp.server.services;

import com.android_sdp.server.dtos.OrderDTO;
import com.android_sdp.server.dtos.PaymentDTO;
import com.android_sdp.server.dtos.RestaurantTableDTO;
import com.android_sdp.server.dtos.MenuDTO;
import com.android_sdp.server.models.Order;
import com.android_sdp.server.models.Payment;
import com.android_sdp.server.models.RestaurantTable;
import com.android_sdp.server.models.Menu;
import com.android_sdp.server.repositories.OrderRepo;
import com.android_sdp.server.repositories.PaymentRepo;
import com.android_sdp.server.repositories.RestaurantTableRepo;
import com.android_sdp.server.repositories.MenuRepo;
import com.android_sdp.server.services.RestaurantManagerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantManagerServiceImpl implements RestaurantManagerService {

    private final RestaurantTableRepo restaurantTableRepo;
    private final OrderRepo orderRepo;
    private final PaymentRepo paymentRepo;
    private final MenuRepo menuRepo;

    public RestaurantManagerServiceImpl(
            RestaurantTableRepo restaurantTableRepo,
            OrderRepo orderRepo,
            PaymentRepo paymentRepo,
            MenuRepo menuRepo
    ) {
        this.restaurantTableRepo = restaurantTableRepo;
        this.orderRepo = orderRepo;
        this.paymentRepo = paymentRepo;
        this.menuRepo = menuRepo;
    }

    @Override
    public List<RestaurantTableDTO> getRestaurantTables(Long restaurantId) {
        List<RestaurantTable> tables = restaurantTableRepo.findByRestaurantId(restaurantId);
        return tables.stream()
                .map(table -> new RestaurantTableDTO(
                        table.getId(),
                        table.getTableNumber(),
                        table.getCapacity(),
                        table.getIsOccupied()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersForTable(Long tableId) {
        List<Order> orders = orderRepo.findByTableId(tableId);
        return orders.stream()
                .map(order -> new OrderDTO(
                        order.getId(),
                        order.getTable().getId(),
                        order.getTotalCost(),
                        order.getStatus(),
                        order.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public String getOrderStatus(Long orderId) {
        return orderRepo.findById(orderId)
                .map(Order::getStatus)
                .orElse("Order Not Found");
    }

    @Override
    public List<MenuDTO> getRestaurantMenu(Long restaurantId) {
        List<Menu> menus = menuRepo.findByRestaurantId(restaurantId);
        return menus.stream()
                .map(menu -> new MenuDTO(
                        menu.getId(),
                        menu.getRestaurant().getId(),
                        menu.getItems()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getPendingPayments(Long restaurantId) {
        List<Payment> payments = paymentRepo.findByRestaurantIdAndPaymentStatus(restaurantId, "Pending");
        return payments.stream()
                .map(payment -> new PaymentDTO(
                        payment.getId(),
                        payment.getOrder().getId(),
                        payment.getStatus().name(),
                        payment.getMethod().name(),
                        payment.getAmount(),
                        payment.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getCompletedPayments(Long restaurantId) {
        List<Payment> payments = paymentRepo.findByRestaurantIdAndPaymentStatus(restaurantId, "Completed");
        return payments.stream()
                .map(payment -> new PaymentDTO(
                        payment.getId(),
                        payment.getOrder().getId(),
                        payment.getStatus().name(),
                        payment.getMethod().name(),
                        payment.getAmount(),
                        payment.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}
