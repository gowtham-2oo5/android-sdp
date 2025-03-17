package com.android_sdp.server.services;

import com.android_sdp.server.dtos.AdminDTO;
import com.android_sdp.server.models.Admins;
import com.android_sdp.server.models.Restaurant;
import com.android_sdp.server.models.UserRole;
import com.android_sdp.server.repositories.AdminRepo;
import com.android_sdp.server.repositories.RestaurantRepo;
import com.android_sdp.server.repositories.UsersRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepo adminRepository;

    @Autowired
    private UsersRepo userRepository;

    @Autowired
    private RestaurantRepo restaurantRepository;

    @Override
    public String createSuperAdmin(AdminDTO superAdmin) {
        try {
            Admins admin = new Admins();
            admin.setName(superAdmin.getName());
            admin.setEmail(superAdmin.getEmail());
            admin.setPassword(superAdmin.getPassword());
            admin.setPhone(superAdmin.getPhone());
            admin.setSuperAdmin(superAdmin.getIsSuperAdmin());
            admin.setRestaurant(null);
            adminRepository.save(admin);
            return "Super Admin created successfully";
        } catch (Exception e) {
            return "Error creating Super Admin: " + e.getMessage();
        }
    }

    @Override
    public List<Admins> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public List<Admins> getSuperAdmins() {
        return adminRepository.findBySuperAdminTrue();
    }

    @Override
    public String createAdmin(AdminDTO admin) {
        try {
            Admins newAdmin = new Admins();
            newAdmin.setName(admin.getName());
            newAdmin.setEmail(admin.getEmail());
            newAdmin.setPassword(admin.getPassword());
            newAdmin.setPhone(admin.getPhone());
            newAdmin.setSuperAdmin(false);
            newAdmin.setRestaurant(null);
            adminRepository.save(newAdmin);
            return "Admin created successfully";
        } catch (Exception e) {
            return "Error creating Admin: " + e.getMessage();
        }
    }

    @Transactional
    @Override
    public String assignAdminToRestaurant(Long adminId, Long restaurantId) {
        try {
            Admins admin = adminRepository.findById(adminId)
                    .orElseThrow(() -> new EntityNotFoundException("Admin not found with ID: " + adminId));

            if (admin.isSuperAdmin()) {
                return "Super Admin cannot be assigned to a restaurant!";
            }

            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with ID: " + restaurantId));

            admin.setRestaurant(restaurant);
            restaurant.setOwner(admin);

            adminRepository.save(admin);
            restaurantRepository.save(restaurant);

            return "Admin assigned to restaurant successfully";
        } catch (EntityNotFoundException e) {
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            return "Unexpected error occurred: " + e.getMessage();
        }
    }

    @Override
    public String createRestaurantAdmin(AdminDTO adminDto) {
        if (adminDto == null) {
            throw new IllegalArgumentException("Admin data cannot be null");
        }

        // Check if the email or phone already exists
        if (userRepository.existsByEmail(adminDto.getEmail()) || userRepository.existsByPhone(adminDto.getPhone())) {
            throw new RuntimeException("Admin with given email or phone already exists");
        }

        // Create Admin entity from DTO
        Admins admin = new Admins();
        admin.setName(adminDto.getName());
        admin.setEmail(adminDto.getEmail());
        admin.setPhone(adminDto.getPhone());
        admin.setPassword(adminDto.getPassword()); // No encoding
        admin.setSuperAdmin(Boolean.TRUE.equals(adminDto.getIsSuperAdmin()));
        admin.setUserRole(UserRole.RESTAURANT_ADMIN); // Set user role

        // Associate the restaurant if provided
        if (adminDto.getRestaurant() != null) {
            Restaurant restaurant = restaurantRepository.findById(adminDto.getRestaurant().getId())
                    .orElseThrow(() -> new RuntimeException("Restaurant not found"));
            admin.setRestaurant(restaurant);
            restaurant.setOwner(admin);
        }

        // Save admin
        userRepository.save(admin);

        return "Restaurant Admin created successfully with email: " + admin.getEmail();
    }

}
