package com.tlecoders.photoapp.photo_app_users.controllers;

import com.tlecoders.photoapp.photo_app_users.entity.UserEntity;
import com.tlecoders.photoapp.photo_app_users.model.User;
import com.tlecoders.photoapp.photo_app_users.model.UserResponse;
import com.tlecoders.photoapp.photo_app_users.service.UsersServiceImpl;
import com.tlecoders.photoapp.photo_app_users.shared.UsersDTO;
import jakarta.validation.Valid;
import org.apache.http.protocol.HTTP;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsersServiceImpl usersService;

    @Autowired
    Environment environment;

    @GetMapping("/status")
    public String status()
    {
        return "Working and the secret is "+environment.getProperty("token.secret");
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody User userDetails)
    {
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UsersDTO usersDTO=modelMapper.map(userDetails,UsersDTO.class);
        UsersDTO user = usersService.createUser(usersDTO);
        UserResponse result=modelMapper.map(user,UserResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
