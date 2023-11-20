package com.userinfo.userservice.Dto.Response;

import com.userinfo.userservice.Domain.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

//자신(유저)의 정보를 보내기 위한 DTO 클래스
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
