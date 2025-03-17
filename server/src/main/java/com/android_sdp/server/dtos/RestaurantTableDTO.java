package com.android_sdp.server.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestaurantTableDTO {

    private Long id;
    private int tableNumber;
    private int capacity;
    private boolean isOccupied;
    private Long restaurantId;

    public RestaurantTableDTO(Long id, int tableNumber, Integer capacity, Boolean isOccupied) {
    }
}
