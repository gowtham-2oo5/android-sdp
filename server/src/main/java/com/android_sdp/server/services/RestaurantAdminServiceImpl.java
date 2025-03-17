package com.android_sdp.server.services;

import com.android_sdp.server.dtos.*;
import com.android_sdp.server.models.*;
import com.android_sdp.server.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantAdminServiceImpl implements RestaurantAdminService {

    private final MenuRepo menuRepo;
    private final ItemRepo itemRepo;
    private final ManagerRepo managerRepo;
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final PaymentRepo paymentRepo;
    private final RestaurantRepo restaurantRepo;

    // ------------------ MENU OPERATIONS ------------------ //

    @Transactional
    @Override
    public String createMenu(Long restaurantId, MenuDTO menuDTO) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Menu menu = new Menu();
        menu.setRestaurant(restaurant);
        menuRepo.save(menu);

        return "Menu created successfully with ID: " + menu.getId();
    }

    @Transactional
    @Override
    public String addItemToMenu(Long menuId, MenuItemDTO menuItemDTO) {
        Menu menu = menuRepo.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        Item item = new Item();
        item.setName(menuItemDTO.getName());
        item.setType(menuItemDTO.getType());
        item.setCost(menuItemDTO.getCost());
        item.setRestaurant(menu.getRestaurant());
        item.setMenu(menu);

        itemRepo.save(item);
        return "Item added successfully to menu ID: " + menuId;
    }

    @Transactional
    @Override
    public String updateMenuItem(Long menuItemId, MenuItemDTO updatedItem) {
        Item item = itemRepo.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        item.setName(updatedItem.getName());
        item.setType(updatedItem.getType());
        item.setCost(updatedItem.getCost());

        itemRepo.save(item);
        return "Menu item updated successfully.";
    }

    @Transactional
    @Override
    public String deleteMenuItem(Long menuItemId) {
        itemRepo.deleteById(menuItemId);
        return "Menu item deleted successfully.";
    }

    @Override
    public List<MenuDTO> getRestaurantMenus(Long restaurantId) {
        // TODO: Fix this
        return List.of();
    }

    // ------------------ MANAGER OPERATIONS ------------------ //

    @Transactional
    @Override
    public String createRestaurantManager(Long restaurantId, ManagerDTO managerDTO) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        RestaurantManagers manager = new RestaurantManagers();
        manager.setName(managerDTO.getName());
        manager.setEmail(managerDTO.getEmail());
        manager.setRestaurant(restaurant);

        managerRepo.save(manager);
        return "Restaurant manager created successfully.";
    }

    @Transactional
    @Override
    public String updateRestaurantManager(Long managerId, ManagerDTO updatedManager) {
        RestaurantManagers manager = managerRepo.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        manager.setName(updatedManager.getName());
        manager.setEmail(updatedManager.getEmail());

        managerRepo.save(manager);
        return "Restaurant manager updated successfully.";
    }

    @Transactional
    @Override
    public String deleteRestaurantManager(Long managerId) {
        managerRepo.deleteById(managerId);
        return "Restaurant manager deleted successfully.";
    }

    @Override
    public List<ManagerDTO> getAllRestaurantManagers(Long restaurantId) {
        return List.of(); // TODO: Fix
    }

    // ------------------ ORDER OPERATIONS ------------------ //

    @Override
    public List<OrderDTO> getAllOrders(Long restaurantId) {
        return List.of();
    }

    @Override
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return new OrderDTO(order.getId(), order.getStatus(), order.getTotalCost(), order.getRestaurant().getId());
    }

    // ------------------ ORDER & PAYMENT SUMMARIES ------------------ //

    @Override
    public OrderSummaryDTO getOrderSummary(Long restaurantId) {
        int totalOrders = orderRepo.countByRestaurantId(restaurantId);
        int completedOrders = orderRepo.countByRestaurantIdAndStatus(restaurantId, "Closed");
        int pendingOrders = totalOrders - completedOrders;
        double totalRevenue = paymentRepo.sumCompletedPaymentsByRestaurantId(restaurantId);

        return new OrderSummaryDTO(restaurantId, totalOrders, completedOrders, pendingOrders, totalRevenue);
    }

    @Override
    public PaymentSummaryDTO getPaymentSummary(Long restaurantId) {
        double totalRevenue = paymentRepo.sumCompletedPaymentsByRestaurantId(restaurantId);
        double pendingPayments = paymentRepo.sumPendingPaymentsByRestaurantId(restaurantId);
        double failedPayments = paymentRepo.sumFailedPaymentsByRestaurantId(restaurantId);

        return new PaymentSummaryDTO(restaurantId, totalRevenue, pendingPayments, totalRevenue, failedPayments);
    }
}
