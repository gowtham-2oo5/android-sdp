package com.android_sdp.server.repositories;

import com.android_sdp.server.models.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantTableRepo extends JpaRepository<RestaurantTable, Long> {

    @Query("SELECT t FROM RestaurantTable t WHERE t.restaurant.id = :restaurantId")
    List<RestaurantTable> findByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("SELECT COUNT(t) FROM RestaurantTable t WHERE t.restaurant.id = :restaurantId AND t.isOccupied = false")
    int countAvailableTables(@Param("restaurantId") Long restaurantId);

    @Query("SELECT COUNT(t) FROM RestaurantTable t WHERE t.restaurant.id = :restaurantId AND t.isOccupied = true")
    int countOccupiedTables(@Param("restaurantId") Long restaurantId);
}
