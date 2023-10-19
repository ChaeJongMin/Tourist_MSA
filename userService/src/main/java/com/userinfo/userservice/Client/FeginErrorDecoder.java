package com.userinfo.userservice.Client;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class FeginErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 400:
                break;
            case 404:
                if(methodKey.contains("getReviews")){
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
