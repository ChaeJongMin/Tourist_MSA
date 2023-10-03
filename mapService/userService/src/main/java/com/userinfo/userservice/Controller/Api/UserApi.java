package com.userinfo.userservice.Controller.Api;

import com.userinfo.userservice.Dto.Request.SaveDto;
import com.userinfo.userservice.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserApi {
    private final UserService userService;
    // 유저 정보
    @GetMapping("/api/user")
    public Long getUser(){
        return (long)1;
    }
    // 회원가입
    @PostMapping("/api/user")
    public ResponseEntity<?> saveUser(@RequestBody SaveDto saveDto){
        log.info("/api/user 작동 " + saveDto);
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(saveDto));
    }
    // 정보 수정
    @PutMapping("/api/user/{id}")
    public Long updateUser(@PathVariable Long id){
        return (long)1;
    }
}
