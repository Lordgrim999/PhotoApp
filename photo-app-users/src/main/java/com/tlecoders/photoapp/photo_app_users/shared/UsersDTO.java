package com.tlecoders.photoapp.photo_app_users.shared;


import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class UsersDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 4138289629561375855L;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
}
