package com.auth.authservice.Domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
// 엔티티 생성일자를 처리해주는 추상 클래스
public class BaseTime {
    @CreatedDate
    private LocalDateTime createDates;

}
