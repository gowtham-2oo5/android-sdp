package com.android_sdp.server.repositories;

import com.android_sdp.server.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.restaurant.id = :restaurantId AND p.status = 'COMPLETED'")
    double sumCompletedPaymentsByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.restaurant.id = :restaurantId AND p.status = 'PENDING'")
    double sumPendingPaymentsByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.restaurant.id = :restaurantId AND p.status = 'FAILED'")
    double sumFailedPaymentsByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("SELECT p FROM Payment p WHERE p.restaurant.id = :restaurantId AND p.status = :status")
    List<Payment> findByRestaurantIdAndPaymentStatus(Long restaurantId, String pending);
}
