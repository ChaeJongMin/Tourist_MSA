package com.auth.authservice.Dto;

import lombok.Getter;

@Getter
public class ResponseTokenDto {
    private String access;
    private String refresh;

    public ResponseTokenDto(String access, String refresh){
        this.access=access;
        this.refresh=refresh;
    }
}
