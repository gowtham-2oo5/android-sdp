package com.android_sdp.server.services;

import com.android_sdp.server.dtos.UserDTO;
import com.android_sdp.server.models.Users;
import com.android_sdp.server.repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UsersService {

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public List<Users> getUsers() {
        return usersRepo.findAll();
    }

    @Override
    public Users getUserDataMail(String email) {
        return usersRepo.findByEmail(email);
    }

    @Override
    public Users getUserDataPhone(String phone) {
        return usersRepo.findByPhone(phone);
    }

    @Override
    @Transactional
    public Users createUser(UserDTO userDto) {
        Users user = new Users();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setPassword(userDto.getPassword());
        return usersRepo.save(user);
    }

    @Override
    @Transactional
    public Users deleteUser(Long id) {
        Optional<Users> userOptional = usersRepo.findById(id);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            usersRepo.delete(user);
            return user;
        }
        return null;
    }

    @Override
    @Transactional
    public Users updateUser(UserDTO userDto) {
        Users target = usersRepo.findByEmail(userDto.getEmail());
        if (!target.getEmail().isEmpty()) {
            target.setName(userDto.getName());
            target.setPhone(userDto.getPhone());
            target.setPassword(userDto.getPassword());
            return usersRepo.save(target);
        }
        return null;
    }
}
