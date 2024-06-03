package com.tlecoders.photoapp.photo_app_users.repository;

import com.tlecoders.photoapp.photo_app_users.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity,Long> {
}
