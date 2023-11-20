package com.auth.authservice.Repository;

import com.auth.authservice.Domain.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//DB와 상호작용하기 위한 인터페이스
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    //유저로부터 엔티티 찾기
    Optional<RefreshTokenEntity> findByUserId(String userId);
    //리프레쉬토큰 값으로부터 엔티티 찾기
    Optional<RefreshTokenEntity> findByValue(String value);


}
