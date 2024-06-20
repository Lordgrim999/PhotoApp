package com.tlecoders.photoapp.photo_app_users.service;

import com.tlecoders.photoapp.photo_app_users.shared.UsersDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {

    public UsersDTO createUser(UsersDTO userDetails);
}
