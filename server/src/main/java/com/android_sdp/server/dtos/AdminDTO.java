package com.android_sdp.server.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminDTO {

    private String name;
    private String email;
    private String phone;
    private String password;
    private Boolean isSuperAdmin;
    private RestaurantDTO restaurant;
}
