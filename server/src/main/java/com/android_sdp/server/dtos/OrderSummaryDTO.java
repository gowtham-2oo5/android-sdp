package com.android_sdp.server.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderSummaryDTO {
    private Long restaurantId;
    private Integer totalOrders;
    private Integer completedOrders;
    private Integer pendingOrders;
    private Double totalRevenue;
}
