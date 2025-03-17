package com.android_sdp.server.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ManagerDTO {
    private Long id;
    private String name;
    private String email;
    private Long restaurantId;
}
