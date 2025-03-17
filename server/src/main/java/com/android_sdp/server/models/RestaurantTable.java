package com.android_sdp.server.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestaurantTable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = true)
    private Restaurant restaurant;

    @Column(nullable = false)
    private int tableNumber;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Boolean isOccupied;

}
