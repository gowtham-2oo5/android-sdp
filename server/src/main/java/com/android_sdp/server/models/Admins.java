package com.android_sdp.server.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@DiscriminatorValue("Admin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Admins extends Users {

    private boolean superAdmin;

    @OneToOne(mappedBy = "owner")
    private Restaurant restaurant;


}
