package com.userinfo.userservice.Dto.Request;

import com.userinfo.userservice.Domain.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveDto {
    private String email;
    private String passwd;
    private String name;
    private String phoneNumber;

    // 해당 메소드를 추가한 이유
    // Entity와 DTO 간의 데이터 전송을 구현하는데 필요한 코드 양을 줄이기 위해
    // 또한 데이터 전송(DTO의 목적) 목적을 위한 기능이므로 해당 메소드 포함
    public UserEntity toUserEntity(){
        return UserEntity.builder()
                .email(email)
                .passwd(passwd)
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
    }

}
