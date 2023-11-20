package com.auth.authservice.Dto;

import lombok.Getter;

@Getter
//타 서비스(유저 서비스)로 토큰 정보를 전달할 DTO 객체
public class ResponseTokenDto {
    private String access;
    private String refresh;

    public ResponseTokenDto(String access, String refresh){
        this.access=access;
        this.refresh=refresh;
    }
}
