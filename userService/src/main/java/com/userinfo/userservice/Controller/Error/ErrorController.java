package com.userinfo.userservice.Controller.Error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ErrorController extends ResponseEntityExceptionHandler {
    //중복 예외처리
    @ExceptionHandler({ DuplicateKeyException.class })
    protected ResponseEntity<?> handleDuplicateException(DuplicateKeyException e) {
        log.info("중복 예외 발생");
        Map<String, Object> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
