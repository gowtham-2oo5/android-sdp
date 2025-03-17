package com.android_sdp.server.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuItemDTO {
    private Long id;
    private String name;
    private String type; // V (Veg) / NV (Non-Veg)
    private Double cost;
    private Long menuId;
    private Long restaurantId;
}
