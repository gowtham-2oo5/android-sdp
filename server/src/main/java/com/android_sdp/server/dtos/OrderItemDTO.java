package com.android_sdp.server.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemDTO {
    private Long id;
    private Long orderId;
    private Long menuItemId;
    private Integer quantity;
}
