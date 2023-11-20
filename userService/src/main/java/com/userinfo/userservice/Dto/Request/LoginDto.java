package com.userinfo.userservice.Dto.Request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

//클라이언트측에서 로그인 정보를 서버에 보내기 위한 DTO 클래스
@Getter
@NoArgsConstructor
public class LoginDto {
    private String email;
    private String paswwd;
}
