package com.userinfo.userservice.Dto.Response;

import com.userinfo.userservice.Domain.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserDto {
    private String email;
    private String name;
    private String phoneNumber;

    private List<ResponseReviewToUserDto> myReviewList;

    public UserDto(UserEntity userEntity, List<ResponseReviewToUserDto> myReviewList){
        this.email=userEntity.getEmail();
        this.name=userEntity.getName();
        this.phoneNumber=userEntity.getPhoneNumber();
        this.myReviewList=myReviewList;
    }

}
