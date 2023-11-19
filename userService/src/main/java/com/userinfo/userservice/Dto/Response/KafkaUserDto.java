package com.userinfo.userservice.Dto.Response;

import com.userinfo.userservice.Domain.UserEntity;
import lombok.Getter;

@Getter
public class KafkaUserDto {

    //id, 이름, 이메일
    private Long id;
    private String email;
    private String name;

    public KafkaUserDto(UserEntity userEntity){
        this.id=userEntity.getId();
        this.email= userEntity.getEmail();
        this.name= userEntity.getName();
    }

}
