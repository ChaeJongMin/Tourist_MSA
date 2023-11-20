package com.userinfo.userservice.Controller.Api;

import com.userinfo.userservice.Dto.Request.SaveDto;
import com.userinfo.userservice.Dto.Request.UpdateDto;
import com.userinfo.userservice.MessageQueue.KafkaProducer;
import com.userinfo.userservice.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-service")
@Slf4j
public class UserApi {
    private final UserService userService;

    // 유저 정보를 탐색하는 API
    @GetMapping("/api/user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId){
        log.info("/api/user/{userId} 호출");
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUserId(userId));
    }
    // 회원가입하는 API
    @PostMapping("/api/user")
    public ResponseEntity<?> saveUser(@RequestBody SaveDto saveDto){
        log.info("/api/user 작동 " + saveDto);
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(saveDto));
    }
    // 유저 정보 수정하는 API
    @PutMapping("/api/user/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody UpdateDto updateDto, @PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userId, updateDto));
    }


}
