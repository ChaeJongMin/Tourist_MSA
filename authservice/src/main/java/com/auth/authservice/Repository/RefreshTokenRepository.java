package com.auth.authservice.Repository;

import com.auth.authservice.Domain.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByUserId(String userId);

    boolean existsByUserId(String userId);
    Optional<RefreshTokenEntity> findByValue(String value);


}
