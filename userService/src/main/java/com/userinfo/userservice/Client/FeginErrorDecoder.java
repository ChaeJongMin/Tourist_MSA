package com.userinfo.userservice.Client;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

//FeginClient로 타 서비스 요청 시 발생한 예외를 처리하기 위한 클래스
public class FeginErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 400:
                break;
            case 404:
                //메소드 getReviews 요청 시 발생한 예외를 처리
                if(methodKey.contains("getReviews")){
                    //예외 발생
                    return new ResponseStatusException(HttpStatusCode.valueOf(response.status()),
                            "User's reviews is empty");
                }
                break;
            default:
                return new Exception(response.reason());
        }
        return null;
    }
}
