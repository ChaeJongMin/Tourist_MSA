package com.userinfo.userservice.Repository;

import com.userinfo.userservice.Domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String target);
    Optional<UserEntity> findByName(String target);
    boolean existsByEmail(String target);
}
