package com.userinfo.userservice.Dto.Response;

import lombok.Getter;

//auth 인스턴스에서 보낸 토큰 정보를 저장하기 위한 DTO 클래스
@Getter
public class ResponseCookieDto {
    private String access;
    private String refresh;
}
