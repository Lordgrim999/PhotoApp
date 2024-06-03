package com.tlecoders.photoapp.photo_app_users.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    @NotNull(message = "first name cannot be null")
    @Size(min=2, message = "first name should have at least 2 characters")
    private String firstName;

    @NotNull(message = "last name cannot be null")
    @Size(min=2, message = "last name should have at least 2 characters")
    private String lastName;

    @NotNull(message = "email cannot be null")
   @Email
    private String email;

    @NotNull(message = "password cannot be null")
    @Size(min=8,max=16,message = "password must be between 8 and 16 characters")
    private String password;
}
