package com.userinfo.userservice.Dto.Response;

import com.userinfo.userservice.Domain.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
    private String email;
    private String name;
    private String phoneNumber;

    public UserDto(UserEntity userEntity){
        this.email=userEntity.getEmail();
        this.name=userEntity.getName();
        this.phoneNumber=userEntity.getPhoneNumber();
    }

}
