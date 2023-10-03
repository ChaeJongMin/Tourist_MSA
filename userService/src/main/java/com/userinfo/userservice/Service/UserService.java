package com.userinfo.userservice.Service;

import com.userinfo.userservice.Dto.Request.SaveDto;
import com.userinfo.userservice.Dto.Request.UpdateDto;
import com.userinfo.userservice.Dto.Response.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    //회원가입
    Long save(SaveDto saveDto);
    //유저 정보 찾기
    UserDto getUserById(Long id);
    String getUserNameByEmail(String email);

    //유저 정보 수정
    Long update(Long id, UpdateDto updateDto);

}
