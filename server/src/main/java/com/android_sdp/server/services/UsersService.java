package com.android_sdp.server.services;

import com.android_sdp.server.dtos.UserDTO;
import com.android_sdp.server.models.Users;

import java.util.List;

public interface UsersService {

    List<Users> getUsers();
    Users getUserDataMail(String email);
    Users getUserDataPhone(String phone);

    Users createUser(UserDTO user);

    Users deleteUser(Long id);
    Users updateUser(UserDTO user);
}
