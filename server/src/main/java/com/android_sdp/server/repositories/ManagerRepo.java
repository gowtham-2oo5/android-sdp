package com.android_sdp.server.repositories;

import com.android_sdp.server.models.RestaurantManagers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ManagerRepo extends JpaRepository<RestaurantManagers, Long> {

    @Query("SELECT m FROM RestaurantManagers m WHERE m.restaurant.id = :restaurantId")
    List<RestaurantManagers> findByRestaurantId(@Param("restaurantId") Long restaurantId);


}
