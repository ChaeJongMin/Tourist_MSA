package com.review.reviewservice.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="user_review")
public class UserEntity {
    @Id
    private Long id;
    private String name;
    private String email;

    @Builder
    public UserEntity(String name, String email){
        this.name=name;
        this.email=email;
    }
}
