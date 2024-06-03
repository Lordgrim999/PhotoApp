package com.tlecoders.photoapp.photo_app_users.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name="users")
@Getter
@Setter
public class UserEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 8175156199025700041L;
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,unique = true)
    private String userId;

    @Column(nullable = false,length = 50)
    private String firstName;

    @Column(nullable = false,length = 50)
    private String lastName;

    @Column(nullable = false,length = 120,unique = true)
    private String email;

    @Column(nullable = false,unique = true)
    private String encryptedPassword;
}
