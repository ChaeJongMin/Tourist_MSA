package com.auth.authservice.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Slf4j
public class RefreshTokenEntity extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String value;

    private long expTime;

    public void update(String value, long expTime){
        log.info("update 메소드 작동 "+expTime);
        this.value=value;
        this.expTime=expTime;

    }

    @Builder
    public RefreshTokenEntity(String userId, String value, long expTime){
        this.userId=userId;
        this.value=value;
        this.expTime = expTime;
    }

}
