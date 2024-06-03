package com.tlecoders.photoapp.photo_app_users.service;

import com.tlecoders.photoapp.photo_app_users.shared.UsersDTO;

import java.util.UUID;

public class UsersServiceImpl implements UsersService{
    @Override
    public UsersDTO createUser(UsersDTO userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        return null;
    }
}
