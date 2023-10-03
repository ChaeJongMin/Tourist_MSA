package com.userinfo.userservice.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String passwd;
    private String name;
    private String phoneNumber;

    public void update(String name, String passwd){
        this.name=name;
        this.passwd=passwd;
    }

    @Builder
    public UserEntity(String email, String passwd, String name, String phoneNumber){
        this.email = email;
        this.passwd = passwd;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
