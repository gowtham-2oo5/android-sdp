package com.android_sdp.server.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("Manager")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class RestaurantManagers extends Users{

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

}
