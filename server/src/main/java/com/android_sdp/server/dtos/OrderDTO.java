package com.android_sdp.server.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDTO {
    private Long id;
    private Long customerId;
    private Long restaurantId;
    private Long tableId;
    private String status; // Open / Closed
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItemDTO> orderItems;

    public OrderDTO(Long id, String status, Long totalCost, Long id1) {
    }

    public OrderDTO(Long id, Long id1, Long totalCost, String status, LocalDateTime createdAt) {
    }
}
