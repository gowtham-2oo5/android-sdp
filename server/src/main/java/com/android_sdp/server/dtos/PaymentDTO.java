package com.android_sdp.server.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentDTO {
    private Long id;
    private Long orderId;
    private String paymentStatus; // Pending / Completed / Failed
    private String paymentMethod; // UPI / Card / Cash
    private Double amount;
    private LocalDateTime createdAt;
}
