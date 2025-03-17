package com.android_sdp.server.repositories;

import com.android_sdp.server.models.Admins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepo extends JpaRepository<Admins, Long> {

    @Query("SELECT a FROM Admins a WHERE a.superAdmin = true")
    List<Admins> findBySuperAdminTrue();

}
