package com.android_sdp.server.services;

import com.android_sdp.server.dtos.AdminDTO;
import com.android_sdp.server.models.Admins;
import java.util.List;

public interface AdminService {

    String createSuperAdmin(AdminDTO superAdmin);
    List<Admins> getAllAdmins();
    List<Admins> getSuperAdmins();
    String createAdmin(AdminDTO admin);
    String assignAdminToRestaurant(Long adminId, Long restaurantId);
    String createRestaurantAdmin(AdminDTO admin);
}