package com.android_sdp.server.repositories;

import com.android_sdp.server.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Users findByEmail(@Param("email") String email);

    @Query("SELECT u FROM Users u WHERE u.phone = :phone")
    Users findByPhone(@Param("phone") String phone);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
