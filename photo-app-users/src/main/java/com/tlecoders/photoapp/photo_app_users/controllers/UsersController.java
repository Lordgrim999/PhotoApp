package com.tlecoders.photoapp.photo_app_users.controllers;

import com.tlecoders.photoapp.photo_app_users.model.User;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @GetMapping("/status")
    public String status()
    {
        return "Working";
    }

    @PostMapping
    public String createUser(@Valid @RequestBody User userDetails)
    {
        return "";
    }

}
