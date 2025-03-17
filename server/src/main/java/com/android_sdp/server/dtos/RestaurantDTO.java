package com.android_sdp.server.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RestaurantDTO {

    private Long id;
    private String name;
    private String ownerId;
    private String ownerName;
}
