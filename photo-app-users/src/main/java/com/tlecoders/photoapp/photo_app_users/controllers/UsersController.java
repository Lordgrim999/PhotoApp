package com.tlecoders.photoapp.photo_app_users.controllers;

import com.tlecoders.photoapp.photo_app_users.entity.UserEntity;
import com.tlecoders.photoapp.photo_app_users.model.User;
import com.tlecoders.photoapp.photo_app_users.service.UsersServiceImpl;
import com.tlecoders.photoapp.photo_app_users.shared.UsersDTO;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsersServiceImpl usersService;

    @GetMapping("/status")
    public String status()
    {
        return "Working";
    }

    @PostMapping
    public UsersDTO createUser(@Valid @RequestBody User userDetails)
    {
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UsersDTO usersDTO=modelMapper.map(userDetails,UsersDTO.class);
        return usersService.createUser(usersDTO);
    }

}
