package com.review.reviewservice.Respository;

import com.review.reviewservice.Domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByName(String name);

}
