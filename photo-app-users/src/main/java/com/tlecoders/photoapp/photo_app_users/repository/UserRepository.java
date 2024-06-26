package com.tlecoders.photoapp.photo_app_users.repository;

import com.tlecoders.photoapp.photo_app_users.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);
}
