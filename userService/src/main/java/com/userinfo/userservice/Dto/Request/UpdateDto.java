package com.userinfo.userservice.Dto.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//클라이언트측에서 보낸 유저 수정 정보 저장한 DTO 클래스
@Getter
@NoArgsConstructor
public class UpdateDto {
    private String email;
    private String phone;
}
