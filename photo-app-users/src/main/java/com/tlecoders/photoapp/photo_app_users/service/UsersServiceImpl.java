package com.tlecoders.photoapp.photo_app_users.service;

import com.tlecoders.photoapp.photo_app_users.entity.UserEntity;
import com.tlecoders.photoapp.photo_app_users.model.User;
import com.tlecoders.photoapp.photo_app_users.repository.UserRepository;
import com.tlecoders.photoapp.photo_app_users.shared.UsersDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService{
    UserRepository userRepository;

    public UsersServiceImpl(UserRepository userRepository)
    {
        this.userRepository=userRepository;
    }
    @Override
    public UsersDTO createUser(UsersDTO userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity user=modelMapper.map(userDetails,UserEntity.class);
        user.setEncryptedPassword("test");
       return modelMapper.map(userRepository.save(user),UsersDTO.class);

    }
}
