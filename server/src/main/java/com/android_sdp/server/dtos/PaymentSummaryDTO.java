package com.android_sdp.server.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentSummaryDTO {
    private Long restaurantId;
    private Double totalRevenue;
    private Double pendingPayments;
    private Double completedPayments;
    private Double failedPayments;
}
